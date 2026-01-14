import java.math.BigInteger;

/**
 * Top-down memoized implementation of the Tetranacci sequence.
 * Eliminates redundant recursive calls by caching intermediate results.
 *
 * Time Complexity: O(n)
 * Space Complexity: O(n) for memo array and recursion stack
 */
public class LinearRecursiveMemo {

    private static BigInteger[] memo;

    public static BigInteger tetranacci(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be >= 0");
        }

        memo = new BigInteger[n + 1];

        memo[0] = BigInteger.ZERO;
        if (n >= 1) memo[1] = BigInteger.ZERO;
        if (n >= 2) memo[2] = BigInteger.ZERO;
        if (n >= 3) memo[3] = BigInteger.ONE;

        return compute(n);
    }

    private static BigInteger compute(int n) {
        if (memo[n] != null) {
            return memo[n];
        }

        memo[n] = compute(n - 1)
                .add(compute(n - 2))
                .add(compute(n - 3))
                .add(compute(n - 4));

        return memo[n];
    }
}

