package gridMap;

import lejos.geom.Point;
import rp.robotics.mapping.IGridMap;

public class GridMap implements IGridMap {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getXSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getYSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isValidGridPosition(int _x, int _y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isObstructed(int _x, int _y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Point getCoordinatesOfGridPosition(int _x, int _y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isValidTransition(int _x1, int _y1, int _x2, int _y2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public float rangeToObstacleFromGridPosition(int _x, int _y, float _heading) {
		// TODO Auto-generated method stub
		return 0;
	}

}
