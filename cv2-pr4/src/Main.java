public class Main {
    public static void main(String[] args) {
        // Create nodes (vertices)
        Node node1 = new Node("Node 1");
        Node node2 = new Node("Node 2");
        Node node3 = new Node("Node 3");
        Node node4 = new Node("Node 4");
        Node node5 = new Node("Node 5");
        Node node6 = new Node("Node 6");

        node1.addNode(node2);
        node1.addNode(node4);
        node1.addNode(node6);
        node1.addNode(node5);

        node2.addNode(node1);
        node2.addNode(node3);
        node2.addNode(node5);

        node3.addNode(node2);

        node4.addNode(node1);
        node4.addNode(node5);

        node5.addNode(node1);
        node5.addNode(node2);
        node5.addNode(node4);

        node6.addNode(node1);


        node1.printNodes();
        node2.printNodes();
        node3.printNodes();
        node4.printNodes();
        node5.printNodes();
        node6.printNodes();
    }
}