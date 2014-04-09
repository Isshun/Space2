package org.bluebox.space2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bluebox.space2.model.SystemModel;
import org.bluebox.space2.model.TravelModel;
import org.bluebox.space2.path.DijkstraAlgorithm;
import org.bluebox.space2.path.Edge;
import org.bluebox.space2.path.Graph;
import org.bluebox.space2.path.Vertex;
import org.bluebox.space2.service.GameService;


public class PathResolver {

	private static PathResolver sSelf;
	
	private Map<SystemModel, Vertex> nodes;
	private List<Edge> edges;

	private PathResolver () {
		nodes = new HashMap<SystemModel, Vertex>();
		edges = new ArrayList<Edge>();

		List<SystemModel> systems = GameService.getInstance().getSystems();
		for (SystemModel system: systems) {
			Vertex location = new Vertex("Node_" + system.getId(), system.getName(), system);
	      nodes.put(system, location);
		}

		List<TravelModel> travels = GameService.getInstance().getTraveLines();
		for (TravelModel travel: travels) {
		    addLane(travel.getName(), travel.getFrom(), travel.getTo(), travel.getLength());
		    addLane(travel.getName(), travel.getTo(), travel.getFrom(), travel.getLength());
		}
	}

	private void addLane(String laneId, SystemModel s1, SystemModel s2, int duration) {
		Edge lane = new Edge(laneId, nodes.get(s1), nodes.get(s2), duration);
		edges.add(lane);
	}

	public static PathResolver getInstance () {
		if (sSelf == null) {
			sSelf = new PathResolver();
		}
		return sSelf;
	}

	public LinkedList<Vertex> getPath (SystemModel origin, SystemModel goal) {
		Graph graph = new Graph(nodes.values(), edges);
		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);

		System.out.println("Get path from " + nodes.get(origin).getName() + " to " + nodes.get(goal).getName());
		dijkstra.execute(nodes.get(origin));
		LinkedList<Vertex> path = dijkstra.getPath(nodes.get(goal));
	    
		if (path != null) {
			for (Vertex vertex : path) {
				System.out.println(vertex);
			}
		}
		
		return path;
	}		

}
