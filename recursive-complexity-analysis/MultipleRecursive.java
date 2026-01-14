import java.math.BigInteger;

/**
 * Naive multiple-recursive implementation of the Tetranacci sequence.
 * Serves as an exponential-time baseline for performance comparison.
 *
 * Time Complexity: O(4^n)
 * Space Complexity: O(n) due to recursion stack depth
 */
public class MultipleRecursive {

    public static BigInteger tetranacci(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be >= 0");
        }

        switch (n) {
            case 0:
            case 1:
            case 2:
                return BigInteger.ZERO;
            case 3:
                return BigInteger.ONE;
            default:
                return tetranacci(n - 1)
                        .add(tetranacci(n - 2))
                        .add(tetranacci(n - 3))
                        .add(tetranacci(n - 4));
        }
    }
}
