/**
 * Created by MKII on 10/26/2016.
 */
import java.math.*;

public class TestDriver
{
    public static void main(String args[])
    {
        //byteArrayToString(s.bitArrayToByte(s.expPerm(mock, s.EXPANSION_PERMUTATION)));

        SDES s = new SDES();
        boolean[] mock = {false,false,false,false,false,false,false,false};
        boolean[] bitArray = new boolean[8];

        byte b = s.bitArrayToByte(mock);
        System.out.println(b);

        bitArray = s.byteToBitArray(b, 7);
        for(int i = 0; i < bitArray.length; i++) {
            System.out.print(bitArray[i]);
        }

        System.out.println("\n\nTEST " + (mock == bitArray));
    }
}