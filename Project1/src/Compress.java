import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Compress {
    public static void main(String[] args) throws IOException {
        //根据路径拿到要压缩的文件
        String inPath = "C:\\Users\\AAA\\Desktop\\DSA仓库\\testcases\\testcase02NormalSingleFile\\1.txt";
        //String path = "C:\\Users\\AAA\\Desktop\\DSA仓库\\testcases\\testcase03XLargeSingleFile\\3.csv";
        //String path="C:\\Users\\AAA\\Desktop\\DSA仓库\\testcases\\testcase01EmptyFile\\empty.txt";

        byte[] bytes = FileUtil.readFile(inPath);
        if (bytes.length == 0) {
            //空文件
            System.out.println("Empty file");
        } else {
            //统计次数、构建huffman tree、构建编码字典（封装在了tree中）
            HashMap<Byte, String> map = Tree.getMap(bytes);
            System.out.println(map);

            String outPath = "C:\\Users\\AAA\\Desktop\\DSA仓库\\testcases\\1test.txt";
            FileUtil.writeFile(outPath, map, bytes,false);
        }
    }
}
