package org.example;

import java.util.ArrayList;
import java.util.List;

// MyTree Class
class MyTree {
    private Node root;
    private final Center center;

    public MyTree(String value, Center center) {
        this.root = new Node(value);
        this.center = center;
        this.center.add(value, root);
    }

    public void insert(String value) {
        root = insertRec(root, value);
    }

    private Node insertRec(Node current, String value) {
        if (current == null) {
            Node newNode = new Node(value);
            center.add(value, newNode);
            return newNode;
        }

        if (value.compareTo(current.value) < 0) {
            current.left = insertRec(current.left, value);
        } else if (value.compareTo(current.value) > 0) {
            current.right = insertRec(current.right, value);
        }
        // Duplicates are not added to the tree
        return current;
    }

    public void inOrderTraversal() {
        inOrderTraversalRec(root);
        System.out.println();
    }

    private void inOrderTraversalRec(Node current) {
        if (current != null) {
            inOrderTraversalRec(current.left);
            System.out.print(current.value + " ");
            inOrderTraversalRec(current.right);
        }
    }

    public void printTree() {
        List<List<String>> lines = new ArrayList<>();
        List<Node> level = new ArrayList<>();
        List<Node> next = new ArrayList<>();

        level.add(root);
        int nn = 1;

        int widest = 0;

        while (nn != 0) {
            List<String> line = new ArrayList<>();

            nn = 0;

            for (Node n : level) {
                if (n == null) {
                    line.add(null);
                    next.add(null);
                    next.add(null);
                } else {
                    String aa = n.value;
                    line.add(aa);
                    if (aa.length() > widest) widest = aa.length();

                    next.add(n.left);
                    next.add(n.right);

                    if (n.left != null) nn++;
                    if (n.right != null) nn++;
                }
            }

            if (widest % 2 == 1) widest++;

            lines.add(line);

            List<Node> tmp = level;
            level = next;
            next = tmp;
            next.clear();
        }

        int perpiece = lines.get(lines.size() - 1).size() * (widest + 4);
        for (int i = 0; i < lines.size(); i++) {
            List<String> line = lines.get(i);
            int hpw = (int) Math.floor(perpiece / 2f) - 1;

            if (i > 0) {
                for (int j = 0; j < line.size(); j++) {

                    char c = ' ';
                    if (j % 2 == 1) {
                        if (line.get(j - 1) != null) {
                            c = (line.get(j) != null) ? '┴' : '┘';
                        } else {
                            if (j < line.size() && line.get(j) != null) c = '└';
                        }
                    }
                    System.out.print(c);

                    if (line.get(j) == null) {
                        for (int k = 0; k < perpiece - 1; k++) {
                            System.out.print(" ");
                        }
                    } else {

                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? " " : "─");
                        }
                        System.out.print(j % 2 == 0 ? "┌" : "┐");
                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? "─" : " ");
                        }
                    }
                }
                System.out.println();
            }

            for (int j = 0; j < line.size(); j++) {

                String f = line.get(j);
                if (f == null) f = "";
                int gap1 = (int) Math.ceil(perpiece / 2f - f.length() / 2f);
                int gap2 = (int) Math.floor(perpiece / 2f - f.length() / 2f);

                for (int k = 0; k < gap1; k++) {
                    System.out.print(" ");
                }
                System.out.print(f);
                for (int k = 0; k < gap2; k++) {
                    System.out.print(" ");
                }
            }
            System.out.println();

            perpiece /= 2;
        }
    }

    /**
     * Transforms the binary tree into a linear one-way linked list.
     * The transformation follows the order: root -> left -> right.
     * Displays the transformation process with animation.
     */
    public void transformToLinkedList() {
        if (root == null) {
            System.out.println("Tree is empty. Cannot transform.");
            return;
        }

        System.out.println("\nStarting transformation to a linear linked list...");
        List<Node> nodes = new ArrayList<>();
        gatherNodes(root, nodes); // Gather nodes in the order: root, left, right

        List<Node> linkedList = new ArrayList<>();

        // Initialize the linear list with root
        Node current = nodes.get(0);
        current.left = null;
        root = current;
        linkedList.add(current);

        // Print initial state
        clearScreen();
        System.out.println("Transformation Step 0:");
        System.out.println("Current Tree:");
        printTree();
        System.out.println("\nLinear Linked List:");
        printLinkedList(linkedList);
        try {
            Thread.sleep(1000); // Pause for 1 second
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Iterate through the nodes and re-link them
        for (int i = 1; i < nodes.size(); i++) {
            Node nextNode = nodes.get(i);
            current.right = nextNode;
            nextNode.left = null;
            current = nextNode;
            linkedList.add(current);

            // Clear screen
            clearScreen();
            // Print current state
            System.out.println("Transformation Step " + i + ":");
            System.out.println("Current Tree:");
            printTree();
            System.out.println("\nLinear Linked List:");
            printLinkedList(linkedList);

            // Pause for animation
            try {
                Thread.sleep(1000); // Pause for 1 second
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Final state
        clearScreen();
        System.out.println("Transformation completed. Tree is now a linear linked list.");
        System.out.println("\nFinal Tree:");
        printTree();
        System.out.println("\nFinal Linear Linked List:");
        printLinkedList(linkedList);
    }

    /**
     * Helper method to gather nodes in a specific order: root, left, right.
     */
    private void gatherNodes(Node current, List<Node> nodes) {
        if (current == null) {
            return;
        }

        nodes.add(current); // Add current node to the list
        gatherNodes(current.left, nodes); // Process left subtree
        gatherNodes(current.right, nodes); // Process right subtree
    }

    /**
     * Prints the linear linked list in a readable format.
     *
     * @param list The list representing the linear linked list.
     */
    private void printLinkedList(List<Node> list) {
        for (Node node : list) {
            System.out.print(node.value + " -> ");
        }
        System.out.println("null");
    }

    /**
     * Clears the console screen.
     * Works on Unix-like systems (including Fedora Linux).
     */
    private void clearScreen() {
        try {
            // ANSI escape code to clear screen and move cursor to home position
            System.out.print("\033[H\033[2J");
            System.out.flush();
        } catch (Exception e) {
            System.out.println("Error clearing the screen.");
        }
    }

    // Helper method to pause the execution for 1 second
    private void waitOneSecond() {
        try {
            Thread.sleep(1000); // Pause for 1 second (1000 milliseconds)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
