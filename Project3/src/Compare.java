import java.io.*;
import java.nio.Buffer;

public class Compare {
    public static void main(String[] args) throws IOException {
        File file1 = new File("src/compare1.txt");
        File file2 = new File("src/compare2.txt");

        InputStream is1 = new FileInputStream(file1);
        InputStream is2 = new FileInputStream(file2);

        BufferedReader reader1 = new BufferedReader(new InputStreamReader(is1));
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(is2));

        String str1, str2;
        int i = 1;
        while ((str1 = reader1.readLine()) != null) {
            str2 = reader2.readLine();
            if (!str1.equals(str2)) {
                System.out.println("第" + i + "行:");
                System.out.println(str1);
                System.out.println(str2);
            }
            i++;
        }
    }
}

