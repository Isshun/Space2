package org.bluebox.space2.path;

import java.util.Collection;
import java.util.List;

public class Graph {
  private final Collection<Vertex> vertexes;
  private final List<Edge> edges;

  public Graph(Collection<Vertex> collection, List<Edge> edges) {
    this.vertexes = collection;
    this.edges = edges;
  }

  public Collection<Vertex> getVertexes() {
    return vertexes;
  }

  public List<Edge> getEdges() {
    return edges;
  }
  
  
  
} 