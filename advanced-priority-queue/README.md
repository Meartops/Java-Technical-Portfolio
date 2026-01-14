# # Advanced Priority Queue (APQ): Adaptive Heap Architecture

This project implements an **Advanced Priority Queue (APQ)** featuring a location-aware architecture. While standard heap implementations suffer from O(n) search latency, this system tracks entry positions in real-time to provide constant-time access and linear-time state transitions between **Min-Heap** and **Max-Heap** modes.

---

## Technical Architectures

### 1. Location-Aware Entry System

Standard priority queues (e.g., `java.util.PriorityQueue`) lack a mechanism to reference internal nodes directly, requiring a linear scan (O(n)) for arbitrary removals or key updates.

- **Mechanism:** Each `Entry` object maintains an internal index field. The heap logic updates these pointers during every swap operation.  
- **Result:** Constant-time O(1) lookup for any known entry.  
- **Impact:** Operations such as `remove(e)` and `replaceKey(e, k)` are executed in O(log n) time because the system bypasses the search phase and immediately begins heap restoration.

### 2. State-Toggle Optimization (Floyd’s Algorithm)

The APQ supports toggling between **Min-Heap** and **Max-Heap** logic dynamically to handle fluctuating priority requirements.

- **Optimization:** Instead of a naive O(n log n) approach (re-inserting all elements), the system utilizes **Floyd’s Bottom-Up Construction**.  
- **Complexity:** Rebuilds the heap in O(n) linear time by processing nodes from the lowest non-leaf levels upward. This allows the data structure to pivot its entire priority logic with minimal computational overhead.

### 3. Non-Destructive Rank Retrieval (`peekAt`)

Retrieving the n-th best element (e.g., the 3rd most critical task) typically requires destructive polling or expensive O(N) memory cloning.

- **Logic:** The `peekAt(n)` method implements a frontier-search using an auxiliary Priority Queue to explore candidate branches of the main heap.  
- **Performance:** Operates in O(n log n) time, where n is the requested rank. This preserves the integrity of the primary heap while efficiently locating deep-rank elements.

---

## Complexity & Performance Analysis

The following table details the algorithmic costs associated with the APQ system:

| Operation       | Time Complexity | Engineering Justification                                        |
|-----------------|----------------|------------------------------------------------------------------|
| `insert(k, v)`  | O(log n)       | Standard binary heap insertion with proactive array resizing.     |
| `remove(e)`     | O(log n)       | Location-awareness converts O(n) search into O(1) lookup. |
| `replaceKey(e, k)` | O(log n)    | Targeted update via stored index, avoids linear scan.           |
| `toggle()`      | O(n)           | Re-orders the entire structure via linear-time Floyd's construction. |
| `merge(other)`  | O(n + m)       | Concatenates arrays and performs a single bottom-up build pass.  |
| `peekAt(n)`     | O(n log n)     | Auxiliary branch-search avoids full heap duplication.            |

---

## Engineering Insights

### Memory-Latency Trade-off

By storing a 32-bit index pointer within each entry, the APQ sacrifices a marginal amount of memory to eliminate the O(n) search bottleneck. In high-frequency systems (such as task schedulers or network packet buffers), this O(log n) guarantee is critical for maintaining sub-millisecond system responsiveness and preventing tail latency spikes.

### Floyd's Construction Efficiency

For large datasets (e.g., N > 1,000,000), Floyd’s algorithm is significantly more efficient than repeated insertions. By minimizing the total number of swaps required to satisfy the heap property, the system can pivot its priority logic in real-time in response to fluctuating system metrics, such as switching from **Shortest Job First** to **Highest Response Ratio Next**.

---

## Conclusion

This implementation demonstrates the efficacy of **location-aware pointer management** and **optimal heap-building algorithms**. The result is a highly adaptable, production-grade priority system that handles dynamic updates and state transitions with mathematical optimality.
