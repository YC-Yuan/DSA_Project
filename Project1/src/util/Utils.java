package util;

import org.junit.Test;

public class Utils {
    //拿到路径中最后一段名字(不带\)
    public static String getFolderName(String path) {
        return path.substring(path.lastIndexOf("\\")+1);
    }

    //拿到文件夹前面部分的路径(带\)
    public static String getFolderPath(String path) {
        return path.substring(0,path.lastIndexOf("\\")+1);
    }

    //拿到文件名后缀形如".txt"
    public static String getFilePostFix(String path) {
        return path.substring(path.lastIndexOf("."));
    }

    public static String getNonFixName(String name){
        return name.substring(0,name.lastIndexOf("."));
    }

    //给出字符串，返回对应的8位bits
    public static byte getByte(String str) {
        return (byte) Integer.parseInt(str, 2);
    }

    //将Byte转化成01组成的String
    public static String getBinaryString(byte b) {
        StringBuilder str = new StringBuilder(Integer.toBinaryString(b));
        if (str.length() > 8) {
            return str.substring(24, 32);
        } else {
            while (str.length() < 8) {
                str.insert(0, "0");
            }
            return str.toString();
        }
    }
}
