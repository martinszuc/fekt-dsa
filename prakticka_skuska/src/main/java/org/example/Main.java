package org.example;

public class Main {
    public static void main(String[] args) {
        Center center = new Center();
        MyTree tree = new MyTree("B", center);
        tree.insert("D");
        tree.insert("A");
        tree.insert("C");
        tree.insert("E");

        System.out.println("In-order traversal of the tree:");
        tree.inOrderTraversal(); // Should print: A B C D E

        System.out.println("\nCenter memory of all inserted values:");
        center.printMemory(); // Should print: [B, D, A, C, E]
    }

}