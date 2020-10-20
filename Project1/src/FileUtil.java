import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class FileUtil {
    public static byte[] readFile(String path) throws IOException {
        ShowTime showTime = new ShowTime();
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(path));
        int inSize = in.available();
        System.out.println("file size= " + inSize);
        //遍历文件夹中所有内容
        byte[] bytes = new byte[inSize];
        for (int i = 0; i < inSize; i++) {
            bytes[i] = (byte) in.read();
        }
        in.close();
        showTime.printTime("read running time:");
        return bytes;
    }

    public static void writeObject(String path,Object object,boolean remain) throws IOException {
        ShowTime showTime=new ShowTime();
        OutputStream outputStream;
        if (remain) outputStream = new FileOutputStream(path, true);
        else outputStream = new FileOutputStream(path);

        //用对象输出流存储文件头
        ObjectOutputStream oop = new ObjectOutputStream(outputStream);
        oop.writeObject(object);

        oop.close();
        outputStream.close();
    }

    public static void writeFile(String path, HashMap<Byte, String> map, byte[] bytes, boolean remain) throws IOException {
        ShowTime showTime = new ShowTime();
        OutputStream outputStream;

        if (remain) outputStream = new FileOutputStream(path, true);
        else outputStream = new FileOutputStream(path);
        BufferedOutputStream out = new BufferedOutputStream(outputStream);
        //用对象输出流存储文件头
        ObjectOutputStream oop = new ObjectOutputStream(outputStream);
        oop.writeObject(map);

        //存储文件内容
        StringBuilder strBuilder = new StringBuilder();
        String str;
        for (Byte b : bytes) {
            //使用缓冲区，攒够8位编码则写入
            str = map.get(b);
            strBuilder.append(str);
            while (strBuilder.length() >= 8) {
                str = strBuilder.substring(0, 8);
                strBuilder.delete(0, 8);
                out.write((byte) Integer.parseInt(str, 2));
            }
        }
        //最后可能会剩下一些，补0处理
        if (strBuilder.length() != 0) {
            while (strBuilder.length() < 8) {
                strBuilder.append("0");
            }
            out.write((byte) Integer.parseInt(strBuilder.toString(), 2));
        }
        out.flush();
        out.close();

        showTime.printTime("write running time:");
    }
}
