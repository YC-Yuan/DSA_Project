package service.impl;

import service.FileNode;
import service.FolderNode;
import service.YYCompress;
import util.ShowTime;
import util.Utils;

import java.io.*;
import java.util.*;

public class YYCompressImpl implements YYCompress {
    String destinationPath;
    ObjectOutputStream oos;
    ObjectInputStream ois;

    public YYCompressImpl(String destinationPath) {
        this.destinationPath = destinationPath;
    }

    /**
     * 每个文件内容：Tree,FileNode,byte[]
     */
    @Override
    public void compress(String originalPath) throws IOException {
        //判断文件的种类，分发给两种实现
        File originalFile = new File(originalPath);
        if (originalFile.isFile()) {
            //System.out.println("Compress file");

            //压缩文件，根据原文件名修改压缩文件名
            destinationPath = destinationPath.concat("\\").concat(Utils.getNonFixName(originalFile.getName())).concat(".YYCFile");

            oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(destinationPath)));
            fileCompress(originalPath);
        } else {
            //压缩文件夹，根据文件夹名修改压缩文件名
            destinationPath = destinationPath.concat("\\").concat(originalFile.getName()).concat(".YYCPack");
            oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(destinationPath)));
            folderCompress(originalPath);
        }
        oos.close();
    }

    @Override
    public void fileCompress(String filePath) throws IOException {
        ShowTime showTime = new ShowTime();

        //创建将要储存的FileNode
        FileNode fileNode = new FileNode(Utils.getFolderName(filePath));

        //读取部分
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath));
        int inSize = bis.available();

        //遍历文件夹中所有内容
        byte[] bytes = new byte[inSize];
        bis.read(bytes);
        bis.close();

        //写入部分
        TreeImpl tree = new TreeImpl(bytes);
        HashMap<Byte, String> map = tree.useTree();

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
        byte[] byteFinal = new byte[writeBytesIndex];//创建最终要写入的数组
        System.arraycopy(writeBytes, 0, byteFinal, 0, writeBytesIndex);
        //写入数据结构，这里或许写入树更好**

        fileNode.comSize = writeBytesIndex;//这里记录压缩后文件长度
        fileNode.oriSize = inSize;

        oos.writeObject(tree);
        oos.writeObject(fileNode);
        oos.writeObject(byteFinal);
        oos.reset();
        showTime.printTime("Compress "+fileNode.name+",speed:"+fileNode.oriSize/1024.0/1024.0/(showTime.getTime()/1000.0)+ "MB/S,size:"+fileNode.oriSize+"cost:");
    }

    /**
     * 压缩内容：FolderNode,各个文件
     * 每个文件内容：Tree,FileNode,byte[]
     */
    @Override
    public void folderCompress(String folderPath) throws IOException {
        ShowTime showTime = new ShowTime();

        FolderNode folderNode = new FolderNode(folderPath);
        String rootPath = Utils.getFolderPath(folderPath);

        oos.writeObject(folderNode);
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
                //压缩目标文件，给出绝对地址+文件名
                fileCompress(rootPath.concat(file.getPath()));
            }
        }

        showTime.printTime("Folder compress cost:");
    }

    @Override
    public void depress(String originalPath) throws IOException, ClassNotFoundException {

        File file = new File(originalPath);
        ois= new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));

        if (Utils.getFilePostFix(file.getName()).equals(".YYCFile")) {//文件解压
            //System.out.println("Depress a file");

            //单文件解压需要读取后才知道文件名称，统一传入文件解压得目标地址，在函数内部获取创建文件的总目录
            fileDepress(destinationPath+"\\");
        } else if (Utils.getFilePostFix(file.getName()).equals(".YYCPack")) {//文件夹解压
            //System.out.println("Depress a folder");
            folderDepress();
        } else {//非法文件名
            System.out.println("Wrong postfix, cannot depress!");
        }
        ois.close();
    }

    @Override
    public void fileDepress(String destinationPath) throws IOException, ClassNotFoundException {
        ShowTime showTime=new ShowTime();

        TreeImpl tree =(TreeImpl) ois.readObject();
        FileNode fileNode = (FileNode) ois.readObject();
        byte[] bytes = (byte[]) ois.readObject();

        destinationPath = destinationPath.concat(fileNode.name);
        FileOutputStream fos = new FileOutputStream(destinationPath);
        BufferedOutputStream bos = new BufferedOutputStream(fos);

        int count = 0;
        byte[] wroteBytes = new byte[fileNode.oriSize];
        Byte bufferedByte;
        StringBuilder stringBuilder = new StringBuilder();
        for (byte aByte : bytes) {
            stringBuilder.append(Utils.getBinaryString(aByte));//读入后应有8位
            for (int j = 0; j < 8 && count < fileNode.oriSize; j++) {
                bufferedByte = tree.scan(stringBuilder.charAt(j));
                if (bufferedByte != null) {
                    wroteBytes[count] = bufferedByte;
                    count++;
                }
            }
            stringBuilder.delete(0, 8);
        }
        bos.write(wroteBytes);
        bos.close();

        showTime.printTime("Depress "+fileNode.name+",speed:"+fileNode.oriSize/1024.0/1024.0/(showTime.getTime()/1000.0)+ "MB/S,size:"+fileNode.oriSize+"cost:");
    }

    @Override
    public void folderDepress() throws IOException, ClassNotFoundException {
        //此时destination为目标路径
        FolderNode folderNode = (FolderNode) ois.readObject();

        //遍历folderNode,创建目录并解压文件
        Queue<FolderNode> folderQueue = new LinkedList<>();
        folderQueue.add(folderNode);
        FolderNode currentFolder;
        //按照文件夹层数，将文件加载进fileQueue
        while (!folderQueue.isEmpty()) {
            currentFolder = folderQueue.poll();
            createFolder(destinationPath + "\\" + currentFolder.getPath());
            folderQueue.addAll(Arrays.asList(currentFolder.folders));
            for (FileNode file : currentFolder.files) {
                //这里统一只传入文件要解压的地址，在函数中获取文件名字
                fileDepress(Utils.getFolderPath(destinationPath + "\\" + file.getPath()));
            }
        }
    }

    @Override
    public void createFolder(String folderPath) {
        File folder = new File(folderPath);
        folder.mkdirs();
    }
}
