import org.junit.Test;

import java.io.*;
import java.util.HashMap;

public class test {

    @Test
    public void fileCompressTest() throws IOException {
        String desPath = "C:\\Users\\AAA\\Desktop\\DSA仓库\\singleFile.yyc";
        Compress compress = new Compress(desPath);
        FileNode fn = new FileNode("small.txt");
        compress.compress(fn, "C:\\Users\\AAA\\Desktop\\DSA仓库");
    }

    @Test
    public void fileDepressTest() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("C:\\Users\\AAA\\Desktop\\DSA仓库\\singleFile.yyc");
        ObjectInputStream ois = new ObjectInputStream(fis);
        BufferedInputStream bis = new BufferedInputStream(ois);

        HashMap<Byte, String> oldMap = (HashMap<Byte, String>) ois.readObject();
        FileNode fn = (FileNode) ois.readObject();

        HashMap<String, Byte> map = new HashMap<>();

        for (Byte key : oldMap.keySet()) {
            map.put(oldMap.get(key), key);
        }

        System.out.println(fn.name + " " + fn.size);
        System.out.println(map);

        //开始还原文件
        FileOutputStream fos = new FileOutputStream("C:\\Users\\AAA\\Desktop\\DSA仓库\\deSingleFile.txt");
        BufferedOutputStream bos = new BufferedOutputStream(fos);
    }

    @Test
    public void folderCompress() throws IOException {
        ShowTime showTime=new ShowTime();

        String compressPath = "C:\\Users\\AAA\\Desktop\\DSA仓库\\testcases\\testcase02NormalSingleFile";
        String desPath = "C:\\Users\\AAA\\Desktop\\DSA仓库\\testfile.yyc";

        FolderNode folderNode = new FolderNode(compressPath);
        folderNode.print();
        showTime.printTime("Read folders cost:");

        folderNode.compress(desPath, Utils.rootPath);
        showTime.printTime("Compress cost in all:");
    }

    @Test
    public void folderDecompress() throws IOException, ClassNotFoundException {
        ShowTime showTime = new ShowTime();

        Decompress decompress = new Decompress("C:\\Users\\AAA\\Desktop\\DSA仓库\\testfile.yyc", "C:\\Users\\AAA\\Desktop\\DSA仓库\\decompress");
        decompress.decompress();

        showTime.printTime("Decompress all cost:");
    }

    @Test
    public void test() throws IOException {
    }
}
