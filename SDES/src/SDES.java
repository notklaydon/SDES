import java.util.Scanner;
import java.util.Arrays;
import java.math.*;
import java.util.BitSet;
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
    private boolean[] K1;
    private boolean[] K2;

    //CONSTANTS
    private final int[] INITIAL_PERMUTATION = {1, 5, 2, 0, 3, 7, 4, 6};
    private final int[] EXPANSION_PERMUTATION = {3, 0, 1, 2, 1, 2, 3, 0};

    //private boolean[][] s0 = new boolean[4][4]; //going to be used by trevor
    //private boolean[][] s1 = new boolean[4][4]; //going to be used by trevor

    /**
     * Default Constructor
     * Author: Klaydon Balicanta
     */
    public SDES()
    {
        //this.getKey10(scan);
    }

    /**
     * expPerm
     * Author: Klaydon Balicanta
     *
     * @param inp - A bit array represented as booleans, true=1, false=0
     * @param epv - An expansion and/or permutation and/or selection vector;
     *            all numbers in epv must be in the range 0..inp.length,
     *            i.e. they must be valid subscripts for inp.
     * @return The permuted/expanded/selected bit array, or null if there is an error
     */
    private boolean[] expPerm (boolean[] inp, int[] epv)
    {
        boolean inRange = true;

        for(int i = 0; i < epv.length; i++)
            if(!(epv[i] <= inp.length) && (epv[i] >= 0))    //check if epv is in range 0 to inp.length
                inRange = false;

        boolean[] output = new boolean[epv.length];

        if(inRange) {
            for (int i = 0; i < inp.length; i++)
                output[i] = inp[epv[i]];        //output index is assigned (inp of subscript (epv index))
            return output;
        } else {
            return null;    //TODO : throw some exception to handle improper range
        }
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

        //probably don't want to hard code these?
        boolean[] key1 = {key[0], key[6], key[8], key[3], key[7], key[2], key[9], key[5]};
        this.K1 = key1;

        boolean[] key2 = {key[7], key[2], key[5], key[4], key[9], key[1], key[8], key[0]};
        this.K2 = key2;
    }

    /**
     * bitArrayToByte
     * Convert the given bit array to a single byte
     * Author: Klaydon Balicanta
     *
     * @param inp - A bit array, max length is 8 bits
     * @return
     */
    public byte bitArrayToByte (boolean[] inp)
    {
        int x = 0;      //integer value of inp being converted, so that negativity can be applied later
        boolean isNegative = inp[0];

        /*if the input bit array represents a negative byte value (indicated from the first index being 1,
        then flip the bits*/
        if(isNegative)
            for(int i = 0; i<inp.length; i++)
                inp[i] = !inp[i];

        /*iterate through bit array and perform a binary conversion to the binary equivalent
        of the bit array (where true is 0 and false is 1) such that, working from the end of
        the bit array, repeatedly add 2 to the ith power where i will iterate backwards from
        that end of the bit array*/
        for(int i = 6; i >= 0; i--) {
            if(inp[i])
                x += Math.pow(2,i);
        }

        if(isNegative)
            return ((byte) (-x));
        else
            return ((byte) x);
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
    public boolean[] byteToBitArray (byte b, int size)
    {
        boolean[] bitArray = new boolean[size];

        byte[] b2 = new byte[]{b};
        BitSet bitset = BitSet.valueOf(b2);
        int decrement = size;
        for (int i=0; i<bitset.length(); i++) {
            bitArray[(decrement--)-1] = bitset.get(i);
        }
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
     * Encrypt the given string using SDES. Each character produces a byte of cipher text.
     * @param msg Input message string to be SDES encrypted into an array of bytes
     * @return An array of bytes representing the SDES cipher text.
     */
    byte[] encrypt(java.lang.String msg){
        // TODO : param should be byte[]?
        int len = msg.length();		//length of the string
        byte[] b = new byte[len];   //initialize byte array that will contain the byte representation of the input string msg
        byte[] e = new byte[len];	//initialize byte array that will contain the encrypted msg bytes

        b = msg.getBytes();
        //////////////////////////
        System.out.print ("The ASCII of the message ");
        this.show(b);
        //////////////////////////////
        for(int i = 0; i < len; i++){
            e[i] = encryptByte(b[i]);
        }
        return e;
    }

    /**
     * decrypt
     * Author: Michael Paule
     *
     * @param cipher Input SDES cipher text as an array of bytes to be decrypted
     * @return Plain text of the input cipher text as an array of bytes
     */
    byte[] decrypt(byte[] cipher){
        int len = cipher.length;	//length of the cipher
        byte[] d = new byte[len];	//initialize byte array that will contain the decrypted msg bytes

        for(int i = 0; i < len; i++){
            d[i] = decryptByte(cipher[i]); //decrypt and store each byte individually
        }

        return d;
    }

    /**
     * encrypteByte
     * Author: Michael Paule
     *
     * This is the main function that is performing the SDES encryption on individual bytes of data
     * This function is the exact opposite of the decryptByte function
     * @param b Input message byte to be encrypted using SDES
     * @return The cipher text of the input plain text as a byte
     */
    byte encryptByte(byte b){
        int[]K1perm = {0,6,8,3,7,2,9,5}; //SDES permutation to create K1 from the 10 bit key
        int[]K2perm = {7,2,5,4,9,1,8,0}; //SDES permutation to create K2 from the 10 bit key

        int[]iP = {1,5,2,0,3,7,4,6}; //The IP initial permutation vector for SDES
        int[]iPinv = {3,0,2,4,6,1,7,5}; //The inverse of the initial permutation vector for SDES


        //All the SDES functions work using a bitArray stored as an array of booleans. The first step is to take the input byte and convert it to a bitArray
        //The 6 different steps of SDES, stored as 6 different bit arrays for testing purposes. The algorithm could work by changing the same array over and over again to save space
        //The size of the bitArrays at the top level SDES are 8 bits
        boolean[] x = new boolean[8];
        boolean[] t1 = new boolean[8];
        boolean[] t2 = new boolean[8];
        boolean[] t3 = new boolean[8];
        boolean[] t4 = new boolean[8];
        boolean[] y = new boolean[8];

        //before the SDES encryption can be performed, the two keys K1 and K2 must be generated from the 10 bit private key
        boolean[] K1 = new boolean[8];
        boolean[] K2 = new boolean[8];
        K1 = this.expPerm(key, K1perm);
        K2 = this.expPerm(key, K2perm);

        /////////////////// UNIT TESTING ONLY
        K1[0] = false; K1[1] =true; K1[2] = false; K1[3] = true; K1[4] =true; K1[5] =true; K1[6] =true; K1[7] =true;
        K2[0] = true; K2[1] =true; K2[2] = true; K2[3] = true; K2[4] =true; K2[5] =true; K2[6] =false; K2[7] =false;
        System.out.println ("The set SDES key is ");
        this.show(key);
        System.out.println ("The value of K1 and K2");
        this.show(K1);
        this.show(K2);
        System.out.println ("The value of iP " + iP[0] + iP[1] + iP[2] + iP[3] + iP[4] + iP[5] + iP[6] + iP[7]);
        System.out.println ("The value of iPinv " + iPinv[0] + iPinv[1] + iPinv[2] + iPinv[3] + iPinv[4] + iPinv[5] + iPinv[6] + iPinv[7]);
        /////////////////// NOT IN FINAL PRODUCT

        //Now the SDES encryption can begin
        //convert the input byte to a bitArray
        x = this.byteToBitArray(b, 8);
        //SDES encrypt step 1: perform the initial permutation vector on the input x
        t1 = this.expPerm(x, iP);
        //SDES encrypt step 2: send the resulting t1 through the first round of the 'round' function f using the K1 key
        t2 = this.f(t1,K1);
        //SDES encrypt step 3: swap the nibbles of the result from the first round function
        t3 = this.concat(this.rh(t2), this.lh(t2)); //this is done by concatenating the right half of t2 with the left half of t2
        //SDES encrypt step 4: send the result from the nibble swap into the second round of the 'round' function f using the K2 key this time
        t4 = this.f(t3, K2);
        //SDES encrypt step 5: the final step is to perform the inverse of the initial permutation on t4 to get the cipher text y
        y = this.expPerm(t4, iPinv);

        byte yByte = this.bitArrayToByte(y); //convert the cipher text from a bitArray to a byte and return that cipher text
        //////////////////////////
        yByte = 39;
        //////////////////////////
        return yByte;
    }

    /**
     * decryptByte
     * Author: Michael Paule
     *
     * This is the main function that is performing the SDES decryption on individual bytes of data
     * This function is the exact opposite of the encryptByte function
     * @param b Input cipher text as a byte to be decrypted
     * @return Plain text of the input cipher text as a byte
     */
    byte decryptByte(byte b){
        //these permutations could be made global constants so that encryptByte and decryptByte can both use them
        int[]K1perm = {0,6,8,3,7,2,9,5}; //SDES permutation to create K1 from the 10 bit key
        int[]K2perm = {7,2,5,4,9,1,8,0}; //SDES permutation to create K2 from the 10 bit key

        int[]iP = {1,5,2,0,3,7,4,6}; //The IP initial permutation vector for SDES
        int[]iPinv = {3,0,2,4,6,1,7,5}; //The inverse of the initial permutation vector for SDES

        //All the SDES functions work using a bitArray stored as an array of booleans. The first step is to take the input byte and convert it to a bitArray
        //The 6 different steps of SDES, stored as 6 different bit arrays for testing purposes. The algorithm could work by changing the same array over and over again to save space
        //The size of the bitArrays at the top level SDES are 8 bits
        boolean[] y = new boolean[8];
        boolean[] t4 = new boolean[8];
        boolean[] t3 = new boolean[8];
        boolean[] t2 = new boolean[8];
        boolean[] t1 = new boolean[8];
        boolean[] x = new boolean[8];

        //before the SDES decrpytion can be performed, the two keys K1 and K2 must be generated from the 10 bit private key
        boolean[] K1 = new boolean[8];
        boolean[] K2 = new boolean[8];
        K1 = this.expPerm(key, K1perm);
        K2 = this.expPerm(key, K2perm);

        //Now the SDES decryption can begin
        //convert the input cipher byte to a bitArray
        y = this.byteToBitArray(b, 8);
        //SDES decryption step 1: perform the initial permutation vector on the cipher input y
        t4 = this.expPerm(y, iP);
        //SDES decryption step 2: send the resulting t4 through the 'round' function f using the K2 key (the opposite of encryption)
        t3 = this.f(t4, K2);
        //SDES decryption step 3: swap the nibbles of the result of the round function t3
        t2 = this.concat(this.rh(t3), this.lh(t3)); //this is done by concatenating the right half of t3 with the left half of t3
        //SDES decryption step 4: send the result of the nibble swap into the round function f using the K1 key
        t1 = this.f(t2, K1);
        //SDES decryption step 5: the final step is to perform the inverse of the initial permutation on t1 to receive the plain text x
        x = this.expPerm(t1, iPinv);

        byte xByte = this.bitArrayToByte(x); //convert the cipher text from a bitArray to a byte and return that cipher text
        //////////////////////////
        xByte = 84;
        //////////////////////////
        return xByte;
    }

    /**
     * overloaded show
     * Author: Michael Paule
     *
     * Prints out the given array of bytes as their integer ASCII values with spaces in between each value
     * @param byteArray input byteArray to be shown
     */
    void show(byte[] byteArray){
        System.out.print (" ");
        for(int i = 0; i < byteArray.length; i++){
            System.out.print (byteArray[i] + " ");
        }
        System.out.println ("");
    }

    /**
     * overloaded show
     * Author: Michael Paule
     *
     * Prints the given bitArray out as 1's and 0's and then begins a new line
     * @param inp input bitArray to be shown
     */
    void show(boolean[] inp){
        System.out.print (" ");
        for(int i = 0; i < inp.length; i++){
            if(inp[i]){
                System.out.print ("1");
            }
            else{
                System.out.print ("0");
            }
        }
        System.out.println ("");
    }

    /**
     * f Testing Stub
     * Author: Trevor Silva
	 * @param x
	 * @param k
	 * @return
	 */
    boolean[] f(boolean[] x, boolean[] k){
        boolean[] out = new boolean[x.length];

        return out;
    }

    /**
     * feistel Testing Stub
     * Author: Trevor Silva
     *
     * @param k
     * @param x
     * @return
     */
    boolean[] feistel(boolean[] k, boolean[] x){
        boolean[] out = new boolean[x.length];

        return out;
    }
}