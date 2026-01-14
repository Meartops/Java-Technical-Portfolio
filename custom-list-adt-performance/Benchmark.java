import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.function.Supplier;

/**
 * Performance Benchmarking.
 * Measures execution time in nanoseconds (ns).
 */
public class ListTester {
    private static final int[] N_VALUES = {10, 100, 1000, 10000, 100000};

    public static void main(String[] args) {
        Random rng = new Random(352);

        for (int N : N_VALUES) {
            int[] base = generateRandomValues(N, rng);
            System.out.println("Benchmark: N = " + N);
            
            List<String> names = Arrays.asList("MyArrayList", "ArrayList (Java)", "MyLinkedList", "LinkedList (Java)");
            List<Supplier<List<Integer>>> builders = Arrays.asList(
                MyArrayList::new, ArrayList::new, MyLinkedList::new, LinkedList::new
            );

            System.out.println("Strategy           | Start-Insert (ns) | End-Insert (ns) | Random-Insert (ns)");
            System.out.println("-------------------|-------------------|-----------------|-------------------");
            
            for (int i = 0; i < builders.size(); i++) {
                final Supplier<List<Integer>> sup = builders.get(i);
                
                long tStart = measure(() -> {
                    List<Integer> L = sup.get();
                    for (int val : base) L.add(0, val);
                });

                long tEnd = measure(() -> {
                    List<Integer> L = sup.get();
                    for (int val : base) L.add(val);
                });

                long tRandom = measure(() -> {
                    List<Integer> L = sup.get();
                    Random localRng = new Random(777);
                    for (int val : base) {
                        int idx = L.isEmpty() ? 0 : localRng.nextInt(L.size() + 1);
                        L.add(idx, val);
                    }
                });

                System.out.printf("%-18s | %-17d | %-15d | %-17d\n", names.get(i), tStart, tEnd, tRandom);
            }
            System.out.println();
        }
    }

    private static int[] generateRandomValues(int N, Random r) {
        int[] v = new int[N];
        for (int i = 0; i < N; i++) v[i] = r.nextInt(2 * N + 1);
        return v;
    }

    
    private static long measure(Runnable task) {
        long t0 = System.nanoTime();
        task.run();
        long t1 = System.nanoTime();
        return (t1 - t0);
    }
}
