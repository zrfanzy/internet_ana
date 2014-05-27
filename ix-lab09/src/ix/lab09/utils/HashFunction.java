package ix.lab09.utils;

import java.util.Random;

import org.apache.hadoop.thirdparty.guava.common.base.Preconditions;

/**
 * A simple parametric hash function that has the following properties:
 *
 * - the number of output bits can be given as a parameter,
 * - the distribution on the output space is relatively uniform.
 *
 * A hash is encoded as a long (typically, 64 bits) but only a certain number of bit
 * actually matter (nbBits, passed as a parameter to the constructor.
 */
public class HashFunction {

    /** Pseudo-random number generator. */
    public static final Random PRNG = new Random();

    private final long m;
    private final long n;
    private final long mask;


    /**
     * Gets a new hash function instance.
     *
     * @param m  an integer, the first parameter of the hash function
     * @param n  an integer, the second parameter of the hash function
     * @param nbBits  the number of bits in the output of the hash
     */
    public HashFunction(long m, long n, int nbBits) {
        Preconditions.checkArgument(nbBits > 0 && nbBits <= 64,
                "'nbBits' should be between 1 and 64");
        this.m = m;
        this.n = n;
        // -1L is 64 ones, we shift it left and take the one's complement to have a bit mask.
        this.mask = ~(-1L << nbBits);
    }


    /**
     * Returns the hash of a string. The hash is encoded in a long (64 bits) but
     * only the first nbBits are relevant.
     *
     * @param str  the string to hash
     * @return the hash, encoded in a long
     */
    public long hash(String str) {
        // It's important to have uniform 0s and 1s on 64 bits.
        long hash = (long) str.hashCode();
        return (this.m + this.n * ((hash << 32) + hash)) & this.mask;
    }


    /** Instantiates a new random hash function. */
    public static HashFunction randomInstance(int nbBits) {
        long m = PRNG.nextLong();
        long n = PRNG.nextLong();
        return new HashFunction(m, n, nbBits);
    }
}
