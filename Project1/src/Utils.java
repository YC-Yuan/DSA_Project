public class Utils {
    public static String rootPath="";

    public static String getDirName(String path){
        String[] names=path.split("\\\\");
        return names[names.length-1];
    }

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
