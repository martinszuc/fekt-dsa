package org.example;

class MyTree {
    private Node root;
    private final Center center; // Reference to Center for tracking inserts

    public MyTree(String value, Center center) {
        this.root = new Node(value);
        this.center = center;
        this.center.add(value); // Track the initial value
    }

    public void insert(String value) {
        root = insertRec(root, value);
        center.add(value); // Track every insertion
    }

    private Node insertRec(Node current, String value) {
        if (current == null) {
            return new Node(value);
        }

        if (value.compareTo(current.value) < 0) {
            current.left = insertRec(current.left, value);
        } else if (value.compareTo(current.value) > 0) {
            current.right = insertRec(current.right, value);
        }
        return current;
    }

    public void inOrderTraversal() {
        inOrderTraversalRec(root);
        System.out.println(); // Add newline after traversal
    }

    private void inOrderTraversalRec(Node current) {
        if (current != null) {
            inOrderTraversalRec(current.left);
            System.out.print(current.value + " ");
            inOrderTraversalRec(current.right);
        }
    }
}