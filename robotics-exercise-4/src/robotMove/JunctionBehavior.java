package robotMove;

import java.util.ArrayList;
import java.util.Arrays;

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
	 * @param pilot This controls the robot.
	 * @param sensorL The left light sensor.
	 * @param sensorR The right light sensor.
	 * @param sensorS The ultrasonic sensor.
	 * @param pathToTake The list of turns to make.
	 * @param followpath This holds information about the post of the robot.
	 * @param rotate This makes the robot rotate and updates the pose.
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
			int repeats = 10;
			int[] readings = new int[repeats];
			
			for(int i = 0; i < repeats; i++) {
				readings[i] = sensorS.getDistance();
			}

			Arrays.sort(readings);
			
			String list = "";
			for(int i = 0; i < readings.length; i++) {
				list += "," + readings[i];
			}
			
			System.out.println("Readings: " + list);
			
			int reading = 0;
			
			for(int i = 2; i < repeats - 2; i++) {
				reading += readings[i];
			}
			
			reading = reading / (repeats - 4);
			
			if(reading < 20) {
				Sound.setVolume(Sound.VOL_MAX);
				Sound.buzz();
				Delay.msDelay(1000);
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
