package followPath;

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
import lejos.nxt.comm.RConsole;
import lejos.robotics.navigation.Pose;

/**
 * Makes the robot go to a set of cooridnates.
 * 
 * 
 * @author Thomas Clarke, Rowan Cole and Kyle Allen-Taylor
 *
 */
public class FollowPath {
	
	private FindPath findPath;
	private Pose pose;
	private ArrayList<Coordinate> targets;
	
	public static void main(String [] args){
		
		RConsole.openBluetooth(0);
		PrintStream ps = RConsole.getPrintStream();
		System.setOut(ps);
		System.setErr(ps);
		new FollowPath();
	}
	
	/**
	 * Makes the robot travel to a set of coordinates.
	 */
	public FollowPath(){	
		
		RPLineMap lineMap = MapUtils.create2015Map1();

		int xJunctions = 14;
		int yJunctions = 8;
		float junctionSeparation = 30;

		int xInset = 15;
		int yInset = 15;

		IGridMap gridMap = createGridMap(lineMap, xJunctions, yJunctions,
				xInset, yInset, junctionSeparation);		
		
		this.pose = new Pose(0, 0, 90);
		findPath = new FindPath(gridMap);
		
		targets = new ArrayList<Coordinate>();
		targets.add(new Coordinate(0, 0));
		targets.add(new Coordinate(6, 2));
		targets.add(new Coordinate(9, 6));
		
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
			nodePath = findPath.getPath(new Coordinate((int)pose.getX(), (int)pose.getY()), targets.get(0));
			if (nodePath.isEmpty()) {
				System.out.println("CANNOT REACH TARGET NODE: " + targets.get(0) + ", skipping...");
				targets.remove(0);
				//Need to decide what happens if all targets have been met/not found.
			} else {
				ready = true;
			}
		}
		
		Node<Coordinate> previousLocation = nodePath.head();   //Would be easier if this modified nodePath to just the tail
		nodePath = nodePath.tail();
		ArrayList<Integer> movePath = new ArrayList<Integer>();
		float actualHeading = pose.getHeading();
		
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
				heading = 90;
			} else if(changeX == -1) {
				heading = -90;
			} else if(changeY == 1) {
				heading = 0;
			} else {
				heading = 180;
			}
			
			int headingChange = (int)(heading - actualHeading);
			
			if(headingChange == -90) {
				movePath.add(0);
			} else if(headingChange == 90) {
				movePath.add(2);
			} else {
				movePath.add(1);
			}
			
			actualHeading += headingChange;
			previousLocation = currentLocation;
		}
		
		System.out.println("directions to take = " + movePath);
		return movePath;
	}
	
	/**
	 * Returns the pose of the robot.
	 * 
	 * @return The pose.
	 */
	public Pose getPose(){
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
		pose.moveUpdate(1);
		Coordinate b = new Coordinate((int)pose.getX(), (int)pose.getY());
		pose.moveUpdate(-1);
		findPath.addObstacle(a, b);
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