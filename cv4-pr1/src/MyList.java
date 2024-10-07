public class MyList {
    private Node head;

    private static class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
        }
    }

    public void addFirst(int value) {         //            O(1)
        Node newNode = new Node(value);
        newNode.next = head;
        head = newNode;
    }

    public void removeFirst() {             // O(1)
        if (head != null) {
            head = head.next;
        }
    }

    public boolean contains(int value) {            // O(n)
        Node current = head;
        while (current != null) {
            if (current.value == value) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public int size() {                         // O(n)

        int count = 0;
        Node current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }

    public void reverse() {                //  O(n)
        Node previous = null;
        Node current = head;
        Node next;

        while (current != null) {
            next = current.next;   // Store next node
            current.next = previous; // Reverse the link
            previous = current;    // Move previous up
            current = next;        // Move current up
        }
        head = previous;           // Update head to the new front
    }

    public boolean isEmpty() {      // O(1)
        return head == null;
    }

    public void printList() {          // O(n)
        Node current = head;
        while (current != null) {
            System.out.print(current.value + " ");
            current = current.next;
        }
        System.out.println();
    }
}