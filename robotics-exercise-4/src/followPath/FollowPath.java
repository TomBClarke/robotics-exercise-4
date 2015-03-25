package followPath;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import gridMap.GridMap;
import ilist.IList;
import ilist.Nil;
import robotMove.GridNavigator;
import robotSearches.*;
import rp.robotics.mapping.IGridMap;
import rp.robotics.mapping.MapUtils;
import rp.robotics.mapping.RPLineMap;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.comm.RConsole;
import lejos.util.Delay;

/**
 * Makes the robot go to a set of cooridnates.
 * 
 * 
 * @author Thomas Clarke, Rowan Cole and Kyle Allen-Taylor
 *
 */
public class FollowPath {
	
	private FindPath findPath;
	private RobotPose pose;
	private ArrayList<Coordinate> targets;
	
	public static void main(String [] args) {
		
		RConsole.openBluetooth(0);
		PrintStream ps = RConsole.getPrintStream();
		System.setOut(ps);
		System.setErr(ps);
		
//		UltrasonicSensor sensor = new UltrasonicSensor(SensorPort.S1);
//		while(true) {
//			System.out.println(sensor.getDistance());
//			Delay.msDelay(1000);
//		}
		
		
		new FollowPath();
	}
	
	/**
	 * Makes the robot travel to a set of coordinates.
	 */
	public FollowPath() {	
		
		RPLineMap lineMap = MapUtils.create2015Map1();

		int xJunctions = 14;
		int yJunctions = 8;
		float junctionSeparation = 30;

		int xInset = 15;
		int yInset = 15;

		IGridMap gridMap = createGridMap(lineMap, xJunctions, yJunctions,
				xInset, yInset, junctionSeparation);		
		
		this.pose = new RobotPose(10, 1, RobotPose.NEG_X);
		findPath = new FindPath(gridMap);
		
		targets = new ArrayList<Coordinate>();
		targets.add(new Coordinate(10, 1));
		targets.add(new Coordinate(6, 2));
		targets.add(new Coordinate(10, 3));
		targets.add(new Coordinate(6, 3));
		targets.add(new Coordinate(0, 7));
		
		new GridNavigator(this);
	}
	
	/**
	 * Create a new set of instructions for the robot to follow.
	 * 
	 * @return The directioin it should turn at each junction until iit reaches its destination.
	 */
	public ArrayList<Integer> getPath(){
		boolean ready = false;
		IList<Node<Coordinate>> nodePath = new Nil<Node<Coordinate>>();
		
		while(!ready) {
			System.out.println("Target: " + targets.get(0));
			nodePath = findPath.getPath(new Coordinate((int)pose.getX(), (int)pose.getY()), targets.get(0));
			if (nodePath.isEmpty()) {
				System.out.println("CANNOT REACH TARGET NODE: " + targets.get(0) + ", skipping...");
				targets.remove(0);
				if(targets.isEmpty()) {
					System.out.println("No more targets, bye!");
					System.exit(0);
				}
			} else {
				ready = true;
			}
		}
		
		Node<Coordinate> previousLocation = nodePath.head();
		nodePath = nodePath.tail();
		ArrayList<Integer> movePath = new ArrayList<Integer>();
		int actualHeading = pose.getHeading();
		
		while(!nodePath.isEmpty()) {
			int initialX = previousLocation.contents().x();
			int initialY = previousLocation.contents().y();
			Node<Coordinate> currentLocation = nodePath.head();
			nodePath = nodePath.tail();
			
			int x = currentLocation.contents().x();
			int y = currentLocation.contents().y();
			int changeX = x - initialX;
			int changeY = y - initialY;
			
			int heading;
			if(changeX == 1) {
				heading = RobotPose.POS_X;
			} else if(changeX == -1) {
				heading = RobotPose.NEG_X;
			} else if(changeY == 1) {
				heading = RobotPose.POS_Y;
			} else {
				heading = RobotPose.NEG_Y;
			}
			
			int headingChange = heading - actualHeading;
			
			if(headingChange == 1 || headingChange == -3) {
				movePath.add(2);
			} else if(headingChange == 2 || headingChange == -2) {
				movePath.add(3);
			} else if(headingChange == 3 || headingChange == -1) {
				movePath.add(0);
			} else {
				movePath.add(1);
			}
			
			actualHeading += headingChange;
			previousLocation = currentLocation;
		}
		
		movePath.add(1);
		
		return movePath;
	}
	
	/**
	 * Returns the pose of the robot.
	 * 
	 * @return The pose.
	 */
	public RobotPose getPose(){
		return pose;
	}
	
	/**
	 * Returns the list of coordinates the robot is aiming to get to.
	 * 
	 * @return The list of target coordinates.
	 */
	public ArrayList<Coordinate> getTargets(){
		return targets;
	}
	
	/**
	 * Checks against the pose to see if the robot is at its destination.
	 * 
	 * @return Whether the robot has reached its destination.
	 */
	public boolean isAtDestination(){
		return pose.getX() == targets.get(0).x() && pose.getY() == targets.get(0).y();
	}
	
	public void addObstacle(){
		Coordinate a = new Coordinate((int)pose.getX(), (int)pose.getY());
		pose.moveForward();
		Coordinate b = new Coordinate((int)pose.getX(), (int)pose.getY());
		pose.moveBackward();
		findPath.addObstacle(a, b);
		System.out.println("New obstacle added between: " + a + " and " + b);
	}
	
	/***
	 * Create an instance of an object that implements IGridMap from a LineMap.
	 * 
	 * @param _lineMap
	 *            The underlying line map
	 * @param _gridXSize
	 *            How many grid positions along the x axis
	 * @param _gridYSize
	 *            How many grid positions along the y axis
	 * @param _xStart
	 *            The x coordinate where grid position (0,0) starts
	 * @param _yStart
	 *            The y coordinate where grid position (0,0) starts
	 * @param _cellSize
	 *            The distance between grid positions
	 * @return The grid map.
	 */
	public static IGridMap createGridMap(RPLineMap _lineMap, int _gridXSize,
			int _gridYSize, float _xStart, float _yStart, float _cellSize) {
		return new GridMap(_gridXSize, _gridYSize, _xStart, _yStart,
				_cellSize, _lineMap);
	}
}