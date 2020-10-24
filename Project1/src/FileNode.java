import java.io.*;
import java.util.ArrayList;

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

    public void decompressFile(String dir,FileInputStream fis) throws IOException {
        ObjectInputStream ois = new ObjectInputStream(fis);

    }
}
