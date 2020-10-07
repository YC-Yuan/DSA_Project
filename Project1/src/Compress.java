import java.io.*;
import java.util.HashMap;

public class Compress {
    public static void main(String[] args) throws IOException {
        //根据路径拿到文件
        String inputFile="C:\\Users\\Administrator\\Desktop\\test.pcm";
        String outputFile="C:\\Users\\Administrator\\Desktop\\test_result.pcm";

        InputStream inputStream=new FileInputStream(inputFile);
        OutputStream outputStream=new FileOutputStream(outputFile);

        int byteRead;
        while((byteRead=inputStream.read()) != -1){
            outputStream.write(byteRead);
        }
        inputStream.close();
        outputStream.close();

        //遍历文件夹中所有内容

        //对每个内容，读取文件名、文件内容(char[])

        char[] chars = new char[]{};
        HashMap<Character, byte[]> map = Tree.useTree(Tree.buildTree(Statistic.statistics(chars)));

    }
}
