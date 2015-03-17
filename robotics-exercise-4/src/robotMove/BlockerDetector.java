package robotMove;

import java.util.ArrayList;

import lejos.nxt.UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;

public class BlockerDetector implements Behavior {

	private ArrayList<Integer> pathToTake;
	private UltrasonicSensor sensorS;
	private boolean moving;

	/**
	 * Detects when the robot gets stuck, but only when not turning round.
	 * 
	 * @param sensorS The ultrasonic sensor.
	 * @param pathToTake The list of moves the robot must do.
	 * @param moving Whether the robot is moving, and hence if the the sensor should be detecting blockages.
	 */
	public BlockerDetector(UltrasonicSensor sensorS, ArrayList<Integer> pathToTake, boolean moving) {
		this.sensorS = sensorS;
		this.pathToTake = pathToTake;
		this.moving = moving;
	}

	@Override
	public boolean takeControl() {
		return moving && (sensorS.getDistance() > 30);
	}

	@Override
	public void action() {
		pathToTake.clear();
		moving = false;
	}

	@Override
	public void suppress() {
		//Nothing to do here.
	}

}
