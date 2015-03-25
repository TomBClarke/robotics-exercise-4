package robotMove;

import followPath.FollowPath;
import lejos.nxt.LightSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;

/**
 * Allows easy control of the robots rotation.
 * 
 * @author Thomas Clarke, Rowan Cole and Kyle Allen-Taylor
 *
 */
public class RobotRotate {
	
	private final DifferentialPilot pilot;
	private final LightSensor sensorL;
	private final LightSensor sensorR;
	private FollowPath followpath;

	/**
	 * Creates an object that can rotate the robot and update the pose correctly.
	 * 
	 * @param followpath This holds the pose.
	 * @param pilot The makes the robot rotate.
	 * @param sensorL The left light sensor.
	 * @param sensorR The right light sensor.
	 */
	public RobotRotate(FollowPath followpath, DifferentialPilot pilot, LightSensor sensorL, LightSensor sensorR) {
		this.followpath = followpath;
		this.pilot = pilot;
		this.sensorL = sensorL;
		this.sensorR = sensorR;
	}
	
	/**
	 * This makes the robot rotate.
	 * 
	 * @param direction How far it should rotatre (0 for -90, 2 for 90, and 3 for 180).
	 */
	public void rotate(int direction) {
		int delay = 200;
		if(direction == 0) {
			pilot.rotate(45);
			pilot.rotateLeft();
			boolean turn = true;
			while(turn) {
				if(sensorL.getLightValue() < 50) {
					turn = false;
					Delay.msDelay(delay);
					pilot.stop();
				}
			}
			followpath.getPose().rotateLeft();
			
		} else if(direction == 2) {
			pilot.rotate(-45);
			pilot.rotateRight();
			boolean turn = true;
			while(turn) {
				if(sensorR.getLightValue() < 50) {
					turn = false;
					Delay.msDelay(delay);
					pilot.stop();
				}
			}
			followpath.getPose().rotateRight();
			
		} else if(direction == 3) {
			for(int i = 0; i < 2; i++) {
				pilot.rotate(-45);
				pilot.rotateRight();
				boolean turn = true;
				while(turn) {
					if(sensorR.getLightValue() < 50) {
						turn = false;
						Delay.msDelay(delay);
						pilot.stop();
					}
				}
			}
			followpath.getPose().rotate180();
		}
	}
}
