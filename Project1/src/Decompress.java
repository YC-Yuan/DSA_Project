import java.io.*;

public class Decompress {
    public String rootPath;
    public String desPath;

    public Decompress(String rootPath,String desPath){
        this.rootPath=rootPath;
        this.desPath=desPath;
    }

    public void decompress() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(rootPath);
//        BufferedInputStream bis = new BufferedInputStream(fis);
        ObjectInputStream ois = new ObjectInputStream(fis);

        //这是总文件夹树
        FolderNode fn = (FolderNode) ois.readObject();
        fn.creatPath(desPath+"\\"+fn.name);
        folderDecompress(fn,desPath+"\\"+fn.name,fis);
        fis.close();
    }

    //以先文件后文件夹的顺序遍历复原
    private void folderDecompress(FolderNode fn,String dir,FileInputStream fis) throws IOException, ClassNotFoundException {
        for (FileNode fileNode:fn.files) {
            //传入地址，读文件并创造
            System.out.println("creating file:"+fileNode.name);
            fileNode.creatFile(dir);
            //将对应的内容写入文件（复原）
            fileNode.decompressFile(dir,fis);
        }
        for (FolderNode folderNode:fn.folders){
            System.out.println("creating folder:"+folderNode.name);
            //创建目录
            folderNode.creatPath(dir+"\\"+folderNode.name);
            //修改地址，重复调用
            folderDecompress(folderNode,dir+"\\"+folderNode.name,fis);
        }
    }
}
