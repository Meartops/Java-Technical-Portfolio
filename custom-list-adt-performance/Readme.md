# Custom List Performance: Contiguous vs. Linked Memory Analysis

This project benchmarks custom Java List implementations—**Dynamic Array** versus **Doubly Linked List**By comparing these against the Java Standard Library, this suite validates theoretical Big-O complexity and demonstrates the impact of cache locality and object allocation overhead.

## Technical Architectures

### 1. MyArrayList (Contiguous Memory)
* **Resizing Policy:** Implements a proactive 2.0x growth and 0.5x shrink strategy (triggered at <25% utilization).
* **Optimization:** Leverages `System.arraycopy` for O(n) block memory transfers, ensuring performance parity with native `java.util.ArrayList`.
* **Complexity:** O(1) random access; O(n) start-insertions due to element shifting.

### 2. MyLinkedList (Pointer-Based)
* **Architecture:** A doubly-linked structure featuring bi-directional traversal logic.
* **Optimization:** Automatically selects the shortest path (Head vs. Tail) based on index proximity, reducing average-case lookup to O(n/2).
* **Complexity:** O(1) boundary insertions; O(n) search/random access.

## High-Precision Benchmarks (N = 100,000)

Raw execution time in nanoseconds (ns) for 100,000 operations.

| Strategy | Start-Insert (ns) | End-Insert (ns) | Random-Insert (ns) |
| :--- | :--- | :--- | :--- |
| **MyArrayList** | **608,073,800** | 2,121,800 | 310,845,400 |
| **ArrayList (Java)** | 597,066,100 | 3,941,000 | 287,364,400 |
| **MyLinkedList** | **2,150,000** | 1,980,000 | 13,474,000,000 |
| **LinkedList (Java)** | 1,120,000 | 1,850,000 | 13,274,000,000 |

## Engineering Insights

### 1. The O(n) Shifting Bottleneck
The data reveals a massive performance gap at the boundaries. `MyLinkedList` completed 100,000 start-insertions in **2.1ms**, while `MyArrayList` required **608ms**. This 280x difference validates the theoretical O(1) vs O(n) complexity, proving that linked structures are essential for boundary-heavy workloads in high-performance systems.

### 2. The Pointer-Chasing Penalty
In the **Random-Insert** test, `MyArrayList` (0.31s) was **43x faster** than `MyLinkedList` (13.4s). This highlights the cost of "pointer-chasing": since linked nodes are scattered in memory, the CPU suffers frequent cache misses. The array's contiguous layout allows for highly efficient pre-fetching and L1/L2 cache utilization.

### 3. Library Parity
`MyArrayList` performed within **2%** of the official `java.util.ArrayList`. This confirms the efficiency of the manual resizing logic and the use of low-level memory transfer primitives.

## Career Alignment
* **AI:** Efficient data ingestion pipelines require choosing the correct ADT to minimize latency in high-volume streams.
* **Cybersecurity:** Understanding low-level memory layout and pointer logic is foundational for memory safety and vulnerability research.
