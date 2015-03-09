package gridMap;

import lejos.geom.Line;
import lejos.geom.Point;
import lejos.robotics.navigation.Pose;
import rp.robotics.mapping.IGridMap;
import rp.robotics.mapping.RPLineMap;

/**
 * An implementation of IGridMap
 * 
 * @author Thomas Clarke, Rowan Cole, Kyle Allen-Taylor
 *
 */
public class GridMap implements IGridMap {
	
	private final int gridXSize;
	private final int gridYSize;
	private final float xStart;
	private final float yStart;
	private final float cellSize;
	private final RPLineMap lineMap;

	/**
	 * Creates a grid map implementation.
	 * 
	 * @param gridXSize The size of the grid in the x direction.
	 * @param gridYSize The sie of the grid in the y direction.
	 * @param xStart The offset to start counting x.
	 * @param yStart The offset to start counting y.
	 * @param cellSize The physical distance between 2 points.
	 * @param lineMap The linemap.
	 */
	public GridMap(int gridXSize, int gridYSize, float xStart, float yStart, float cellSize, RPLineMap lineMap){
		this.gridXSize = gridXSize;
		this.gridYSize = gridYSize;
		this.xStart = xStart;
		this.yStart = yStart;
		this.cellSize = cellSize;
		this.lineMap = lineMap;
	}

	@Override
	public int getXSize() {
		return gridXSize;
	}

	@Override
	public int getYSize() {
		return gridYSize;
	}

	@Override
	public boolean isValidGridPosition(int _x, int _y) {
		return _x < gridXSize && _x >= 0 && _y < gridYSize && _y >= 0;
	}

	@Override
	public boolean isObstructed(int _x, int _y) {
		return !lineMap.inside(getCoordinatesOfGridPosition(_x, _y));
	}

	@Override
	public Point getCoordinatesOfGridPosition(int _x, int _y) {
		return new Point(xStart + _x * cellSize, yStart + _y * cellSize);
	}

	@Override
	public boolean isValidTransition(int _x1, int _y1, int _x2, int _y2) {
		if(isObstructed(_x1, _y1) || isObstructed(_x2, _y2)){
			return false;
		} else if(Math.sqrt(Math.pow(_x1 - _x2, 2) + Math.pow( _y1 -_y2, 2))!=1) {
			return false;
		} else if(!isValidGridPosition(_x1, _y1) || !isValidGridPosition(_x2, _y2)){
			return false;
		} else {
			Point pointA = getCoordinatesOfGridPosition(_x1, _y1);
			Point pointB = getCoordinatesOfGridPosition(_x2, _y2);
			Line line1 = new Line(pointA.x, pointA.y, pointB.x, pointB.y);
			Line[] lines = lineMap.getLines();
			for (Line line2 : lines) {
				if(lineMap.intersectsAt(line1, line2) != null){
					return false;
				}
			}
			return true;
		}
	}

	@Override
	public float rangeToObstacleFromGridPosition(int _x, int _y, float _heading) {
		return lineMap.range(new Pose(_x, _y, _heading));
	}

}
