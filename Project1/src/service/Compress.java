package service;

import util.ShowTime;
import util.Tree;

import java.io.*;
import java.util.HashMap;

public class Compress {
    public String desPath;

    public Compress(String desPath) throws IOException {
        this.desPath = desPath;
        File file = new File(desPath);
        if (!file.exists()) {
            if (file.createNewFile()) System.out.println("Create file successful:"+file.getPath());
        }
    }

    public void writeFolders(FolderNode folderNode) throws IOException {
        OutputStream ops = new FileOutputStream(desPath, true);

        ObjectOutputStream oop = new ObjectOutputStream(ops);
        oop.writeObject(folderNode);
        oop.close();
        ops.close();
    }

    //输入要压缩进去的文件，往desPath;rootPath为文件所在目录
    //压缩顺序：map、node、byte数组
    public void compress(FileNode fileNode, String rootPath) throws IOException {
        ShowTime showTime = new ShowTime();

        //读取部分
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(rootPath+"\\"+fileNode.name));
        int inSize = bis.available();
        System.out.println("file size= " + inSize);
        fileNode.oriSize=inSize;
        //遍历文件夹中所有内容
        byte[] bytes = new byte[inSize];
        for (int i = 0; i < inSize; i++) {
            bytes[i] = (byte) bis.read();
        }
        bis.close();

        //写入部分
        HashMap<Byte, String> map = Tree.getMap(bytes);
        System.out.println(map);

        //压缩时只能选择存在的目录，所以创建文件就够了
        OutputStream ops = new FileOutputStream(desPath, true);

        ObjectOutputStream oop = new ObjectOutputStream(ops);
        BufferedOutputStream bos = new BufferedOutputStream(oop);

        oop.writeObject(map);
        oop.writeObject(fileNode);
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
        bos.close();
        showTime.printTime("Function.Compress " + rootPath+"\\"+fileNode.name + " cost:");
    }
}
