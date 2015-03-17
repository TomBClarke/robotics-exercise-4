package followPath;

import ilist.*;
import robotSearches.*;
import rp.robotics.mapping.IGridMap;
import maybe.*;

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
				if (gridMap.isValidGridPosition(i, j)
						&& !gridMap.isObstructed(i, j)) {
					Coordinate c = new Coordinate(i, j);
					Node<Coordinate> node = graph.nodeWith(c);
					// checks the 4 points around the current point to check if
					// there is a valid transition
					for (int k = i - 1; k < i + 2; k +=2) {
						if (gridMap.isValidTransition(i, j, k, j)) {
							node.addSuccessor(graph.nodeWith(new Coordinate(k, j)));
						}
					}
					for (int k = j - 1; k < j + 2; k +=2) {
						if (gridMap.isValidTransition(i, j, i, k)) {
							node.addSuccessor(graph.nodeWith(new Coordinate(i, k)));
						}
					}
				}
			}
		}
		
		for (Node<Coordinate> node : graph.nodes()) {
			System.out.print("(" + node.contents().x() + ","
					+ node.contents().y() + "): ");
			for (Node<Coordinate> s : node.successors()) {
				System.out.print("(" + s.contents().x() + ","
						+ s.contents().y() + "), ");
			}
			System.out.println();
		}
	}

	public IList<Node<Coordinate>> getPath(Coordinate origin, Coordinate coordinate) {
		Maybe<IList<Node<Coordinate>>> path = graph.findPathFrom(graph.nodeWith(origin), graph.nodeWith(coordinate));
		System.out.println(path.fromMaybe());
		return path.fromMaybe();
	}

	public void addObstacle(Coordinate a, Coordinate b) {
		Node<Coordinate> nodeA = graph.nodeWith(a);
		Node<Coordinate> nodeB = graph.nodeWith(b);

		nodeA.successors().remove(nodeB);
		nodeB.successors().remove(nodeA);
	}
}
