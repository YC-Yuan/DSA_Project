import java.io.*;
import java.util.HashMap;

public class test {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String inputFile = "C:\\Users\\AAA\\Desktop\\DSA仓库\\testcases\\1test.txt";
        InputStream inputStream = new FileInputStream(inputFile);

        ObjectInputStream ois=new ObjectInputStream(inputStream);
        HashMap<Character,String> map = (HashMap<Character, String>) ois.readObject();

        System.out.println(map);
    }
}
