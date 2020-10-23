import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class WriteUtil {
    public String desPath;

    public WriteUtil(String desPath) throws IOException {
        this.desPath = desPath;
        File file = new File(desPath);
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    public void writeFolders(FolderNode folderNode) throws IOException {
        OutputStream ops = new FileOutputStream(desPath, true);

        ObjectOutputStream oop = new ObjectOutputStream(ops);
        oop.writeObject(folderNode);
        oop.close();
        ops.close();
    }

    //输入要压缩进去的文件，往desPath
    public void compress(String oriPath) throws IOException {
        ShowTime showTime = new ShowTime();

        //读取部分
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(oriPath));
        int inSize = in.available();
        System.out.println("file size= " + inSize);
        //遍历文件夹中所有内容
        byte[] bytes = new byte[inSize];
        for (int i = 0; i < inSize; i++) {
            bytes[i] = (byte) in.read();
        }
        in.close();

        //写入部分
        HashMap<Byte, String> map = Tree.getMap(bytes);

        //压缩时只能选择存在的目录，所以创建文件就够了
        OutputStream ops = new FileOutputStream(desPath, true);

        ObjectOutputStream oop = new ObjectOutputStream(ops);
        BufferedOutputStream bos = new BufferedOutputStream(ops);

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
                bos.write((byte) Integer.parseInt(str, 2));
            }
        }
        //最后可能会剩下一些，补0处理
        if (strBuilder.length() != 0) {
            while (strBuilder.length() < 8) {
                strBuilder.append("0");
            }
            bos.write((byte) Integer.parseInt(strBuilder.toString(), 2));
        }
        bos.flush();
        ops.close();
        oop.close();
        bos.close();
        showTime.printTime("Compress " + oriPath + " cost:");
    }
}
