package robotMove;

import java.util.ArrayList;

import followPath.FollowPath;
import lejos.nxt.LightSensor;
import lejos.nxt.Sound;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;

/**
 * Control the robot when it reaches a junction.
 * 
 * @author Thomas Clarke, Rowan Cole and Kyle Allen-Taylor
 *
 */
public class JunctionBehavior implements Behavior {
	
	private final DifferentialPilot pilot;
	private final LightSensor sensorL;
	private final LightSensor sensorR;
	private ArrayList<Integer> pathToTake;
	private FollowPath followpath;
	private UltrasonicSensor sensorS;
	private RobotRotate rotate;
	
	/**
	 * Allows the class access to the sensors and pilot.
	 * 
	 * @param pilot The pilot controlling the robot.
	 * @param sensorL The left light sensor.
	 * @param sensorR The right light sensor.
	 * @param followpath This holds information about the post of the robot.
	 */
	public JunctionBehavior(DifferentialPilot pilot, LightSensor sensorL, LightSensor sensorR, UltrasonicSensor sensorS, ArrayList<Integer> pathToTake, FollowPath followpath, RobotRotate rotate) {
		this.pilot= pilot;
		this.sensorL = sensorL;
		this.sensorR = sensorR;
		this.sensorS = sensorS;
		this.pathToTake = pathToTake;
		this.followpath = followpath;
		this.rotate = rotate;
	}

	@Override
	public boolean takeControl() {
		 return (sensorL.getLightValue() < 50 && sensorR.getLightValue() < 50 && !pathToTake.isEmpty());
	}

	@Override
	public void action() {
		
		followpath.getPose().moveForward();
		
		pilot.travel(100);
		Integer direction = pathToTake.get(0);
		pathToTake.remove(0);
		rotate.rotate(direction);
		
		if(pathToTake.isEmpty()) {
			Sound.setVolume(Sound.VOL_MAX);
			Sound.beepSequence();
			Delay.msDelay(1000);
		} else {
			int readings = 0;
			int repeats = 10;
			
			for(int i = 0; i < repeats; i++) {
				readings += sensorS.getDistance();
			}
			
			readings = readings / repeats;
			
			if(readings < 25) {
				pathToTake.clear();
			}
		}

		System.out.println("Pose: " + followpath.getPose());
	}

	@Override
	public void suppress() {
		//Nothing to do here.
	}
}
