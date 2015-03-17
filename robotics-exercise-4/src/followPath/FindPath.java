package followPath;

import ilist.*;
import robotSearches.*;
import rp.robotics.mapping.IGridMap;

public class FindPath {

	private Graph<Coordinate> graph;

	public FindPath(IGridMap gridMap) {
		graph = new Graph<Coordinate>();

		// creates the nodes required for the search
		// loops through all of the points in the array.
		for (int i = 0; i < gridMap.getXSize(); i++) {
			for (int j = 0; j < gridMap.getYSize(); j++) {
				// checks if it is a valid grid position and if the point is
				// obstructed.
				if (gridMap.isValidGridPosition(i, j) && !gridMap.isObstructed(i, j)) {
					Coordinate c = new Coordinate(i, j);
					Node<Coordinate> node = graph.nodeWith(c);
					// checks the 4 points around the current point to check if
					// there is a valid transition
					for (int k = i - 1; k < i + 2; k += 2) {
						for (int l = j - 1; l < j + 2; l += 2) {
							if (gridMap.isValidTransition(i, j, k, l)) {
								Node<Coordinate> node2 = graph.nodeWith((Coordinate) new Coordinate(k, l));
								node.addSuccessor(node2);
								node2.addSuccessor(node);
							}
						}
					}
				}
			}
		}
	}

	public IList<Node<Coordinate>> getPath(Coordinate origin, Coordinate coordinate) {

		Predicate<Coordinate> condition = new Predicate<Coordinate>() {
			@Override
			public boolean holds(Coordinate a) {
				return a.equals(coordinate);
			}
		};

		return graph.findPathFrom(graph.nodeWith(origin), condition).fromMaybe();
	}

	public void addObstacle(Coordinate a, Coordinate b) {
		Node<Coordinate> nodeA = graph.nodeWith(a);
		Node<Coordinate> nodeB = graph.nodeWith(b);

		nodeA.successors().remove(nodeB);
		nodeB.successors().remove(nodeA);
	}
}
