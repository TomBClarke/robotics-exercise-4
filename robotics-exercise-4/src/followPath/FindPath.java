package followPath;

import ilist.*;
import robotSearches.*;
import rp.robotics.mapping.IGridMap;

/**
 * Provides functionality to generate the graph, control its nodes and perform searches.
 * 
 * 
 * @author Thomas Clarke, Rowan Cole and Kyle Allen-Taylor
 *
 */
public class FindPath {

	private Graph<Coordinate> graph;

	/**
	 * This shall setup the grid map for the robot so a search can be computed.
	 * 
	 * @param gridMap
	 */
	public FindPath(IGridMap gridMap) {
		graph = new Graph<Coordinate>();
		
		for (int i = 0; i < gridMap.getXSize(); i++) {
			for (int j = 0; j < gridMap.getYSize(); j++) {
				
				if (gridMap.isValidGridPosition(i, j) && !gridMap.isObstructed(i, j)) {
					Coordinate c = new Coordinate(i, j);
					Node<Coordinate> node = graph.nodeWith(c);

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
		
		//Prints the map.
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

	/**
	 * Returns the list of coordinates the robot shall visit on its way to the destination.
	 * 
	 * @param origin Where the robot is starting from.
	 * @param coordinate Where the robot wants to get to.
	 * @return The list of coordinates it shall visit,
	 */
	public IList<Node<Coordinate>> getPath(Coordinate origin, Coordinate coordinate) {
		return graph.findPathFrom(graph.nodeWith(origin), graph.nodeWith(coordinate));
	}

	/**
	 * Deletes a connection between two nodes.
	 * 
	 * @param a One node (should be the one the robot is at).
	 * @param b The other node (should be the one the robot is looking at).
	 */
	public void addObstacle(Coordinate a, Coordinate b) {
		Node<Coordinate> nodeA = graph.nodeWith(a);
		Node<Coordinate> nodeB = graph.nodeWith(b);

		nodeA.successors().remove(nodeB);
		nodeB.successors().remove(nodeA);
	}
}
