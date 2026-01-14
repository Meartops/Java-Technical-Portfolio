# Tetranacci Sequence Optimization – Complexity & Performance

Java implementations exploring the performance impact of different recursive strategies for high-order recurrence relations. This project demonstrates how algorithmic design transforms an exponential-time solution into a linear-time one through explicit state management.

---

## Problem Overview

The Tetranacci sequence is defined as:

T(n) = T(n−1) + T(n−2) + T(n−3) + T(n−4)

With a branching factor of 4, a naive recursive approach quickly becomes impractical due to extensive overlap between subproblems. For n > 30, execution time grows rapidly on standard hardware.

---

## Implementations

### 1. Naive Multiple Recursion  
**Time Complexity:** O(4ⁿ)

Baseline implementation used to illustrate the cost of redundant recursive calls. Performance degrades sharply as input size increases due to the absence of shared state.

---

### 2. Top-Down Memoization  
**Time Complexity:** O(n)

Introduces a caching layer using a `BigInteger[]` array to store previously computed values. This eliminates repeated work and reduces the recursion tree to linear growth.

---

### 3. Tail-Recursive Design  
**Time Complexity:** O(n)  
**Space Complexity:** O(n) 

Uses a sliding window of the four most recent values passed as parameters. While logically iterative, Java does not support Tail Call Optimization, so stack usage remains linear.

---

## Benchmark Results

Measured using `System.nanoTime()`.

| n   | Naive Recursion | Memoized | Tail-Recursive |
|-----|------------------|----------|----------------|
| 5   | 0 ms             | 0 ms     | 0 ms           |
| 20  | 1 ms             | 0 ms     | 0 ms           |
| 25  | 24 ms            | 0 ms     | 0 ms           |
| 30  | 499 ms           | 0 ms     | 0 ms           |
| 200 | Infeasible       | <1 ms    | <1 ms          |

---

## Engineering Considerations

- **Arbitrary-precision arithmetic:** Used `java.math.BigInteger` to handle values exceeding 64-bit limits.
- **Resource protection:** Added execution time thresholds and stack depth awareness to prevent failures during naive runs.

---

## Key Takeaway

This project demonstrates how dynamic programming and state reuse dramatically improve scalability. Algorithmic efficiency, rather than hardware capability, is the dominant factor in system performance.
