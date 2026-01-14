import java.util.List;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * Custom Doubly Linked List implementation.
 * Optimized traversal logic selects the shortest path (Head vs Tail)
 * based on the target index.
 */
public class MyLinkedList<E> implements List<E> {
    
    private static class Node<E> {
        E value;
        Node<E> prev, next;
        Node(E value) { this.value = value; }
    }

    private Node<E> head, tail;
    private int size;

    public MyLinkedList() {}

    /**
     * Traversal Optimization: O(n/2) lookup.
     */
    private Node<E> nodeAt(int index) {
        if (index < (size >> 1)) {
            Node<E> x = head;
            for (int i = 0; i < index; i++) x = x.next;
            return x;
        } else {
            Node<E> x = tail;
            for (int i = size - 1; i > index; i--) x = x.prev;
            return x;
        }
    }

    @Override
    public boolean add(E e) {
        Node<E> newNode = new Node<>(e);
        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
        return true;
    }

    @Override
    public void add(int index, E element) {
        validatePositionIndex(index);
        if (index == size) {
            add(element);
            return;
        }
        if (index == 0) {
            Node<E> newNode = new Node<>(element);
            newNode.next = head;
            if (head != null) head.prev = newNode;
            head = newNode;
            if (tail == null) tail = newNode;
            size++;
            return;
        }
        Node<E> succ = nodeAt(index);
        Node<E> pred = succ.prev;
        Node<E> newNode = new Node<>(element);
        newNode.prev = pred;
        newNode.next = succ;
        pred.next = newNode;
        succ.prev = newNode;
        size++;
    }

    @Override
    public E remove(int index) {
        validateElementIndex(index);
        Node<E> x = nodeAt(index);
        E element = x.value;
        Node<E> next = x.next;
        Node<E> prev = x.prev;

        if (prev == null) head = next; else prev.next = next;
        if (next == null) tail = prev; else next.prev = prev;

        x.prev = x.next = null;
        x.value = null;
        size--;
        return element;
    }

    @Override
    public boolean remove(Object o) {
        for (Node<E> x = head; x != null; x = x.next) {
            if (o == null ? x.value == null : o.equals(x.value)) {
                Node<E> next = x.next;
                Node<E> prev = x.prev;
                if (prev == null) head = next; else prev.next = next;
                if (next == null) tail = prev; else next.prev = prev;
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public void clear() {
        for (Node<E> x = head; x != null; ) {
            Node<E> next = x.next;
            x.value = null; x.prev = null; x.next = null;
            x = next;
        }
        head = tail = null;
        size = 0;
    }

    @Override
    public int size() { return size; }

    @Override
    public boolean isEmpty() { return size == 0; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> x = head;
        while (x != null) {
            sb.append(x.value);
            if (x.next != null) sb.append(", ");
            x = x.next;
        }
        return sb.append("]").toString();
    }

    private void validatePositionIndex(int index) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
    }

    private void validateElementIndex(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
    }

    // --- Interface Stubs (Unsupported Operations) ---
    @Override public boolean contains(Object o) { throw new UnsupportedOperationException(); }
    @Override public Iterator<E> iterator() { throw new UnsupportedOperationException(); }
    @Override public Object[] toArray() { throw new UnsupportedOperationException(); }
    @Override public <T> T[] toArray(T[] a) { throw new UnsupportedOperationException(); }
    @Override public boolean containsAll(Collection<?> c) { throw new UnsupportedOperationException(); }
    @Override public boolean addAll(Collection<? extends E> c) { throw new UnsupportedOperationException(); }
    @Override public boolean addAll(int index, Collection<? extends E> c) { throw new UnsupportedOperationException(); }
    @Override public boolean removeAll(Collection<?> c) { throw new UnsupportedOperationException(); }
    @Override public boolean retainAll(Collection<?> c) { throw new UnsupportedOperationException(); }
    @Override public E get(int index) { throw new UnsupportedOperationException(); }
    @Override public E set(int index, E element) { throw new UnsupportedOperationException(); }
    @Override public int indexOf(Object o) { throw new UnsupportedOperationException(); }
    @Override public int lastIndexOf(Object o) { throw new UnsupportedOperationException(); }
    @Override public ListIterator<E> listIterator() { throw new UnsupportedOperationException(); }
    @Override public ListIterator<E> listIterator(int index) { throw new UnsupportedOperationException(); }
    @Override public List<E> subList(int fromIndex, int toIndex) { throw new UnsupportedOperationException(); }
}
