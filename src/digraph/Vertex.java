package digraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ListIterator;

public class Vertex {
	String id;
	ArrayList<Vertex> neighbours;

	public Vertex(String id) {
		this.id = id;
		neighbours = new ArrayList<>();
	}

	public void addNeighbour(Vertex v) {
		neighbours.add(v);
	}

	public void addNeighbours(Collection<Vertex> c) {
		for (Vertex v : c)
			addNeighbour(v);
	}

	public boolean adjacent(String id) {
		return findNeighbour(id) != null;
	}

	public boolean adjacent(Vertex v) {
		return neighbours.contains(v);
	}

	public int compareTo(Vertex v) {
		return id.compareTo(v.getID());
	}

	public int degree() {
		return neighbours.size();
	}

	public Vertex findNeighbour(String id) {
		for (Vertex v : neighbours) {
			if (v.getID().compareTo(id) == 0)
				return v;
		}
		return null;
	}

	public String getID() {
		return id;
	}

	public ListIterator<Vertex> neighbours() {
		return neighbours.listIterator();
	}

	public void removeNeighbour(String id) {
		removeNeighbour(findNeighbour(id));
	}

	public void removeNeighbour(Vertex v) {
		neighbours.remove(v);
	}

	@Override
	public String toString() {
		return "Vertex{" +
				"id='" + id + '\'' +
				", neighbours=" + neighbours +
				'}';
	}
}