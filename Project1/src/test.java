import org.junit.Test;

import java.io.*;
import java.util.HashMap;

public class test {

    @Test
    public void fileCompressTest() throws IOException {
        String desPath = "C:\\Users\\15344\\Desktop\\DSA仓库\\singleFile.yyc";
        Compress compress = new Compress(desPath);
        FileNode fn = new FileNode("small.txt");
        compress.compress(fn, "C:\\Users\\15344\\Desktop\\DSA仓库");
    }

    @Test
    public void fileDepressTest() throws IOException, ClassNotFoundException {
        FileNode fn = new FileNode("depressedFile.txt");
        FileInputStream is = new FileInputStream("C:\\Users\\15344\\Desktop\\DSA仓库\\singleFile.yyc");
        fn.decompressFile("C:\\Users\\15344\\Desktop\\DSA仓库",is);
        is.close();
    }

    @Test
    public void folderCompress() throws IOException {
        ShowTime showTime=new ShowTime();

        String compressPath = "C:\\Users\\15344\\Desktop\\DSA仓库\\testcases\\testcase02NormalSingleFile";
        String desPath = "C:\\Users\\15344\\Desktop\\DSA仓库\\testfile.yyc";

        FolderNode folderNode = new FolderNode(compressPath);
        folderNode.print();
        showTime.printTime("Read folders cost:");

        folderNode.compress(desPath, Utils.rootPath);
        showTime.printTime("Compress cost in all:");
    }

    @Test
    public void folderDecompress() throws IOException, ClassNotFoundException {
        ShowTime showTime = new ShowTime();

        Decompress decompress = new Decompress("C:\\Users\\15344\\Desktop\\DSA仓库\\testfile.yyc", "C:\\Users\\15344\\Desktop\\DSA仓库\\decompress");
        decompress.decompress();

        showTime.printTime("Decompress all cost:");
    }

    @Test
    public void test() throws IOException {
    }
}
