package followPath;

import java.util.ArrayList;

import gridMap.GridMap;
import ilist.IList;
import robotMove.GridNavigator;
import robotSearches.*;
import rp.robotics.mapping.IGridMap;
import rp.robotics.mapping.MapUtils;
import rp.robotics.mapping.RPLineMap;
import lejos.robotics.navigation.Pose;

public class FollowPath {
	
	private FindPath findPath;
	private GridNavigator robotMove;
	private Pose pose;
	private ArrayList<Coordinate> targets;
	
	public static void main(String [] args){
		new FollowPath();
	}
	
	public FollowPath(){
		
		RPLineMap lineMap = MapUtils.create2015Map1();

		// grid map dimensions for this line map
		int xJunctions = 14;
		int yJunctions = 8;
		float junctionSeparation = 30;

		// position of grid map 0,0
		int xInset = 15;
		int yInset = 15;

		IGridMap gridMap = createGridMap(lineMap, xJunctions, yJunctions,
				xInset, yInset, junctionSeparation);		
		
		
		this.pose = new Pose(0, 0, 0);
		//Need to find out how Pose is defined
		findPath = new FindPath(gridMap);    //Need to create lineMap
		targets = new ArrayList<Coordinate>();
		targets.add(new Coordinate(0,0));
		targets.add(new Coordinate(9,6));
		GridNavigator robotMove = new GridNavigator(this);
	}
	
	public ArrayList<Integer> getPath(){
		IList<Node<Coordinate>> nodePath = findPath.getPath(new Coordinate((int)pose.getX(), (int)pose.getY()), targets.get(0));
		Node<Coordinate> previousLocation = nodePath.head();   //Would be easier if this modified nodePath to just the tail
		nodePath = nodePath.tail();
		ArrayList<Integer> movePath = new ArrayList<Integer>();
		while(!nodePath.isEmpty()){
			int initialX = previousLocation.contents().x();
			int initialY = previousLocation.contents().y();
			Node<Coordinate> currentLocation = nodePath.head();
			nodePath = nodePath.tail();
			int x = currentLocation.contents().x();
			int y = currentLocation.contents().y();
			int changeX = x-initialX;
			int changeY = y-initialY;
			int heading;
			if(changeX==1) {
				heading = 90;
			} else if(changeX==-1) {
				heading = -90;
			} else if(changeY==1) {
				heading = 0;
			} else {
				heading = 180;
			}
			int headingChange = (int)(heading - pose.getHeading());
			if(headingChange==-90){
				movePath.add(0);
			}
			else if(headingChange==90){
				movePath.add(2);
			}
			else{
				movePath.add(1);
			}
		}
		return movePath;
	}
	
	public Pose getPose(){
		return pose;
	}
	
	public ArrayList<Coordinate> getTargets(){
		return targets;
	}
	
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
	 * You don't need to use this method, but it's a useful way for me to
	 * document the parameters you might need.
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
	 * @return
	 */
	public static IGridMap createGridMap(RPLineMap _lineMap, int _gridXSize,
			int _gridYSize, float _xStart, float _yStart, float _cellSize) {
		return new GridMap(_gridXSize, _gridYSize, _xStart, _yStart,
				_cellSize, _lineMap);
	}
}