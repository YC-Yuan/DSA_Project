import java.io.*;
import java.util.ArrayList;

public class FolderNode implements Serializable {
    public FolderNode[] folders;
    public FileNode[] files;
    public String name;

    //根据地址读入文件夹
    public FolderNode(String path) {
        if (Utils.rootPath.equals("")) Utils.rootPath = path;
        this.name = Utils.getDirName(path);

        ArrayList<FolderNode> folders=new ArrayList<>();
        ArrayList<FileNode> files=new ArrayList<>();


        File file = new File(path);
        File[] fs = file.listFiles();//遍历path下的文件和目录，放在File数组中
        if (fs != null) {
            for (File f : fs) {
                String fileName = f.getName();//获取文件或目录名
                if (!f.isDirectory()) {//是文件
                    files.add(new FileNode(fileName));
                } else {//是文件夹
                    folders.add(new FolderNode(path + "\\" + fileName));
                }
            }
        }
        this.files=files.toArray(new FileNode[0]);
        this.folders=folders.toArray(new FolderNode[0]);
    }

    //按照储存顺序print
    public void print() {
        System.out.println("Folder:" + name);
        for (FolderNode folder : folders) folder.print();
        for (FileNode file : files) System.out.println("File:" + file.name);

    }

    //压缩区
    //按照储存顺序(先文件，后文件夹)写入文件
    //rootPath为此文件/文件夹所在的绝对路径
    public void compress(String desPath, String rootPath) throws IOException {
        new Compress(desPath).writeFolders(this);
        writeFile(desPath, rootPath);
    }

    private void writeFile(String desPath, String rootPath) throws IOException {
        Compress compress = new Compress(desPath);
        for (FileNode file : files) {
            System.out.println("Compressing:" + file.name);
            compress.compress(file, rootPath);
        }
        for (FolderNode fd : folders) {
            System.out.println("writeFold evoked:" + rootPath + "\\" + fd.name);
            fd.writeFile(desPath, rootPath + "\\" + fd.name);
        }
    }

    //解压区
    public void creatPath(String dir) {
        File file = new File(dir);
        if (file.mkdirs())
            System.out.println("Make dirs for:" + name + " Success!");
        else System.out.println("Make dirs for:" + name + " Fail! It may already existed.");
    }
}
