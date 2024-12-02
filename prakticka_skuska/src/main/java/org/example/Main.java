package org.example;

import java.util.Scanner;

// Main Class
public class Main {
    public static void main(String[] args) {
        Center center = new Center();
        MyTree tree = new MyTree("B", center); // Initialize tree with root "B"

        Scanner scanner = new Scanner(System.in);
        System.out.println("==============================================");
        System.out.println("      Binary Tree Console Application");
        System.out.println("==============================================");
        System.out.println("\nInitial Tree:");
        tree.printTree();

        while (true) {
            System.out.println("\n---------------------------------------------------------------");
            System.out.println("Menu:");
            System.out.println("1. Add a node");
            System.out.println("2. Print in-order traversal");
            System.out.println("3. Print tree structure");
            System.out.println("4. Search for a node");
            System.out.println("5. Print Center Mappings");
            System.out.println("6. Transform to linear linked list");
            System.out.println("7. Exit");
            System.out.println("---------------------------------------------------------------");

            System.out.print("Enter your choice (1-7): ");
            String input = scanner.nextLine().trim();

            // Validate menu choice
            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice! Please enter a number between 1 and 7.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter a value to add (single uppercase letter): ");
                    String value = scanner.nextLine().trim();
                    if (value.length() != 1 || !Character.isUpperCase(value.charAt(0))) {
                        System.out.println("Invalid input! Please enter a single uppercase letter (A-Z).");
                    } else {
                        tree.insert(value);
                        System.out.println("Node added: " + value);
                        System.out.println("\nUpdated Tree:");
                        tree.printTree();
                    }
                    break;

                case 2:
                    System.out.println("\nIn-order traversal of the tree:");
                    tree.inOrderTraversal();
                    break;

                case 3:
                    System.out.println("\nTree structure:");
                    tree.printTree();
                    break;

                case 4:
                    System.out.print("Enter a value to search: ");
                    String searchValue = scanner.nextLine().trim();
                    if (searchValue.length() != 1 || !Character.isUpperCase(searchValue.charAt(0))) {
                        System.out.println("Invalid input! Please enter a single uppercase letter (A-Z).");
                        break;
                    }
                    Node retrievedNode = center.getNode(searchValue);
                    if (retrievedNode != null) {
                        System.out.println("Node found: " + retrievedNode.value + " (Memory: " + retrievedNode + ")");
                    } else {
                        System.out.println("Node not found.");
                    }
                    break;

                case 5:
                    center.printMappings();
                    break;

                case 6:
                    tree.transformToLinkedList();
                    break;

                case 7:
                    System.out.println("\nExiting the program. Thank you for using the Binary Tree Console Application!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice! Please enter a number between 1 and 7.");
            }
        }
    }
}
