package robotMove;

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

	private final LineFollower lf;

	/**
	 * Allows the class access to the sensors and pilot.
	 * 
	 * @param pilot The pilot controlling the robot.
	 * @param sensorL The left light sensor.
	 * @param sensorR The right light sensor.
	 * @param moving Used to describe if the robot is moving.
	 */
	public GridFollower(DifferentialPilot pilot, LightSensor sensorL, LightSensor sensorR) {
		this.lf = new LineFollower(pilot, sensorL, sensorR);
	}

	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		lf.checkLine();
	}

	@Override
	public void suppress() {
		//Nothing to do here.s
	}
}
