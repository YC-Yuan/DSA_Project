import org.junit.Test;

import java.io.*;
import java.util.HashMap;

public class test {
    @Test
    public void test() throws IOException {
        File file = new File("C:\\\\Users\\\\AAA\\\\Desktop\\\\DSA仓库\\\\testcases");
        if (!file.exists()) {
            file.createNewFile();
        }
    }
}
