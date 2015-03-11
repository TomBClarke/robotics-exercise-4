package robotMove;

import java.util.LinkedList;

import lejos.robotics.subsumption.Behavior;

/**
 * This class handles the robot when there is no moves for it to do.
 * 
 * @author Tom
 *
 */
public class Stopped implements Behavior {

	private FollowPath followpath;
	private LinkedList<Integer> pathToTake;
	
	/**
	 * Creates the object.
	 * 
	 * @param followpath The class which holds the pose of the robot. 
	 * @param pathToTake The moves the robot must perform.
	 */
	public Stopped(FollowPath followpath, LinkedList<Integer> pathToTake){
		this.followpath = followpath;
		this.pathToTake = pathToTake;
	}
	
	@Override
	public boolean takeControl() {
		return pathToTake.isEmpty();
	}

	@Override
	public void action() {
		//NOTICE
		//this will work if FOllowPath immediatedly makes this with iteself, and as the pathTOtake will be empty it'll imediately come here, and ither populate this list or stop completely.
		if(followpath.isAtDestination()){
			//do next search or finish
		} else {
			//add broken path,
			//research
			//populate pathtotake
		}
	}

	@Override
	public void suppress() {
		//Noting to do here.
	}

}
