package DeliverySystem.other;

import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class CustomList<T> implements Iterable<T> {
    private static final Logger logger = Logger.getLogger(CustomList.class.getName());
    CustomNode<T> head;
    private int length = 0;

    public CustomList() {
        this.head = null;
    }

    public int getLength() {
        return length;
    }

    public void add(T data) { //Add at the end
        CustomNode<T> temp = new CustomNode<>(data);

        if (this.head == null) {
            head = temp;
        }
        else {
            // Temporary CustomNode for traversal
            CustomNode<T> X = head;

            // Iterating till end of the List
            while (X.next != null) {
                X = X.next;
            }

            // Adding new valued CustomNode at the end of the list
            X.next = temp;
        }
        length++;
    }

    public void add(int position, T data) { //Add at any position
        // Checking if position is valid
        if (position > length + 1) {
            // Display message only
            logger.warn("Position Unavailable in LinkedList");
            return;
        }

        // If new position is head then replace head CustomNode
        if (position == 1) {
            // Temporary CustomNode that stores previous head value
            CustomNode<T> temp = head;
            // New valued CustomNode stored in head
            head = new CustomNode<T>(data);
            // New head CustomNode pointing to old head CustomNode
            head.next = temp;
            return;
        }

        // Temporary CustomNode for traversal
        CustomNode<T> temp = head;

        // Dummy CustomNode with null value that stores previous CustomNode
        CustomNode<T> prev = new CustomNode<T>(null);
        // iterating to the given position
        while (position - 1 > 0) {
            // assigning previous CustomNode
            prev = temp;
            // incrementing next CustomNode
            temp = temp.next;
            // decreasing position counter
            position--;
        }
        // previous CustomNode now points to new value
        prev.next = new CustomNode<T>(data);
        // new value now points to former current CustomNode
        prev.next.next = temp;
    }

    public void remove(T key) {
        //  NOTE
        // dummy CustomNode is used to represent the CustomNode before
        // the current CustomNode Since in a Singly Linked-List we
        // cannot go backwards from a CustomNode, we use a dummy
        // CustomNode to represent the previous CustomNode. In case of
        // head CustomNode, since there is no previous CustomNode, the
        // previous CustomNode is assigned to null.

        // Dummy CustomNode with null value
        CustomNode<T> prev = new CustomNode<>(null);
        // Dummy CustomNode pointing to head CustomNode
        prev.next = head;
        // Next CustomNode that points ahead of current CustomNode
        CustomNode<T> next = head.next;
        // Temporary CustomNode for traversal
        CustomNode<T> temp = head;

        // Boolean value that checks whether value to be
        // deleted exists or not
        boolean exists = false;
        // If head CustomNode needs to be deleted
        if (head.data == key) {
            head = head.next;
            // CustomNode to be deleted exists
            exists = true;
        }

        // Iterating over LinkedList
        while (temp.next != null) {
            // We convert value to be compared into Strings
            // and then compare using
            // String1.equals(String2) method

            // Comparing value of key and current CustomNode
            if (String.valueOf(temp.data).equals(String.valueOf(key))) {
                // If CustomNode to be deleted is found previous
                // CustomNode now points to next CustomNode skipping the
                // current CustomNode
                prev.next = next;
                // CustomNode to be deleted exists
                exists = true;

                // As soon as we find the CustomNode to be deleted
                // we exit the loop
                break;
            }

            // Previous CustomNode now points to current CustomNode
            prev = temp;
            // Current CustomNode now points to next CustomNode
            temp = temp.next;
            // Next CustomNode points the CustomNode ahead of current
            // CustomNode
            next = temp.next;
        }

        // Comparing the last CustomNode with the given key value
        if (!exists && String.valueOf(temp.data).equals(String.valueOf(key))) {
            // If found , last CustomNode is skipped over
            prev.next = null;
            // CustomNode to be deleted exists
            exists = true;
        }

        // If CustomNode to be deleted exists
        if (exists) {
            length--;
        }
        // If CustomNode to be deleted does not exist
        else {
            logger.warn("Given Value is not present in linked list");
        }
    }

    public void clear() {
        head = null;
        length = 0;
    }

    public boolean empty() {
        return head == null;
    }


    public int length() {
        return this.length;
    }

    @Override
    public String toString()
    {
        StringBuilder S = new StringBuilder("(");
        CustomNode<T> X = head;

        if (X == null)
            return S + "List is Empty)";

        while (X.next != null) {
            S.append(X.data);
            S.append(", ");
            X = X.next;
        }

        S.append(X.data);
        return S + ")";
    }

    public static void iterateList(CustomList<Integer> linkedList){
        // Creating an Iterator to our current LinkedList

        for (Object o : linkedList) {
            // next() return the next element in the iteration
            logger.info(o + ", ");
        }

    }

    public CustomNode<T> getHead() {
        return head;
    }

    @Override
    public void forEach(Consumer action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Iterator<T> iterator() {
        return new ListIterator<T>(this);
    }

    @Override
    public Spliterator spliterator() {
        return Iterable.super.spliterator();
    }
}

class ListIterator<T> implements Iterator<T> {
    CustomNode<T> current;

    // initialize pointer to head of the list for iteration
    public ListIterator(CustomList<T> list) {
        current = list.getHead();
    }

    // returns false if next element does not exist
    public boolean hasNext() {
        return current != null;
    }

    // return current data and update pointer
    public T next() {
        T data = current.getData();
        current = current.getNext();
        return data;
    }
}
