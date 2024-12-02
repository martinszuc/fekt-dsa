package org.example;

import java.util.ArrayList;
import java.util.List;

// Center Class
class Center {
    private final List<ValueNodePair> nodeList; // List to hold value-node pairs

    public Center() {
        nodeList = new ArrayList<>();
    }

    // Add a value and its corresponding node to the list
    public void add(String value, Node node) {
        nodeList.add(new ValueNodePair(value, node));
    }

    // Retrieve the node by its value
    public Node getNode(String value) {
        for (ValueNodePair pair : nodeList) {
            if (pair.value.equals(value)) {
                return pair.node;
            }
        }
        return null; // Return null if not found
    }

    // Print the stored mappings (for debugging)
    public void printMappings() {
        System.out.println("Center memory (value to node mappings):");
        for (ValueNodePair pair : nodeList) {
            System.out.println("Value: " + pair.value + ", Node: " + pair.node);
        }
    }
}