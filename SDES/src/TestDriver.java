/**
 * Created by MKII on 10/26/2016.
 */
public class TestDriver
{
    public static void main(String args[])
    {
        String example = "this is an example";
        byte[] bytes = example.getBytes();

        System.out.println("Test : " + example);
        System.out.println("Text [Byte Format] : " + bytes);
        System.out.println("Text [Byte Format] : " + bytes.toString());
        for(int i = 0; i < bytes.length; i++) {
            System.out.println(bytes[i]);
        }

        String s = new String(bytes);
        System.out.println("Text Decrypted : " + s);
    }
}
