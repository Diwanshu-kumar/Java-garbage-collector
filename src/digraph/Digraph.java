package digraph;

import java.util.ArrayList;
import java.util.ListIterator;

public class Digraph {
	ArrayList<Vertex> vertices;

	public Digraph() {
		vertices = new ArrayList<>();
	}

	public boolean addEdge(String u_id, String v_id) {
		if (!hasVertex(u_id) || !hasVertex(v_id))
			return false;
		getVertex(u_id).addNeighbour(getVertex(v_id));
		return true;
	}

	public boolean addEdge(Vertex u, Vertex v) {
		if (!hasVertex(u) || !hasVertex(v))
			return false;
		u.addNeighbour(v);
		return true;
	}

	public boolean addVertex(String id) {
		if (!hasVertex(id)) {
			vertices.add(new Vertex(id));
			return true;
		}
		return false;
	}

	public boolean addVertex(Vertex v) {
		if (!hasVertex(v)) {
			vertices.add(v);
			return true;
		}
		return false;
	}

	public boolean adjacent(String u_id, String v_id) {
		if (!hasVertex(u_id))
			return false;
		return getVertex(u_id).adjacent(v_id);
	}

	public boolean adjacent(Vertex u, Vertex v) {
		if (!hasVertex(u))
			return false;
		return u.adjacent(v);
	}

	public Vertex getVertex(String id) {
		for (Vertex v : vertices)
			if (v.getID().compareTo(id) == 0)
				return v;
		return null;
	}

	public boolean hasVertex(String id) {
		for (Vertex v : vertices)
			if (v.getID().compareTo(id) == 0)
				return true;
		return false;
	}

	public boolean hasVertex(Vertex v) {
		return vertices.contains(v);
	}

	public int numEdges() {
		int sum = 0;
		for (Vertex v : vertices)
			sum += v.degree();
		return sum;
	}

	public void removeEdge(String u_id, String v_id) {
		removeEdge(getVertex(u_id), getVertex(v_id));
	}

	public void removeEdge(Vertex u, Vertex v) {
		if (u != null)
			u.removeNeighbour(v);
	}

	public void removeVertex(String id) {
		removeVertex(getVertex(id));
	}

	public void removeVertex(Vertex v) {
		for (Vertex u : vertices) {
			if (u.adjacent(v))
				u.removeNeighbour(v);
		}
		vertices.remove(v);
	}

	public int size() {
		return vertices.size();
	}

	public String toString() {
		String output = "";
		for (Vertex v : vertices) {
			output += v.getID() + ": ";
			ListIterator<Vertex> n = v.neighbours();
			while (n.hasNext()) {
				output += n.next().getID() + " ";
			}
			output += "\n";
		}
		return output;
	}

	public ListIterator<Vertex> vertices() {
		return vertices.listIterator();
	}
}