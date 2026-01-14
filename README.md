# Java-Technical-Portfolio
Technical portfolio showcasing advanced Java data structures, location-aware heaps, and algorithmic complexity analysis.
## Portfolio Directory

### 1. [Adaptive Priority Queue System](advanced-priority-queue)
* **Core Concepts:** Location-Aware Entry tracking, Floyd’s O(n) Heap Construction, Rank-Based Retrieval.
-   **Engineering Impact:** Eliminated the O(n) search bottleneck for arbitrary entry modification, achieving O(log n) performance for targeted updates and removals.

### 2. [List Performance & Cache Locality Suite](custom-list-adt-performance)
* **Core Concepts:** Contiguous vs. Pointer-based memory, CPU Cache Locality, High-Precision Benchmarking.
-   **Engineering Impact:** Validated a 43x performance delta in random-access patterns, demonstrating the real-world impact of L1/L2 cache hits over raw Big-O theory.

### 3. [Tetranacci: Memoization vs. Recursion](recursive-complexity-analysis)
* **Core Concepts:** Dynamic Programming, Linear vs. Exponential Growth, Stack Depth Management.
-   **Engineering Impact:** Optimized a naive O(4^n) recursive algorithm into a linear O(n) process, preventing stack overflow and reducing execution time from minutes to milliseconds.

---

## Technical Themes

### Algorithmic Optimization
Across all projects, the focus is on reducing computational complexity and identifying the "crossover points" where specific data structures become more efficient than their alternatives.

### Memory & Hardware Awareness
Implementations prioritize cache-friendly data layouts and location-aware pointer management to minimize memory overhead and maximize throughput.

### Empirical Validation
Each project includes a benchmarking suite or analysis report that grounds theoretical claims in raw execution data (nanoseconds/milliseconds).
