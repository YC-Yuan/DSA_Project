package service.impl;

import service.FileNode;
import service.FolderNode;
import util.Tree;
import service.YYCompress;
import util.ShowTime;
import util.Utils;

import javax.rmi.CORBA.Util;
import java.io.*;
import java.util.*;

public class YYCompressImpl implements YYCompress {
    String destinationPath;

    public YYCompressImpl(String destinationPath) {
        this.destinationPath = destinationPath;
    }

    @Override
    public void compress(String originalPath) throws IOException {
        //判断文件的种类，分发给两种实现
        File originalFile = new File(originalPath);
        if (originalFile.isFile()) fileCompress(originalPath);
        else folderCompress(originalPath);
    }

    @Override
    public void fileCompress(String filePath) throws IOException {
        //ShowTime showTime = new ShowTime();

        //读取部分
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath));
        int inSize = bis.available();
        //System.out.println("file size= " + inSize);

        //创建将要储存的FileNode
        FileNode fileNode = new FileNode(Utils.getFolderName(filePath));
        fileNode.oriSize = inSize;

        //遍历文件夹中所有内容
        byte[] bytes = new byte[inSize];
        bis.read(bytes);
        bis.close();

        //写入部分
        Tree tree = Tree.getTree(bytes);
        HashMap<Byte, String> map = Tree.useTree(tree);
        //System.out.println(map);

        //压缩时只能选择存在的目录，所以创建文件就够了
        OutputStream ops = new FileOutputStream(destinationPath, true);//写入时不清空，便于压缩文件夹调用
        BufferedOutputStream bos = new BufferedOutputStream(ops);
        ObjectOutputStream oop = new ObjectOutputStream(bos);

        //存储文件内容
        int writeBytesIndex = 0;
        byte[] writeBytes = new byte[inSize];
        StringBuilder strBuilder = new StringBuilder();
        String str;
        for (Byte b : bytes) {
            //使用缓冲区，攒够8位编码则写入
            str = map.get(b);
            strBuilder.append(str);
            while (strBuilder.length() >= 8) {
                str = strBuilder.substring(0, 8);
                strBuilder.delete(0, 8);
                writeBytes[writeBytesIndex] = Utils.getByte(str);
                writeBytesIndex++;
            }
        }
        //最后可能会剩下一些，补0处理
        if (strBuilder.length() != 0) {
            while (strBuilder.length() < 8) {
                strBuilder.append("0");
            }
            writeBytes[writeBytesIndex] = Utils.getByte(strBuilder.toString());
            writeBytesIndex++;
        }
        fileNode.comSize = writeBytesIndex;//这里记录压缩后文件长度
        byte[] byteFinal = new byte[fileNode.comSize];//创建最终要写入的数组
        System.arraycopy(writeBytes, 0, byteFinal, 0, fileNode.comSize);
        //写入数据结构，这里或许写入树更好**
        oop.writeObject(tree);
        //oop.writeObject(map);
        oop.writeObject(fileNode);
        oop.writeObject(byteFinal);

        oop.close();
        //showTime.printTime("Function.Compress " + filePath + "\\" + fileNode.name + " cost:");
    }

    @Override
    public void folderCompress(String folderPath) throws IOException {
        ShowTime showTime=new ShowTime();

        FolderNode folderNode = new FolderNode(folderPath);
        String rootPath = Utils.getFolderPath(folderPath);

        //压缩时只能选择存在的目录，所以创建文件就够了
        OutputStream ops = new FileOutputStream(destinationPath, true);
        BufferedOutputStream bos = new BufferedOutputStream(ops);
        ObjectOutputStream oop = new ObjectOutputStream(bos);

        oop.writeObject(folderNode);
        oop.close();
        //遍历folderNode,压缩所有文件
//        Queue<FileNode> fileQueue=new LinkedList<>();
        Queue<FolderNode> folderQueue = new LinkedList<>();
        folderQueue.add(folderNode);
        FolderNode currentFolder;
        //按照文件夹层数，将文件加载进fileQueue
        while (!folderQueue.isEmpty()) {
            currentFolder = folderQueue.poll();
            folderQueue.addAll(Arrays.asList(currentFolder.folders));
//            fileQueue.addAll(Arrays.asList(currentFolder.files));
            for (FileNode file : currentFolder.files) {
                fileCompress(rootPath.concat(file.getPath()));
            }
        }

        showTime.printTime("Folder compress cost:");
    }

    @Override
    public void depress(String filePath) {

    }

    @Override
    public void createFolder(String folderPath) {
        File folder = new File(folderPath);
        folder.mkdirs();
    }
}
