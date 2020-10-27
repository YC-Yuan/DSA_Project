package service;

import util.Utils;

import java.io.*;
import java.util.ArrayList;

public class FolderNode implements Serializable {
    public FolderNode[] folders;
    public FileNode[] files;
    public String name;
    public FolderNode parent = null;

    //根据地址读入文件夹
    public FolderNode(String path) {
        this.name = Utils.getFolderName(path);

        ArrayList<FolderNode> folders = new ArrayList<>();
        ArrayList<FileNode> files = new ArrayList<>();

        File file = new File(path);
        File[] fileArray = file.listFiles();//遍历path下的文件和目录，放在File数组中
        if (fileArray != null) {
            for (File f : fileArray) {
                String fileName = f.getName();//获取文件或目录名
                if (!f.isDirectory()) {//是文件
                    files.add(new FileNode(fileName, this));
                } else {//是文件夹
                    folders.add(new FolderNode(path + "\\" + fileName,this));
                }
            }
        }
        this.files = files.toArray(new FileNode[0]);
        this.folders = folders.toArray(new FolderNode[0]);
    }

    public FolderNode(String path, FolderNode parent) {
        this(path);
        this.parent = parent;
    }

    //向上追溯到父文件夹,包含自己
    public String getPath() {
        FolderNode currentFolder = this;
        StringBuilder path = new StringBuilder(this.name);
        while (currentFolder.parent != null) {
            currentFolder = currentFolder.parent;
            path.insert(0, currentFolder.name + "\\");
        }
        return path.toString();
    }

    //按照储存顺序print
    public void print() {
        System.out.println("Folder:" + name);
        for (FolderNode folder : folders) folder.print();
        for (FileNode file : files) System.out.println("File:" + file.name);
    }
}
