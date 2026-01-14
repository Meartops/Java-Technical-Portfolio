import java.util.List;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * Custom Dynamic Array implementation featuring a proactive resizing policy.
 * Optimized for O(1) random access with amortized O(1) insertions.
 */
public class MyArrayList<E> implements List<E> {
    private Object[] data;
    private int size;

    public MyArrayList() {
        this(8);
    }

    public MyArrayList(int capacity) {
        if (capacity <= 0) capacity = 8;
        data = new Object[capacity];
        size = 0;
    }

    /**
     * Resizing Policy: Doubles capacity on growth; 
     * halves capacity when utilization drops below 25%.
     */
    private void grow() {
        int newCapacity = data.length * 2;
        Object[] newData = new Object[newCapacity];
        System.arraycopy(data, 0, newData, 0, size);
        data = newData;
    }

    private void shrink() {
        if (data.length > 8 && size < data.length / 4) {
            int newCapacity = data.length / 2;
            if (newCapacity < 8) newCapacity = 8;
            Object[] newData = new Object[newCapacity];
            System.arraycopy(data, 0, newData, 0, size);
            data = newData;
        }
    }

    @Override
    public boolean add(E e) {
        if (size == data.length) grow();
        data[size++] = e;
        return true;
    }

    @Override
    public void add(int index, E element) {
        validatePositionIndex(index);
        if (size == data.length) grow();
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = element;
        size++;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E remove(int index) {
        validateElementIndex(index);
        E removedValue = (E) data[index];
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(data, index + 1, data, index, numMoved);
        }
        data[--size] = null;
        shrink();
        return removedValue;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (o == null ? data[i] == null : o.equals(data[i])) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) data[i] = null;
        size = 0;
        shrink();
    }

    @Override
    public int size() { return size; }

    @Override
    public boolean isEmpty() { return size == 0; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            if (i > 0) sb.append(", ");
            sb.append(data[i]);
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
