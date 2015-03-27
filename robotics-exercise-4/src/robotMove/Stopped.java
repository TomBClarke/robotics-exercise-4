package robotMove;

import java.util.ArrayList;

import followPath.FollowPath;
import lejos.nxt.Button;
import lejos.robotics.subsumption.Behavior;

/**
 * This class handles the robot when there is no moves for it to do.
 * 
 * @author Thomas Clarke
 *
 */
public class Stopped implements Behavior {

	private FollowPath followpath;
	private ArrayList<Integer> pathToTake;
	private final RobotRotate rotate;
	
	/**
	 * Creates the object.
	 * 
	 * @param followpath The class which holds the pose of the robot. 
	 * @param pathToTake The moves the robot must perform.
	 * @param rotate This rotates the robot, and updates the pose.
	 */
	public Stopped(FollowPath followpath, ArrayList<Integer> pathToTake, RobotRotate rotate) {
		this.followpath = followpath;
		this.pathToTake = pathToTake;
		this.rotate = rotate;
	}
	
	@Override
	public boolean takeControl() {
		return pathToTake.isEmpty();
	}

	@Override
	public void action() {
		ArrayList<Integer> tempPath;
		if(followpath.isAtDestination()) {
			System.out.println("Ready to go!");
			Button.waitForAnyPress();
			followpath.getTargets().remove(0);
			tempPath = followpath.getPath();
		} else {
			followpath.addObstacle();
			tempPath = followpath.getPath();
		}
		
		for(int i = 0; i < tempPath.size(); i++) {
			pathToTake.add(tempPath.get(i));
		}
		
		Integer direction = pathToTake.get(0);
		pathToTake.remove(0);
		pathToTake = rotate.rotate(direction);
	}

	@Override
	public void suppress() {
		//Noting to do here.
	}

}
