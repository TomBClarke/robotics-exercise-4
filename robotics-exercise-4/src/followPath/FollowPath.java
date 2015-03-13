package followPath;

import java.util.ArrayList;

import ilist.IList;
import robotMove.GridNavigator;
import robotSearches.*;
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
		this.pose = new Pose(0, 0, 0);    //Need to find out how Pose is defined
		findPath = new FindPath(lineMap);    //Need to create lineMap
		targets = new ArrayList<Coordinate>();
		targets.add(new Coordinate(0,0));
		targets.add(new Coordinate(9,6));
		GridNavigator robotMove = new GridNavigator(this);
	}
	
	public ArrayList<Integer> getPath(){
		IList<Node<Coordinate>> nodePath = FindPath.getPath();
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
	
	public boolean isAtDestination(){
		return pose.getX() == targets.get(0).x() && pose.getY() == targets.get(0).y();
	}
	
	public void addObstacle(Coordinate point1, Coordinate point2){
		
	}
}