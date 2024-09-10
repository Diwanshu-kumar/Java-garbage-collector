package memory;

import digraph.Digraph;
import digraph.Vertex;

import java.util.*;

public class GenerationalGarbageCollector {
    private Digraph youngGeneration;
    private Digraph oldGeneration;
    private Deque<Vertex> stackMemory;
    private HashMap<String, Vertex> markedObjects;
    private int youngCapacity;
    private int oldCapacity;

    public GenerationalGarbageCollector(int youngCapacity, int oldCapacity) {
        this.youngGeneration = new Digraph();
        this.oldGeneration = new Digraph();
        this.stackMemory = new LinkedList<>();
        this.markedObjects = new HashMap<>();
        this.youngCapacity = youngCapacity;
        this.oldCapacity = oldCapacity;
    }

    public void addHeapObject(String id) {
        if (!youngGeneration.hasVertex(id) && !oldGeneration.hasVertex(id)) {
            if (youngGeneration.size() < youngCapacity) {
                youngGeneration.addVertex(id);
            } else {
                collectGarbage(youngGeneration);
                youngGeneration.addVertex(id);
            }
        }
    }

    public void addReference(String fromId, String toId) {
        if (isInMemory(fromId) && isInMemory(toId)) {
            getGeneration(fromId).addEdge(fromId, toId);
        }
    }

    public void removeReference(String fromId, String toId) {
        if (isInMemory(fromId) && isInMemory(toId)) {
            getGeneration(fromId).removeEdge(fromId, toId);
        }
    }

    public void addStackObject(String id) {
        if (!stackMemory.contains(id)) {
            stackMemory.add(new Vertex(id));
        }
    }

    public void collectGarbage(Digraph generation) {
        mark();
        sweep(generation);
        promote();
    }

    private void mark() {
        markedObjects.clear();
        for (Vertex root : stackMemory) {
            markObject(root);
        }
    }

    private void markObject(Vertex vertex) {
        if (vertex == null || markedObjects.containsKey(vertex.getID())) {
            return;
        }
        markedObjects.put(vertex.getID(), vertex);
        for (ListIterator<Vertex> it = vertex.neighbours(); it.hasNext(); ) {
            Vertex neighbour = it.next();
            markObject(neighbour);
        }
    }

    private void sweep(Digraph generation) {
        ListIterator<Vertex> iterator = generation.vertices();
        ArrayList<String> vertices = new ArrayList<>();
        while (iterator.hasNext()) {
            vertices.add(iterator.next().getID());
        }
        for (String vertex : vertices) {
            if (!markedObjects.containsKey(vertex)) {
                generation.removeVertex(vertex);
            }
        }
    }

    private void promote() {
        ListIterator<Vertex> iterator = youngGeneration.vertices();
        ArrayList<String> vertices = new ArrayList<>();
        while (iterator.hasNext()) {
            vertices.add(iterator.next().getID());
        }
        for (String vertex : vertices) {
            if (markedObjects.containsKey(vertex)) {
                if (oldGeneration.size() < oldCapacity) {
                    oldGeneration.addVertex(vertex);
                } else {
                    collectGarbage(oldGeneration);
                    if(oldCapacity<oldGeneration.size()){
                        throw new OutOfMemoryError("heap memory limit exceeded");
                    }
                    oldGeneration.addVertex(vertex);
                }
                youngGeneration.removeVertex(vertex);
            }
        }
    }

    public Digraph getGeneration(String id) {
        if (youngGeneration.hasVertex(id)) {
            return youngGeneration;
        } else {
            return oldGeneration;
        }
    }

    public boolean isInMemory(String id) {
        return youngGeneration.hasVertex(id) || oldGeneration.hasVertex(id);
    }

    public int memUse() {
        return youngGeneration.size() + oldGeneration.size() + stackMemory.size();
    }
}