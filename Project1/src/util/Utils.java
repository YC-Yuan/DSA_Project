package util;

public class Utils {
    //拿到路径中最后一段名字
    public static String getFolderName(String path){
        String[] names=path.split("\\\\");
        return names[names.length-1];
    }

    public static String getFolderPath(String path){
        String[] names=path.split("\\\\");
        StringBuilder dir=new StringBuilder("");
        for (int i=0;i<names.length-1;i++){
            dir.append(names[i]).append("\\");
        }
        return dir.toString();
    }

    public static byte getByte(String str){
        return (byte) Integer.parseInt(str, 2);
    }

    //将Byte转化成01组成的String
    public static String getBinaryString(byte b){
        StringBuilder str = new StringBuilder(Integer.toBinaryString(b));
        if (str.length()>8){
            return str.substring(24,32);
        }
        else{
            while(str.length()<8){
                str.insert(0,"0");
            }
            return str.toString();
        }
    }
}
