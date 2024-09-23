import java.util.ArrayList;
import java.util.List;

class Node {
    private final String name;
    private final List<Node> neighbors;  // List to store neighbors of the node

    public Node(String name) {
        this.name = name;
        this.neighbors = new ArrayList<>();
    }

    public void addNode(Node neighbor) {
        neighbors.add(neighbor);
    }

    public Node getNode(int index) {
        if (index >= 0 && index < neighbors.size()) {
            return neighbors.get(index);
        } else {
            return null;
        }
    }

    public void printNodes() {
        System.out.print("Node " + name + " has neighbors: ");
        for (Node neighbor : neighbors) {
            System.out.print(neighbor.name + " ");
        }
        System.out.println();
    }}
