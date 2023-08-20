import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class DequeTest {
    Deque<Integer> d;
    int item;

    public DequeTest() {
        d = new Deque<>();
    }

    @Test
    @DisplayName("Add node as first")
    void addFirst() {
        item = 0;
        d.addFirst(item);
        assertEquals(1, d.size(), "Error adding item at first position");
        Logger.getLogger(getClass().getName()).log(Level.INFO, String.format("Add item at back '%s' - OK!", item));

        item = 1;
        d.addFirst(item);
        assertEquals(2, d.size(), "Error adding item at first position");
        Logger.getLogger(getClass().getName()).log(Level.INFO, String.format("Add item at back '%s' - OK!", item));

        item = 2;
        d.addFirst(item);
        assertEquals(3, d.size(), "Error adding item at first position");
        Logger.getLogger(getClass().getName()).log(Level.INFO, String.format("Add item at back '%s' - OK!", item));
    }

    @Test
    @DisplayName("Add node as last")
    void addLast() {
        item = 0;
        d.addLast(item);
        assertEquals(1, d.size(), "Error adding item at last position");
        Logger.getLogger(getClass().getName()).log(Level.INFO, String.format("Add item at front'%s' - OK!", item));

        item = 1;
        d.addLast(item);
        assertEquals(2, d.size(), "Error adding item at last position");
        Logger.getLogger(getClass().getName()).log(Level.INFO, String.format("Add item at front '%s' - OK!", item));

        item = 2;
        d.addLast(item);
        assertEquals(3, d.size(), "Error adding item at last position");
        Logger.getLogger(getClass().getName()).log(Level.INFO, String.format("Add item at front '%s' - OK!", item));
    }

    @Test
    @DisplayName("Remove first node")
    void removeFirst() {
        Deque<Integer> d = new Deque<>();
        Iterator<Integer> it;

        try {
            d.removeFirst();
        } catch (NoSuchElementException e) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, "NoSuchElementException when trying to remove first element on empty list - OK!");
        }

        // try {
        //     it = d.iterator();
        // } catch (NullPointerException e) {
        //     Logger.getLogger(getClass().getName()).log(Level.INFO, "NullPointerException when invoking iterator with empty list - OK!");
        // }

        d.addFirst(0);
        it = d.iterator();
        it.next();

        try {
            it.next();
        } catch (NoSuchElementException e) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, "NoSuchElementException when trying to move cursor after last element - OK!");
        }

        d.removeFirst(); // test adding and removing a single item

        assertEquals(0, d.size()); // ensure consistency with list size and current items

        // final sequence: 1-2-3-4
        d.addFirst(2);
        d.addFirst(1);
        d.addLast(3);
        d.addLast(4);

        String firstOut = "";
        for (Integer integer : d) firstOut = firstOut.concat(integer + " ");
        d.removeFirst(); // comment to get AssertionFailedError strings are equal on l.100

        String secondOut = "";
        for (Integer integer : d) secondOut = secondOut.concat(integer + " ");

        firstOut = firstOut.trim();
        secondOut = secondOut.trim();

        assertNotEquals(firstOut, secondOut);
        Logger.getLogger(getClass().getName()).log(Level.INFO, String.format("First out: '%s'; Second out: '%s' - OK!", firstOut, secondOut));

        // clear the rest of the list
        d.removeFirst();
        d.removeFirst();
        d.removeFirst();

        try {
            it.remove();
        } catch (UnsupportedOperationException e) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, "UnsupportedOperationException when calling remove() - OK!");
        }
    }

    @Test
    @DisplayName("Remove last node")
    void removeLast() {
        Deque<Integer> d = new Deque<>();
        Iterator<Integer> it;

        try {
            d.removeLast();
        } catch (NoSuchElementException e) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, "NoSuchElementException when trying to remove last element on empty list - OK!");
        }

        // try {
        //     it = d.iterator();
        // } catch (NullPointerException e) {
        //     Logger.getLogger(getClass().getName()).log(Level.INFO, "NullPointerException when invoking iterator with empty list - OK!");
        // }

        d.addLast(0);
        it = d.iterator();
        it.next();

        try {
            it.next();
        } catch (NoSuchElementException e) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, "NoSuchElementException when trying to move cursor after last element - OK!");
        }

        d.removeLast(); // test adding and removing a single item

        assertEquals(0, d.size()); // ensure consistency with list size and current items

        // final sequence: 1-2-3-4
        d.addLast(1);
        d.addLast(2);
        d.addLast(3);
        d.addLast(4);

        String firstOut = "";
        for (Integer integer : d) firstOut = firstOut.concat(integer + " ");
        d.removeLast(); // comment to get AssertionFailedError strings are equal on l.100

        String secondOut = "";
        for (Integer integer : d) secondOut = secondOut.concat(integer + " ");

        firstOut = firstOut.trim();
        secondOut = secondOut.trim();

        assertNotEquals(firstOut, secondOut);
        Logger.getLogger(getClass().getName()).log(Level.INFO, String.format("First out: '%s'; Second out: '%s' - OK!", firstOut, secondOut));

        // clear the rest of the list
        d.removeLast();
        d.removeLast();
        d.removeLast();

        try {
            it.remove();
        } catch (UnsupportedOperationException e) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, "UnsupportedOperationException when calling remove() - OK!");
        }
    }
}
