import org.junit.Test;

import java.io.*;
import java.util.HashMap;

public class test {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ShowTime showTime = new ShowTime();

/*        String compressPath = "C:\\Users\\AAA\\Desktop\\DSA仓库\\testcases\\testcase05NomalFolder";
        String desPath = "C:\\Users\\AAA\\Desktop\\DSA仓库\\testfile.yyc";

        FolderNode folderNode = new FolderNode(compressPath);
        folderNode.print();
        showTime.printTime("Read folders cost:");
        showTime.updateTime();

        folderNode.compress(desPath, Utils.rootPath);*/

        Decompress decompress = new Decompress("C:\\Users\\AAA\\Desktop\\DSA仓库\\testfile.yyc", "C:\\Users\\AAA\\Desktop\\DSA仓库\\decompress");
        decompress.decompress();

        showTime.printTime("Compress all cost:");
    }

    @Test
    public void test() throws IOException {
        FileOutputStream fos = new FileOutputStream("C:\\Users\\AAA\\Desktop\\DSA仓库\\stream.txt");

    }
}
