package robotMove;

import java.util.ArrayList;

import followPath.FollowPath;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;

/**
 * 
 * Allows the robot to turn at junctions of lines.
 * 
 * @author Thomas Clarke, Rowan Cole and Kyle Allen-Taylor
 *
 */
public class GridNavigator {

	/**
	 * Allows the robot to turn  when it hits a junction between lines.
	 * 
	 * @param followpath The class that holds the pose and search object in the robot.
	 */
	public GridNavigator(FollowPath followpath){
		
		DifferentialPilot pilot = new DifferentialPilot(88.0, 162, Motor.C, Motor.B);
		LightSensor sensorL = new LightSensor(SensorPort.S2, true);
		LightSensor sensorR = new LightSensor(SensorPort.S3, true);
		UltrasonicSensor sensorS = new UltrasonicSensor(SensorPort.S1);
		
		int speed = 100;
		pilot.setTravelSpeed(speed);
		pilot.setRotateSpeed(speed);
		
		ArrayList<Integer> pathToTake = new ArrayList<Integer>();
		
		Delay.msDelay(500);
		
		LineFollower.calibrate(sensorL, sensorR);
		
		followpath.getTargets().remove(0);
		pathToTake = followpath.getPath();

		Integer direction = pathToTake.get(0);
		pathToTake.remove(0);
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
		
		Arbitrator arby = new Arbitrator(new Behavior[] {
			new GridFollower(pilot, sensorL, sensorR),
			new Stopped(followpath, pathToTake, pilot),
			new JunctionBehavior(pilot, sensorL, sensorR, sensorS, pathToTake, followpath), 
			});
		
		arby.start();
	}
	
}
