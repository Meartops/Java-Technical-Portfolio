import java.math.BigInteger;

/**
 * Tail-recursive implementation of the Tetranacci sequence.
 * Uses a sliding window to carry state forward.
 *
 * Time Complexity: O(n)
 * Space Complexity: O(n)
 *
 * Note: Java does NOT perform tail-call optimization,
 * so recursion depth still grows linearly.
 */
public class TailRecursive {

    public static BigInteger tetranacci(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be >= 0");
        }

        if (n == 0 || n == 1 || n == 2) {
            return BigInteger.ZERO;
        }

        if (n == 3) {
            return BigInteger.ONE;
        }

        // Initial window: T0=0, T1=0, T2=0, T3=1
        return helper(
                4,
                n,
                BigInteger.ZERO,
                BigInteger.ZERO,
                BigInteger.ZERO,
                BigInteger.ONE
        );
    }

    private static BigInteger helper(
            int index,
            int target,
            BigInteger t0,
            BigInteger t1,
            BigInteger t2,
            BigInteger t3) {

        BigInteger next = t0.add(t1).add(t2).add(t3);

        if (index == target) {
            return next;
        }

        return helper(index + 1, target, t1, t2, t3, next);
    }
}
