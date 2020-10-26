import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FileNode implements Serializable {
    public String name;
    public int size;

    public FileNode(String name) {
        this.name = name;
    }

    //解压区
    public void creatFile(String dir) throws IOException {
        File file = new File(dir + "\\" + name);
        if (file.createNewFile()) System.out.println("Create file:" + dir + "\\" + name + " Success!");
        else System.out.println("Create file:" + dir + "\\" + name + " Fail! It may already existed.");
    }

    public void decompressFile(String dir,FileInputStream fis) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(fis);
        BufferedInputStream bis = new BufferedInputStream(ois);

        HashMap<Byte,String> oldMap = (HashMap<Byte,String>) ois.readObject();
        FileNode fn = (FileNode) ois.readObject();

        HashMap<String,Byte> map = new HashMap<>();

        for (Byte key : oldMap.keySet()) {
            map.put(oldMap.get(key),key);
        }

        //开始还原文件
        FileOutputStream fos = new FileOutputStream(dir + "\\" + name);
        BufferedOutputStream bos = new BufferedOutputStream(fos);

        //存储文件内容
        StringBuilder strBuilder = new StringBuilder();//扫描区
        String str;//最终存入内容

        int count = 0;
        int start;
        boolean failed;

        Byte b;
        while (count < fn.size && strBuilder.length() < 20) {
            //读一个Byte
            start = strBuilder.length();
            strBuilder.append(Utils.getBinaryString((byte) bis.read()));
            System.out.println("read new:" + strBuilder.toString());
            do {
                failed = true;
                for (int i = start; i < strBuilder.length() && failed && count < fn.size && strBuilder.length() < 20; i++) {
                    str = strBuilder.substring(0,i);
                    b = map.get(str);
                    if (b != null) {//找到了写入目标
                        bos.write(b);//写入
                        count++;
                        strBuilder.delete(0,str.length());//改动缓冲区
                        start=0;//从第一个开始找
                        System.out.println("Write:" + str + ",current:" + strBuilder.toString());
                        failed = false;//成功找到了！进行到下次循环
                        bos.flush();
                    }
                }
            } while (!failed);
        }
        bos.flush();
        bos.close();
    }
}
