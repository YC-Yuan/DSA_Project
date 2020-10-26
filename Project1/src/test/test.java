package test;

import org.junit.Test;
import service.*;
import service.impl.YYCompressImpl;
import util.ShowTime;
import util.Utils;

import java.io.*;

public class test {

    @Test
    public void fileCompressTest() throws IOException {
        String desPath = "C:\\Users\\AAA\\Desktop\\DSA仓库\\singleFile.yyc";
        Compress compress = new Compress(desPath);
        FileNode fn = new FileNode("16.svg");
        compress.compress(fn, "C:\\Users\\AAA\\Desktop\\DSA仓库\\testcases\\testcase02NormalSingleFile");
    }

    @Test
    public void fileDepressTest() throws IOException, ClassNotFoundException {
        ShowTime showTime=new ShowTime();

        FileNode fn = new FileNode("16.svg");
        FileInputStream is = new FileInputStream("C:\\Users\\AAA\\Desktop\\DSA仓库\\singleFile.yyc");
        fn.decompressFile("C:\\Users\\AAA\\Desktop\\DSA仓库",is);
        is.close();

        showTime.printTime("Function.Compress cost:");
    }

    @Test
    public void folderCompress() throws IOException {
        ShowTime showTime=new ShowTime();

        String compressPath = "C:\\Users\\AAA\\Desktop\\DSA仓库\\testcases\\testcase06SubFolders";
        String desPath = "C:\\Users\\AAA\\Desktop\\DSA仓库\\testfile.yyc";

        FolderNode folderNode = new FolderNode(compressPath);
        folderNode.print();
        showTime.printTime("Read folders cost:");

        //folderNode.compress(desPath, Utils.rootPath);
        showTime.printTime("Function.Compress cost in all:");
    }

    @Test
    public void folderDecompress() throws IOException, ClassNotFoundException {
        ShowTime showTime = new ShowTime();

        Decompress decompress = new Decompress("C:\\Users\\AAA\\Desktop\\DSA仓库\\testfile.yyc", "C:\\Users\\AAA\\Desktop\\DSA仓库\\decompress");
        decompress.decompress();

        showTime.printTime("Function.Decompress all cost:");
    }

    @Test
    public void test() throws IOException {
        String folderPath="C:\\Users\\AAA\\Desktop\\DSA仓库\\testcases";
        YYCompressImpl yyCompress = new YYCompressImpl("C:\\Users\\AAA\\Desktop\\DSA仓库\\testfile.yyc");
        yyCompress.folderCompress(folderPath);
    }
}
