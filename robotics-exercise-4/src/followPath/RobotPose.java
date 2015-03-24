package followPath;

public class RobotPose {

	public final static int POS_Y = 0;
	public final static int POS_X = 1;
	public final static int NEG_Y = 2;
	public final static int NEG_X = 3;
	
	private int x;
	private int y;
	private int heading;

	public RobotPose(int x, int y, int heading) {
		this.x = x;
		this.y = y;
		this.heading = heading;
	}
	
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
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int heading() {
		return heading;
	}
	
	public String toString() {
		return "(" + x + "," + y + ") " + heading;
	}
}
