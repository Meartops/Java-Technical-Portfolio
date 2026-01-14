// =======================
// Benchmark.java
// =======================

/**
 * Benchmark runner for comparing Tetranacci implementations.
 * Uses System.nanoTime() for timing measurements.
 */
public class Benchmark {

    public static void main(String[] args) {

        int[] testValues = {5, 20, 25, 30, 200};

        System.out.println("Benchmarking Tetranacci Implementations");
        System.out.println("n, multiple_recursive_ns, memoized_ns, tail_recursive_ns");

        for (int n : testValues) {

            // Multiple Recursive (may overflow or be infeasible)
            String multiTime;
            try {
                long start = System.nanoTime();
                MultipleRecursive.tetranacci(n);
                multiTime = String.valueOf(System.nanoTime() - start);
            } catch (StackOverflowError e) {
                multiTime = "INF";
            }

            // Memoized
            long startMemo = System.nanoTime();
            LinearRecursiveMemo.tetranacci(n);
            long memoTime = System.nanoTime() - startMemo;

            // Tail Recursive
            long startTail = System.nanoTime();
            TailRecursive.tetranacci(n);
            long tailTime = System.nanoTime() - startTail;

            System.out.printf(
                    "%d, %s, %d, %d%n",
                    n,
                    multiTime,
                    memoTime,
                    tailTime
            );
        }
    }
}
