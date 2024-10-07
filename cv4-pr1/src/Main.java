public class Main {
    public static void main(String[] args) {
        MyList list = new MyList();

        list.addFirst(10);
        list.addFirst(7);
        list.addFirst(15);
        list.addFirst(3);

        System.out.print("Initial list: ");
        list.printList();

        System.out.println("List contains 7: " + list.contains(7));
        System.out.println("List contains 5: " + list.contains(5));

        System.out.println("Size of the list: " + list.size());

        list.removeFirst();
        System.out.print("List after removing the first element: ");
        list.printList();

        list.reverse();
        System.out.print("Reversed list: ");
        list.printList();

        System.out.println("Is the list empty? " + list.isEmpty());

        while (!list.isEmpty()) {
            list.removeFirst();
        }

        System.out.println("Is the list empty? " + list.isEmpty());
    }
}