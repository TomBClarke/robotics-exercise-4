package robotMove;

import java.util.ArrayList;

import followPath.FollowPath;
import lejos.nxt.Button;
import lejos.robotics.navigation.DifferentialPilot;
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
	private boolean suppressed;
	private final DifferentialPilot pilot;
	
	/**
	 * Creates the object.
	 * 
	 * @param followpath The class which holds the pose of the robot. 
	 * @param pathToTake The moves the robot must perform.
	 */
	public Stopped(FollowPath followpath, ArrayList<Integer> pathToTake, boolean suppressed, DifferentialPilot pilot) {
		this.followpath = followpath;
		this.pathToTake = pathToTake;
		this.suppressed = suppressed;
		this.pilot = pilot;
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
		} else {
			followpath.addObstacle();
			pathToTake = followpath.getPath();
		}
		
		Integer direction = pathToTake.get(0);
		pathToTake.remove(0);
		System.out.println("directio = " + direction);
		if(direction == 0) {
			pilot.rotate(90);
			followpath.getPose().rotateRight();
		} else if(direction == 2) {
			pilot.rotate(-90);
			followpath.getPose().rotateLeft();
		} else if(direction == 3) {
			pilot.rotate(180);
			followpath.getPose().rotate180();
		}
		
		suppressed = false;
	}

	@Override
	public void suppress() {
		//Noting to do here.
	}

}
