package robotMove;

import java.util.ArrayList;
import java.util.LinkedList;

import followPath.FollowPath;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Pose;
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
		boolean moving = false;
		
		ArrayList<Integer> pathToTake = new ArrayList<Integer>();
		
		Delay.msDelay(500);
		
		LineFollower.calibrate(sensorL, sensorR);
		
		int speed = 100;
		pilot.setTravelSpeed(speed);
		pilot.setRotateSpeed(speed);
		
		Arbitrator arby = new Arbitrator(new Behavior[] {
			new Stopped(followpath, pathToTake),
			new GridFollower(pilot, sensorL, sensorR, pathToTake, moving),
			new JunctionBehavior(pilot, sensorL, sensorR, pathToTake, followpath), 
			new BlockerDetector(sensorS, pathToTake, moving)
			});
		arby.start();
	}
	
}
