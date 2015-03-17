package robotMove;

import java.util.ArrayList;

import lejos.nxt.LightSensor;
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
	private boolean moving;

	/**
	 * Allows the class access to the sensors and pilot.
	 * 
	 * @param pilot The pilot controlling the robot.
	 * @param sensorL The left light sensor.
	 * @param sensorR The right light sensor.
	 * @param moving Used to describe if the robot is moving.
	 */
	public GridFollower(DifferentialPilot pilot, LightSensor sensorL, LightSensor sensorR, ArrayList<Integer> pathToTake, boolean moving) {
		this.lf = new LineFollower(pilot, sensorL, sensorR);
		suppressed = false;
		this.pathToTake = pathToTake;
		this.moving = moving;
	}

	@Override
	public boolean takeControl() {
		return !pathToTake.isEmpty();
	}

	@Override
	public void action() {
		//not sure if this fixes the issue, needs testing on the actual robot, with a proper distance used.
		//moving = true;
		
		//moving = false;
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
