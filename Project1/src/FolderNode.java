import com.sun.xml.internal.ws.api.model.SEIModel;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;

public class FolderNode implements Serializable {
    public static void main(String[] args) throws IOException {
        ShowTime showTime = new ShowTime();

        FolderNode folderNode = new FolderNode("C:\\Users\\15344\\Desktop\\辩论");
        folderNode.print();
        showTime.printTime("Read folders cost:");
        showTime.updateTime();

        folderNode.compress("C:\\Users\\15344\\Desktop\\testfile.yyc");
        showTime.printTime("Compress all cost:");
    }

    public ArrayList<FolderNode> folders = new ArrayList<>();
    public ArrayList<FileNode> files = new ArrayList<>();
    public String path;

    //根据地址读入
    public FolderNode(String path) {
        this.path = path;
        File file = new File(path);
        File[] fs = file.listFiles();//遍历path下的文件和目录，放在File数组中
        if (fs != null) {
            for (File f : fs) {
                String fileName = f.getName();//获取文件或目录名
                if (!f.isDirectory()) {//是文件
                    files.add(new FileNode(path + "\\" + fileName));
                }
                else {//是文件夹
                    folders.add(new FolderNode(path + "\\" + fileName));
                }
            }
        }
    }

    //按照储存顺序print
    public void print() {
        System.out.println("Folder:" + path);
        for (FolderNode folder : folders
        ) {
            folder.print();
        }
        for (FileNode file : files
        ) {
            System.out.println("File:" + file.path);
        }
    }

    //压缩区
    //按照储存顺序(先文件，后文件夹)写入文件
    public void compress(String desPath) throws IOException {
        new WriteUtil(desPath).writeFolders(this);
        writeFile(desPath);
    }

    private void writeFile(String desPath) throws IOException {
        WriteUtil writeUtil = new WriteUtil(desPath);
        for (FileNode file : files) {
            System.out.println("Compressing:" + file.path);
            writeUtil.compress(file.path);
        }
        for (FolderNode fd : folders) {
            System.out.println("writeFold evoked:" + fd.path);
            fd.writeFile(desPath);
        }
    }
}
