package test;

import org.junit.Test;
import service.*;
import service.impl.YYCompressImpl;
import util.ShowTime;
import util.Utils;

import java.io.*;
import java.text.DecimalFormat;
import java.util.Arrays;

public class test {
    @Test
    public void test() {
        DecimalFormat df = new DecimalFormat("######0.00");
        String format = df.format(1.11111);
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
        depress.depress("C:\\Users\\AAA\\Desktop\\DSA仓库\\testcases.YYCPack");
    }

    @Test
    public void iterateFolder() {
        FolderNode folderNode = new FolderNode("C:\\Users\\AAA\\Desktop\\DSA仓库\\testcases");
        System.out.println(folderNode.getPath());
    }
}
