package test;

import org.junit.Test;
import service.*;
import service.impl.YYCompressImpl;

import java.io.*;

public class test {
    @Test
    public void test() {
        File file = new File("C:\\Users\\15344\\Desktop\\DSA_Repository\\testcase");
        System.out.println(file.length());
    }

    @Test
    public void compress() throws IOException {
        String folderPath = "C:\\Users\\AAA\\Desktop\\DSA仓库\\testcases";
        YYCompressImpl yyCompress = new YYCompressImpl("C:\\Users\\AAA\\Desktop\\DSA仓库");
        yyCompress.compress(folderPath);

    }

    @Test
    public void depress() throws IOException, ClassNotFoundException {
        YYCompress depress = new YYCompressImpl("C:\\Users\\AAA\\Desktop\\DSA仓库\\depress");
        depress.decompress("C:\\Users\\AAA\\Desktop\\DSA仓库\\testcases.YYCPack");
    }

    @Test
    public void iterateFolder() {
        FolderNode folderNode = new FolderNode("C:\\Users\\AAA\\Desktop\\DSA仓库\\testcases");
        System.out.println(folderNode.getPath());
    }
}
