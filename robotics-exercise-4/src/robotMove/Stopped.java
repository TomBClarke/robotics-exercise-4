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
	
	/**
	 * Creates the object.
	 * 
	 * @param followpath The class which holds the pose of the robot. 
	 * @param pathToTake The moves the robot must perform.
	 */
	public Stopped(FollowPath followpath, ArrayList<Integer> pathToTake){
		this.followpath = followpath;
		this.pathToTake = pathToTake;
	}
	
	@Override
	public boolean takeControl() {
		return pathToTake.isEmpty();
	}

	@Override
	public void action() {
		if(followpath.isAtDestination()) {
			System.out.println("Ready to go!");
			Button.waitForAnyPress();
			followpath.getTargets().remove(0);
			pathToTake = followpath.getPath();
			System.out.println("following path");
		} else {
			followpath.addObstacle();
			pathToTake = followpath.getPath();
		}
	}

	@Override
	public void suppress() {
		//Noting to do here.
	}

}
