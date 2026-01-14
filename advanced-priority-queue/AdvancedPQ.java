import java.util.Comparator;

/**
 * Advanced Priority Queue (APQ) System.
 * * An adaptable, location-aware heap implementation supporting dynamic toggling 
 * between Min-Heap and Max-Heap states in linear time.
 */
public class AdvancedPQ<K extends Comparable<K>, V> {

    /**
     * Entry object tracking its own internal array index to enable O(1) lookup.
     */
    public static class Entry<K, V> {
        private K key;
        private V value;
        private int index; 

        public Entry(K key, V value, int index) {
            this.key = key;
            this.value = value;
            this.index = index;
        }

        public K getKey() { return key; }
        public V getValue() { return value; }
        
        @Override
        public String toString() { return "(" + key + ", " + value + ")"; }
    }

    private Entry<K, V>[] heap;
    private int size;
    private boolean isMinHeap; 
    private static final int DEFAULT_CAPACITY = 16;

    @SuppressWarnings("unchecked")
    public AdvancedPQ() {
        this.heap = (Entry<K, V>[]) new Entry[DEFAULT_CAPACITY];
        this.size = 0;
        this.isMinHeap = true;
    }

    @SuppressWarnings("unchecked")
    private AdvancedPQ(int capacity, boolean isMinState) {
        this.heap = (Entry<K, V>[]) new Entry[capacity];
        this.size = 0;
        this.isMinHeap = isMinState;
    }

    /**
     * Dynamic State Toggle (O(n)).
     * Switches heap priority logic and rebuilds the structure using 
     * Floyd's Bottom-Up construction.
     */
    public void toggle() {
        this.isMinHeap = !this.isMinHeap;
        if (size > 1) buildHeap();
    }

    public Entry<K, V> insert(K key, V value) {
        if (size >= heap.length) resize();
        Entry<K, V> newEntry = new Entry<>(key, value, size);
        heap[size] = newEntry;
        size++;
        upHeap(size - 1);
        return newEntry;
    }

    public Entry<K, V> removeTop() {
        if (isEmpty()) return null;
        Entry<K, V> root = heap[0];
        swap(0, size - 1); 
        heap[size - 1] = null;
        size--;
        if (size > 0) downHeap(0);
        return root;
    }

    /**
     * Targeted Removal (O(log n)).
     * Removes an arbitrary entry using its internal index pointer, 
     * bypassing the need for O(n) linear search.
     */
    public Entry<K, V> remove(Entry<K, V> e) {
        int i = e.index;
        if (i < 0 || i >= size || heap[i] != e) 
            throw new IllegalArgumentException("Entry not present in this heap.");

        if (i == size - 1) { 
            heap[i] = null;
            size--;
            return e;
        }

        swap(i, size - 1);
        heap[size - 1] = null;
        size--;

        upHeap(i);   
        downHeap(i);
        return e;
    }

    public K replaceKey(Entry<K, V> e, K newKey) {
        int i = e.index;
        if (i < 0 || i >= size || heap[i] != e)
            throw new IllegalArgumentException("Entry not present in this heap.");
        
        K oldKey = e.key;
        e.key = newKey;

        if (compare(newKey, oldKey) < 0) upHeap(i);
        else downHeap(i);
        
        return oldKey;
    }

    /**
     * Rank-Based Retrieval (O(n log n)).
     * Finds the n-th best element without mutating or cloning the main heap.
     */
    public Entry<K, V> peekAt(int n) {
        if (n < 1 || n > size) throw new IllegalArgumentException("Rank out of bounds.");
        if (n == 1) return top();

        AdvancedPQ<K, Integer> auxPQ = new AdvancedPQ<>(n + 1, this.isMinHeap);
        auxPQ.insert(this.heap[0].key, 0);

        for (int i = 0; i < n - 1; i++) {
            Entry<K, Integer> best = auxPQ.removeTop();
            int idx = best.getValue();

            int left = 2 * idx + 1;
            int right = 2 * idx + 2;

            if (left < this.size) auxPQ.insert(this.heap[left].key, left);
            if (right < this.size) auxPQ.insert(this.heap[right].key, right);
        }

        return this.heap[auxPQ.top().getValue()];
    }

    public void merge(AdvancedPQ<K, V> other) {
        if (other == null || other.isEmpty()) return;
        while (this.size + other.size > heap.length) resize();

        for (int i = 0; i < other.size; i++) {
            Entry<K, V> e = other.heap[i];
            heap[this.size] = new Entry<>(e.key, e.value, this.size);
            this.size++;
        }
        buildHeap();
    }

    public Entry<K, V> top() { return isEmpty() ? null : heap[0]; }
    public boolean isEmpty() { return size == 0; }
    public int size() { return size; }
    public String state() { return isMinHeap ? "Min-Heap" : "Max-Heap"; }

    private void buildHeap() {
        for (int i = (size - 2) / 2; i >= 0; i--) downHeap(i);
    }

    private void upHeap(int i) {
        while (i > 0) {
            int p = (i - 1) / 2;
            if (compare(heap[i].key, heap[p].key) < 0) {
                swap(i, p);
                i = p;
            } else break;
        }
    }

    private void downHeap(int i) {
        while (2 * i + 1 < size) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int target = left;

            if (right < size && compare(heap[right].key, heap[left].key) < 0) 
                target = right;

            if (compare(heap[target].key, heap[i].key) < 0) {
                swap(i, target);
                i = target;
            } else break;
        }
    }

    private int compare(K k1, K k2) {
        int res = k1.compareTo(k2);
        return isMinHeap ? res : -res;
    }

    private void swap(int i, int j) {
        Entry<K, V> temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
        heap[i].index = i;
        heap[j].index = j;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Entry<K, V>[] newHeap = (Entry<K, V>[]) new Entry[heap.length * 2];
        System.arraycopy(heap, 0, newHeap, 0, size);
        heap = newHeap;
    }

    /**
     * Functional demonstration of APQ capabilities.
     */
    public static void main(String[] args) {
        AdvancedPQ<Integer, String> apq = new AdvancedPQ<>();
        System.out.println("Mode: " + apq.state());

        AdvancedPQ.Entry<Integer, String> e1 = apq.insert(50, "Critical");
        apq.insert(10, "Low");
        apq.insert(100, "Infeasible");
        
        System.out.println("Top (Min): " + apq.top()); // Should be (10, Low)
        
        apq.toggle(); 
        System.out.println("Mode: " + apq.state());
        System.out.println("Top (Max): " + apq.top()); // Should be (100, Infeasible)

        apq.replaceKey(e1, 200);
        System.out.println("Updated Key 50 -> 200. New Top: " + apq.top());
        
        System.out.println("3rd Best Element: " + apq.peekAt(3));
    }
}
