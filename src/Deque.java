import java.util.Iterator;
import java.util.NoSuchElementException;
// import java.util.logging.Level;
// import java.util.logging.Logger;

public class Deque<Item> implements Iterable<Item> {
    private final DoublyLinkedList linkedList;
    private int size;

    private class Node {
        Node prev;  // pointer to prev item
        Node next;  // pointer to next item
        Item data;  // item itself

        @Override
        public String toString() {
            return "Node{" +
                    ", data=" + data +
                    '}';
        }
    }

    private class DoublyLinkedList {
        Node first; // pointer to first item
        Node last;  // pointer to last item

        @Override
        public String toString() {
            return "DoublyLinkedList{" +
                    "first=" + first +
                    ", last=" + last +
                    '}';
        }
    }

    // construct an empty deque
    public Deque() {
        linkedList = new DoublyLinkedList();
        size = 0;
    }

    private class ListIterator implements Iterator<Item> {
        Node node; // current element

        public ListIterator() {
            node = new Node();
            node.next = linkedList.first;
            // if (node.next == null) throw new NullPointerException("There are no items");
        }

        // return whether there are more elements in the linked list that haven't been iterated over
        public boolean hasNext() {
            return node.next != null;
        }

        // return the next element of the iteration and move the current node to the next one.
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            node = node.next;
            return node.data;
        }
    }

    // is the deque empty?
    public boolean isEmpty() {
        return (linkedList.first == null && linkedList.last == null && size == 0);
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();

        // Create references to nodes
        Node newFirst = new Node();
        Node oldFirst = linkedList.first;

        // Populate item to be added
        newFirst.prev = null;
        newFirst.next = oldFirst;
        newFirst.data = item;

        // Move the old first node second
        if (oldFirst != null) oldFirst.prev = newFirst;

        // Declare pointer to new added first node
        linkedList.first = newFirst;

        if (size == 0) linkedList.last = linkedList.first;

        // Logger.getLogger(getClass().getName()).log(Level.INFO, linkedList.toString());

        // Increase the item count inside the linked list
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();

        // Create references to nodes
        Node newLast = new Node();
        Node oldLast = linkedList.last;

        // If appending on first run
        if (oldLast != null) {
            // Move the old last node second to last
            oldLast.next = newLast;
            newLast.prev = oldLast;
        } else {
            newLast.prev = null;
            linkedList.first = newLast;
        }

        // Populate item to be added
        newLast.next = null;
        newLast.data = item;

        // Declare pointer to new added last node
        linkedList.last = newLast;

        // Logger.getLogger(getClass().getName()).log(Level.INFO, linkedList.toString());

        // Increase the item count inside the linked list
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (!iterator().hasNext()) throw new NoSuchElementException();

        Item data = linkedList.first.data;

        if (linkedList.first.next == null) {
            linkedList.first = null;
            linkedList.last = null;
        } else {
            linkedList.first = linkedList.first.next;
            linkedList.first.prev = null;
        }

        size--;

        return data;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (!iterator().hasNext()) throw new NoSuchElementException();

        Item data = linkedList.last.data;

        if (linkedList.last.prev == null) {
            linkedList.last = null;
            linkedList.first = null;
        } else {
            linkedList.last = linkedList.last.prev;
            linkedList.last.next = null;
        }

        size--;

        return data;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> d = new Deque<>();

        System.out.println("Initial size: " + d.size());
        System.out.println("Empty: " + d.isEmpty());

        d.addFirst(0);
        d.addLast(1);

        System.out.println("Remove first: " + d.removeFirst());
        System.out.println("Remove last: " + d.removeLast());

        d.addFirst(1);
        d.addLast(2);
        d.addLast(3);

        int i = 0;
        Iterator<Integer> it = d.iterator();
        while (it.hasNext()) System.out.println("Item " + ++i + ":" + it.next());

        System.out.println("Final size: " + d.size());
        System.out.println("Empty: " + d.isEmpty());
    }
}
