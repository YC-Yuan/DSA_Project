import java.io.*;
import java.util.HashMap;

public class FileNode implements Serializable {
    public byte[] data;
    public String path;
    public HashMap<Byte, String> map;
    public int size;

    public FileNode(String path) {
        this.path = path;
    }

    //压缩区
    public void write(String desPath) throws IOException {
        WriteUtil writeUtil = new WriteUtil(desPath);
        writeUtil.compress(path);
    }

    //解压区
    public void creatPath() {
        File file = new File(path);
        if (file.mkdirs())
            System.out.println("Make dirs for:" + path + " Success!");
        else System.out.println("Make dirs for:" + path + " Fail!");
    }

    public void creatFile() throws IOException {
        File file = new File(path);
        if (file.createNewFile()) System.out.println("Create file:" + (path) + " Success!");
        else System.out.println("Create file:" + (path) + " Fail!");
    }
}
