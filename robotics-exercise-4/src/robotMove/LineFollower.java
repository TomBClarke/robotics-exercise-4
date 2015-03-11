package robotMove;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;

/**
 * 
 * Follows a line.
 * 
 * @author Thomas Clarke, Rowan Cole and Kyle Allen-Taylor
 *
 */
public class LineFollower {
	
	private final DifferentialPilot pilot;
	private final LightSensor sensorL;
	private final LightSensor sensorR;

	/**
	 * Allows the robot to follow a line using two light sensors.
	 * 
	 * @param pilot The pilot controlling the robot.
	 * @param sensorL The left light sensor.
	 * @param sensorR The right light sensor.
	 */
	public LineFollower(DifferentialPilot pilot, LightSensor sensorL, LightSensor sensorR){
		this.pilot = pilot;
		this.sensorL = sensorL;
		this.sensorR = sensorR;
	}
	
	/**
	 * Makes the robot follow a line.
	 */
	public void run(){
		
		calibrate(sensorL, sensorR);
		
		pilot.setTravelSpeed(130);
		while(true){
				checkLine();
		}
	}
	
	/**
	 * Calibrates the robots light sensors individually.
	 * 
	 * @param sL The left light sensor.
	 * @param sR The right light sensor.
	 */
	public static void calibrate(LightSensor sL, LightSensor sR){
		int sumLl = 0;
		int sumRl = 0;
		int sumLd = 0;
		int sumRd = 0;
		
		System.out.println("Light surface calibration...");
		Button.waitForAnyPress();
		for(int i = 0; i < 5; i++){
			sumLl += sL.getNormalizedLightValue();
			sumRl += sR.getNormalizedLightValue();
		}
		
		System.out.println("Dark surface calibration...");
		Button.waitForAnyPress();
		for(int i = 0; i < 5; i++){
			sumLd += sL.getNormalizedLightValue();
			sumRd += sR.getNormalizedLightValue();
		}
		
		sL.setHigh(sumLl / 5);
		sR.setHigh(sumRl / 5);
		sL.setLow(sumLd / 5);
		sR.setLow(sumRd / 5);
		
		System.out.println("Light values: Left = " + (sumLl / 5) + " Right = " + (sumRl / 5));
		System.out.println("Dark values: Left = " + (sumLd / 5) + " Right = " + (sumRd / 5));
		System.out.println("Done! Press when ready to continue.");
		Button.waitForAnyPress();
	}
	
	/**
	 * Changes the motion of the robot to follow a line.
	 */
	public void checkLine(){
		pilot.forward();
		int turnTime = 50;
		int turnAmount = 200;
		//System.out.println("L = " + sensorL.getLightValue() + ", R = " + sensorR.getLightValue());
		if(sensorL.getLightValue() < 50){
			pilot.steer(turnAmount);
			Delay.msDelay(turnTime);
			pilot.forward();
		}
		if(sensorR.getLightValue() < 50){
			pilot.steer(-turnAmount);
			Delay.msDelay(turnTime);
			pilot.forward();
		}
	}
	
	public static void main(String[] args) {
		System.out.println("Line follower ready. Press any button to begin.");
		Button.waitForAnyPress();
		
		DifferentialPilot pilot = new DifferentialPilot(88.0, 162, Motor.C, Motor.B);
		LightSensor sensorL = new LightSensor(SensorPort.S2, true);
		LightSensor sensorR = new LightSensor(SensorPort.S3, true);
		
		LineFollower lf = new LineFollower(pilot, sensorL, sensorR);
		
		lf.run();
	}
}
