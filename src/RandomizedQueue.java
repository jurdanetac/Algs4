import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] dynamicArray; // will be resized as item count grows or shrinks
    private int itemCount;       // keep track of how many items there are

    // notice how dynamicArray.length DOES NOT equal itemCount.
    // when the array resizes it may have free null spots that aren't items

    // construct an empty randomized queue
    public RandomizedQueue() {
        itemCount = 0;
        dynamicArray = (Item[]) new Object[itemCount];
    }

    private Item[] shuffleList() {
        boolean[] usedItems = new boolean[itemCount]; // tells if item n has already been placed in shuffled list
        int usedCount = 0;                            // keep used item count

        Item[] shuffledList = (Item[]) new Object[dynamicArray.length];

        // shuffle list
        for (int i = 0; i < itemCount; i++) {
            int n;
            do {
                n = StdRandom.uniformInt(0, itemCount);
            } while (usedItems[n] && usedCount != usedItems.length);
            usedItems[n] = true;
            usedCount++;
            shuffledList[i] = dynamicArray[n];
        }

        return shuffledList;
    }

    private class ListIterator implements Iterator<Item> {
        int current;          // keep track in what item are we
        Item[] shuffledList;  // used to shuffle the list

        public ListIterator() {
            shuffledList = shuffleList();  // each iterator gives a randomized set of the list
            current = 0;                   // when an iterator is created start on pos. 0
        }

        // return whether there are more elements in the linked list that haven't been iterated over
        public boolean hasNext() {
            return current < itemCount;
        }

        // return the next element of the iteration and move the current node to the next one.
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return shuffledList[current++];
        }
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return itemCount == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return itemCount;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();

        if (itemCount == dynamicArray.length) {
            Item[] aux;

            if (itemCount == 0) aux = (Item[]) new Object[2];
            else aux = (Item[]) new Object[itemCount * 2];

            int index = 0;
            for (Item i : dynamicArray) aux[index] = dynamicArray[index++];
            dynamicArray = aux;
        }

        dynamicArray[itemCount++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (itemCount == 0) {
            throw new NoSuchElementException();
        } else if (itemCount == 1) {
            itemCount--;
            return dynamicArray[0];
        }

        // halve array to save unused memory
        if (itemCount == dynamicArray.length / 2) {
            // allocate new array
            Item[] aux = (itemCount % 2 != 0) ? (Item[]) new Object[(dynamicArray.length / 2) + 1] : (Item[]) new Object[dynamicArray.length / 2];

            // copy items to new array
            for (int i = 0; i < itemCount; i++) aux[i] = dynamicArray[i];

            // make array point to new halved array
            dynamicArray = aux;
        }

        Item[] aux = (Item[]) new Object[dynamicArray.length - 1];
        Iterator<Item> it = iterator();
        // since iterator uses a random set of the list, just remove the first item
        Item deleted = it.next();

        // change pointer to new list without a random item
        int i = 0;
        while (it.hasNext()) aux[i++] = it.next();
        dynamicArray = aux;

        itemCount--;

        return deleted;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (itemCount < 1) throw new NoSuchElementException();

        return shuffleList()[0];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();

        System.out.println("Initial size: " + rq.size());
        System.out.println("Empty: " + rq.isEmpty());

        rq.enqueue(50);
        rq.enqueue(100);
        rq.enqueue(150);

        System.out.println("Empty: " + rq.isEmpty());
        System.out.println("Sample: " + rq.sample());

        int i = 0;
        for (Integer integer : rq) System.out.println("Item " + ++i + ": " + integer);

        rq.dequeue();
        rq.dequeue();
        rq.dequeue();

        System.out.println("Final size: " + rq.size());
        System.out.println("Empty: " + rq.isEmpty());
    }
}
