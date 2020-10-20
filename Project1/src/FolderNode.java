import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

public class FolderNode {
    public static void main(String[] args) {
        new FolderNode("C:\\Users\\AAA\\Desktop\\DSA仓库\\testcases");
    }

    public ArrayList<FolderNode> folders = new ArrayList<>();
    public ArrayList<FileNode> files = new ArrayList<>();
    public String path;

    public FolderNode(String path) {
        System.out.println("Folder:" + path + " loading");
        this.path = path;
        File file = new File(path);
        File[] fs = file.listFiles();//遍历path下的文件和目录，放在File数组中
        if (fs != null) {
            for (File f : fs) {
                String fileName = f.getName();//获取文件或目录名
                if (!f.isDirectory()) {//是文件
                    System.out.println("file:" + fileName);
                    files.add(new FileNode(path, fileName));
                } else {//是文件夹
                    System.out.println("dir:" + fileName);
                    folders.add(new FolderNode(path +"\\"+ fileName));
                }
            }
        }
        System.out.println("Folder:" + path + " loaded");
    }
}
