package robotMove;

import java.util.ArrayList;

import lejos.nxt.LightSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Behavior;

/**
 * Allows the robot to follow a line when ot at a junction.
 * 
 * @author Thomas Clarke, Rowan Cole and Kyle Allen-Taylor
 *
 */
public class GridFollower implements Behavior {

	private boolean suppressed;
	private final LineFollower lf;
	private ArrayList<Integer> pathToTake;
	private UltrasonicSensor sensorS;

	/**
	 * Allows the class access to the sensors and pilot.
	 * 
	 * @param pilot The pilot controlling the robot.
	 * @param sensorL The left light sensor.
	 * @param sensorR The right light sensor.
	 * @param moving Used to describe if the robot is moving.
	 */
	public GridFollower(DifferentialPilot pilot, LightSensor sensorL, LightSensor sensorR, ArrayList<Integer> pathToTake, UltrasonicSensor sensorS, boolean suppressed) {
		this.lf = new LineFollower(pilot, sensorL, sensorR);
		this.pathToTake = pathToTake;
		this.sensorS = sensorS;
		this.suppressed = suppressed;
	}

	@Override
	public boolean takeControl() {
		return !pathToTake.isEmpty();
	}

	@Override
	public void action() {
//		int readings = 0;
//		int repeats = 5;
//		
//		for(int i = 0; i < repeats; i++) {
//			readings += sensorS.getDistance();
//		}
//		
//		readings = readings / repeats;
//		
//		if(readings < 32) {
//			suppressed = true;
//			pathToTake.clear();
//		}
		
		while(!suppressed){
			lf.checkLine();
		}
		
		suppressed = false;
	}

	@Override
	public void suppress() {
		suppressed = true;
	}
}
