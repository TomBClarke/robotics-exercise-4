package followPath;

import ilist.*;
import robotSearches.*;
import rp.robotics.mapping.IGridMap;

public class FindPath {

	private Graph<Coordinate> graph;

	public FindPath(IGridMap gridmap) {
		graph = new Graph<Coordinate>();

		for (int i = 0; i < gridmap.getXSize(); i++) {
			for (int j = 0; j < gridmap.getYSize(); j++) {
				// make all coords
			}
		}
	}

	public IList<Node<Coordinate>> getPath(Coordinate origin, Coordinate coordinate) {

		AStarFunctions<Node<Coordinate>> functions = new AStarFunctions<Node<Coordinate>>() {

			@Override
			public double distanceFunction(Node<Coordinate> node1,
					Node<Coordinate> node2) {
				if (node1.successors().contains(node2))
					return 1.0;
				else
					return Double.POSITIVE_INFINITY;
			}

			@Override
			public double heuristicFunction(Node<Coordinate> node1,Node<Coordinate> node2) {
				Coordinate c1 = (Coordinate) node1.contents();
				Coordinate c2 = (Coordinate) node2.contents();

				return Math.sqrt(Math.pow((c1.x() - c2.x()), 2) + Math.pow(c1.y() - c2.y(), 2));
			}
		};

		return graph.findPathFromGeneralisation(graph.nodeWith(origin), graph.nodeWith(coordinate), functions, new MyPriorityQueue<Node<Coordinate>>(functions,graph.nodeWith(new Coordinate(9,0)))).fromMaybe();
	}
	
	public void addObstacle(Coordinate a, Coordinate b){
		Node<Coordinate> nodeA = graph.nodeWith(a);
		Node<Coordinate> nodeB = graph.nodeWith(b);
		
		nodeA.successors().remove(nodeB);
		nodeB.successors().remove(nodeA);
	}
}
