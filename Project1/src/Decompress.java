import java.io.*;
import java.util.HashMap;

public class Decompress {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ShowTime showTime = new ShowTime();
        String inPath = "C:\\Users\\AAA\\Desktop\\DSA仓库\\testcases\\1test.txt";

        FileInputStream fileInputStream = new FileInputStream(inPath);
        BufferedInputStream in = new BufferedInputStream(fileInputStream);
        ObjectInputStream oin=new ObjectInputStream(fileInputStream);

        HashMap<Byte,String> map= (HashMap<Byte, String>) oin.readObject();
        System.out.println("map = " + map);

        int inSize = in.available();
        System.out.println("file size= " + inSize);
        //遍历文件夹中所有内容
        byte[] bytes = new byte[inSize];
        for (int i = 0; i < inSize; i++) {
            bytes[i] = (byte) in.read();
        }
        in.close();
        showTime.printTime("read running time:");
    }
}
