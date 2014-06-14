package org.bluebox.space2.path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bluebox.space2.game.model.ILocation;
import org.bluebox.space2.game.model.SystemModel;
import org.bluebox.space2.game.model.TravelModel;
import org.bluebox.space2.game.service.GameService;


public class PathResolver {

	private static PathResolver sSelf;
	
	private Map<SystemModel, Vertex> nodes;
	private List<Edge> edges;

	private void addLane(String laneId, SystemModel s1, SystemModel s2, int duration) {
		Edge lane = new Edge(laneId, nodes.get(s1), nodes.get(s2), duration);
		edges.add(lane);
	}
	
	public void init(List<SystemModel> systems, List<TravelModel> travels) {
		nodes = new HashMap<SystemModel, Vertex>();
		edges = new ArrayList<Edge>();

		for (SystemModel system: systems) {
			Vertex location = new Vertex("Node_" + system.getId(), system.getName(), system);
	      nodes.put(system, location);
		}

		for (TravelModel travel: travels) {
		    addLane(travel.getName(), travel.getFrom(), travel.getTo(), travel.getLength());
		    addLane(travel.getName(), travel.getTo(), travel.getFrom(), travel.getLength());
		}
	}

	public static PathResolver getInstance () {
		if (sSelf == null) {
			sSelf = new PathResolver();
		}
		return sSelf;
	}

	public LinkedList<Vertex> getPath (ILocation origin, ILocation goal) {
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
