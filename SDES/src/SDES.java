import java.util.Scanner;
import java.util.Arrays;
import java.math.*;
/**
 * ASSIGNMENT:  Project 2 - SDES
 * AUTHORS:     Klaydon Balicanta
 *              Michael Paule
 *              Trevor Silva
 *
 * PROFESSOR:   Seth Bergmann
 * CLASS:       Computer Cryptography
 *
 * SUBMISSION DUE DATES
 * INDIVIDUAL:  8 November 2016
 * FINAL:       15 November 2016
 */

public class SDES {

    Scanner scan = new Scanner(System.in);
    private boolean[] key = new boolean[10];
    //private boolean[][] s0 = new boolean[4][4]; //going to be used by trevor
    //private boolean[][] s1 = new boolean[4][4]; //going to be used by trevor

    /**
     * Default Constructor
     * Author: Klaydon Balicanta
     */
    public SDES()
    {
        this.getKey10(scan);
    }

    /**
     * expPerm
     * Author: Klaydon Balicanta
     *
     * @param inp - A bit array represented as booleans, true=1, false=0
     * @param epv - An expansion and/or permutation and/or selection vector; all numbers in epv must be in the range 0..inp.length, i.e. they must be valid subscripts for inp.
     * @return The permuted/expanded/selected bit array, or null if there is an error
     */
    private boolean[] expPerm (boolean[] inp, int[] epv)
    {
        boolean[] b = new boolean[1];
        return b;
    }

    /**
     * getKey10
     * Get a 10 bit key from the keyboard, such as 1010101010. Store it as an array of booleans in a field.
     * Author: Klaydon Balicanta
     *
     */
    private void getKey10 (java.util.Scanner scanner)
    {
        String s;
        do
        {
            System.out.println("Enter 10-bit binary key: ");
            s = scanner.next();
        }while(s.length() != 10);
    }

    /**
     * bitArrayToByte
     * Convert the given bit array to a single byte
     * Author: Klaydon Balicanta
     *
     * @param inp - A bit array, max length is 8 bits
     * @return
     */
    private byte bitArrayToByte (boolean[] inp)
    {
        byte b = 0;
        for(int i = 7; i > 0; i--) {
            if(inp[i]) {
                b += Math.pow(2,i);
            }
        }
        return b;
    }

    /**
     * byteToBitArray
     * Convert the given byte to a bit array, of the given size
     * Author: Klaydon Balicanta
     *
     * @param b
     * @param size - The size of the resulting bit array. The >>> can be used for an unsigned right shift
     * @return
     */
    private boolean[] byteToBitArray (byte b, int size)
    {
        boolean[] bitArray = new boolean[size];

        return bitArray;
    }

    /**
     * byteArrayToString
     * Convert the given byte array to a String
     * Author: Klaydon Balicanta
     *
     * @param inp - An array of bytes, hopefully storing the codes of printable characters.
     * @return The characters as a String
     */
    public java.lang.String byteArrayToString(byte[] inp)
    {
        String result = "";
        for(int i = 0; i < inp.length; i++)
        {
            result += inp[i];
        }
        return result;
    }

    /**
     * lh
     * Left half of x, L(x)
     * Author: Klaydon Balicanta
     *
     * @param inp
     * @return a bit array which is the left half of the parameter, inp.
     */
    private boolean[] lh (boolean[] inp)
    {
        return Arrays.copyOfRange(inp, 0, ((inp.length)/2));
    }

    /**
     * rh
     * Right half of x, R(x)
     * Author: Klaydon Balicanta
     *
     * @param inp
     * @return a bit array which is the right half of the parameter, inp.
     */
    private boolean[] rh (boolean[] inp)
    {
        return Arrays.copyOfRange(inp, ((inp.length)/2), inp.length-1);
    }

    /**
     * xor
     * Exclusive OR. x a y must have the same length. x xor y is the same as x != y
     * Author: Klaydon Balicanta
     *
     * @param x
     * @param y
     * @return
     */
    private boolean[] xor (boolean[] x, boolean[] y)
    {
        // TODO: throw an exception if parameters' lengths aren't equal...otherwise buffer the shorter with x or 0
        if(x.length != y.length) {
            System.out.println("boolean arrays are not of equal length and cannot be XORed properly");
        }

        final int ARRAY_SIZE = x.length;
        boolean[] b = new boolean[ARRAY_SIZE];

        for(int i = 0; i < ARRAY_SIZE; i++)
        {
            b[i] = (x[i] != y[i]);
        }

        return b;
    }

    /**
     * concat
     * Concatenate the two bit arrays, x||y
     * Author: Klaydon Balicanta
     *
     * @param x
     * @param y
     * @return the concatenation of x and y
     */
    private boolean[] concat (boolean[] x, boolean[] y)
    {
        final int firstLength = x.length, secondLength = y.length, bothLength = firstLength+secondLength;
        boolean[] both = new boolean[bothLength];
        for(int i = 0; i < both.length; i++) {
            if(!(i > firstLength))
                both[i] = x[i];
            else
                both[i] = y[i-firstLength];
        }
        return both;
        //boolean[] both = (boolean[])ArrayUtils.addAll(x, y);
    }

    /* ******************************************************/
    /* ******************************************************/
    /* ******************************************************/
    /* ******************************************************/



    /**
     * encrypt
     * Author: Michael Paule
     *
     * @param someBytes
     */
    public void encrypt(byte[] someBytes)
    {
        /*String s;
        int len = someBytes.length;
        byte[] b = new byte[len];

        b = */
    }

    /**
     * decrypt
     * Author: Michael Paule
     *
     * @param someBytes
     * /
    public void decrypt(byte[] someBytes)
    {

    }
    */

    /**
     * encrypteByte
     * Author: Michael Paule
     *
     * @param someByte
     */
    public void encryptByte(byte someByte)
    {

    }

    /**
     * decryptByte
     * Author: Michael Paule
     *
     * @param someByte
     */
    public void decryptByte(byte someByte)
    {

    }

    /**
     * overloaded show
     * Author: Michael Paule
     *
     * @param someBytes
     */
    public void show(byte[] someBytes)
    {

    }

    /**
     * overloaded show
     * Author: Michael Paule
     *
     * @param someBooleans
     */
    public void show(boolean[] someBooleans)
    {

    }

    /*
    /**
     * f
     * Author: Trevor Silva
     *
     * /
    public f(x,k)
    {

    }

    /**
     * feistel
     * Author: Trevor Silva
     *
     * /
    public feistel(k,x) = F(k,x)
    */
}
