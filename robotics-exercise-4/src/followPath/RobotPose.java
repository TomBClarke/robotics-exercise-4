package followPath;

/**
 * Holds the pose of the robot.
 * 
 * @author Thomas Clarke, Rowan Cole and Kyle Allen-Taylor
 *
 */
public class RobotPose {

	public final static int POS_Y = 0;
	public final static int POS_X = 1;
	public final static int NEG_Y = 2;
	public final static int NEG_X = 3;
	
	private int x;
	private int y;
	private int heading;

	/**
	 * Holds the pose of the robot.
	 * 
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param heading The current heading of the robot.
	 */
	public RobotPose(int x, int y, int heading) {
		this.x = x;
		this.y = y;
		this.heading = heading;
	}
	
	/**
	 * Moves the robot forward by 1.
	 */
	public void moveForward() {
		if(heading == POS_Y) {
			y++;
		} else if (heading == POS_X) {
			x++;
		} else if (heading == NEG_Y) {
			y--;
		} else if (heading == NEG_X) {
			x--;
		} else {
			System.out.println("Error changing pose");
		}
	}
	
	/**
	 * Moves the robot back by 1.
	 */
	public void moveBackward() {
		if(heading == POS_Y) {
			y--;
		} else if (heading == POS_X) {
			x--;
		} else if (heading == NEG_Y) {
			y++;
		} else if (heading == NEG_X) {
			x++;
		} else {
			System.out.println("Error changing pose");
		}
	}
	
	/**
	 * Rotates the heading 90 to the left.
	 */
	public void rotateLeft() {
		if(heading == POS_Y) {
			heading = NEG_X;
		} else if (heading == POS_X) {
			heading = POS_Y;
		} else if (heading == NEG_Y) {
			heading = POS_X;
		} else if (heading == NEG_X) {
			heading = NEG_Y;
		} else {
			System.out.println("Error changing pose");
		}
	}
	
	/**
	 * Rotates the heading 90 to the right.
	 */
	public void rotateRight() {
		if(heading == POS_Y) {
			heading = POS_X;
		} else if (heading == POS_X) {
			heading = NEG_Y;
		} else if (heading == NEG_Y) {
			heading = NEG_X;
		} else if (heading == NEG_X) {
			heading = POS_Y;
		} else {
			System.out.println("Error changing pose");
		}
	}
	
	/**
	 * Reverses the robots heading.
	 */
	public void rotate180() {
		if(heading == POS_Y) {
			heading = NEG_Y;
		} else if (heading == POS_X) {
			heading = NEG_X;
		} else if (heading == NEG_Y) {
			heading = POS_Y;
		} else if (heading == NEG_X) {
			heading = POS_X;
		} else {
			System.out.println("Error changing pose");
		}
	}
	
	/**
	 * Gets the x coordinate of the robot.
	 * 
	 * @return The x coordinate.
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * The y coordinate of the robot.
	 * 
	 * @return The y coordinate.
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Gets the current heading of the robot. When used, the heading values at the top should be used, not literal numbers (e.g.: POS_Y instead of 0).
	 * 
	 * @return The heading.
	 */
	public int getHeading() {
		return heading;
	}
	
	public String toString() {
		return "(" + x + "," + y + ") " + heading;
	}
}
