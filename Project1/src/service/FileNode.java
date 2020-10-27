package service;

import service.impl.TreeImpl;
import util.ShowTime;
import util.Utils;

import java.io.*;
import java.util.HashMap;

public class FileNode implements Serializable {
    public String name;
    public int oriSize;
    public int comSize;
    public FolderNode parent;

    public FileNode(String name) {
        this.name = name;
    }

    public FileNode(String name, FolderNode parent) {
        this.name = name;
        this.parent = parent;
    }

    //向上追溯到第一个文件夹
    public String getPath() {
        FolderNode currentFolder;
        StringBuilder path = new StringBuilder(this.name);
        if (this.parent != null) {
            currentFolder = this.parent;
            path.insert(0, currentFolder.name + "\\");
            while (currentFolder.parent != null) {
                currentFolder = currentFolder.parent;
                path.insert(0, currentFolder.name + "\\");
            }
        }
        return path.toString();
    }

    //解压区
    public void creatFile(String dir) throws IOException {
        File file = new File(dir + "\\" + name);
        if (file.createNewFile()) System.out.println("Create file:" + dir + "\\" + name + " Success!");
        else System.out.println("Create file:" + dir + "\\" + name + " Fail! It may already existed.");
    }

    public void decompressFile(String dir, FileInputStream fis) throws IOException, ClassNotFoundException {
        ShowTime showTime = new ShowTime();

        ObjectInputStream ois = new ObjectInputStream(fis);
        BufferedInputStream bis = new BufferedInputStream(ois);

        HashMap<Byte, String> oldMap = (HashMap<Byte, String>) ois.readObject();
        FileNode fn = (FileNode) ois.readObject();

        HashMap<String, Byte> map = new HashMap<>();

        int shortestLength = 1000;
        String mapValue;
        for (Byte key : oldMap.keySet()) {
            mapValue = oldMap.get(key);
            shortestLength = Math.min(mapValue.length(), shortestLength);
            map.put(mapValue, key);
        }
        System.out.println(map);

        //开始还原文件
        FileOutputStream fos = new FileOutputStream(dir + "\\" + name);
        BufferedOutputStream bos = new BufferedOutputStream(fos);

        //存储文件内容
        StringBuilder strBuilder = new StringBuilder();//扫描区
        String str;//最终存入内容

        int count = 0;
        int start;
        boolean failed;

        Byte b;
        while (count < fn.oriSize) {
            //读一个Byte
            start = shortestLength - 1;
            strBuilder.append(Utils.getBinaryString((byte) bis.read()));
//            System.out.println("read new:" + strBuilder.toString());
            do {
                failed = true;
                for (int i = start; i < strBuilder.length() && failed && count < fn.oriSize; i++) {
                    str = strBuilder.substring(0, i);
                    b = map.get(str);
                    if (b != null) {//找到了写入目标
                        bos.write(b);//写入
                        count++;
                        strBuilder.delete(0, str.length());//改动缓冲区
                        start = shortestLength - 1;//从最短长度开始找
//                        System.out.println("Write:" + str + ",current:" + strBuilder.toString());
                        failed = false;//成功找到了！进行到下次循环
                        bos.flush();
                    }
                }
            } while (!failed);
        }
        bos.flush();
        bos.close();

        showTime.printTime("Function.Decompress " + fn.name + " cost:");
    }
}
