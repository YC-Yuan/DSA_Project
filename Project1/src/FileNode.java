import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class FileNode implements Serializable {
    public byte[] data;
    public String name;
    public String path;
    public HashMap<Byte, String> map;
    
    public FileNode(String path,String name){
        this.path=path;
        this.name=name;
    }

    public FileNode(String path,  String name, HashMap<Byte, String> map, byte[] data) {
        this.path=path;
        this.name = name;
        this.map = map;
        this.data = data;
    }

    public void creatPath() {
        File file = new File(path );
        if (file.mkdirs())
            System.out.println("Make dirs for:" + this.name + " Path:" + path + " Success!");
        else System.out.println("Make dirs for:" + this.name + " Path:" + path + " Fail!");
    }

    public void creatFile() throws IOException {
        File file = new File(path + name);
        if (file.createNewFile()) System.out.println("Create file:" + (path + name) + " Success!");
        else System.out.println("Create file:" + (path + name) + " Fail!");
        FileUtil.writeFile(path + name, map, data, true);
    }
}
