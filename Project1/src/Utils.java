public class Utils {
    public static String rootPath="";

    public static String getDirName(String path){
        String[] names=path.split("\\\\");
        return names[names.length-1];
    }
}
