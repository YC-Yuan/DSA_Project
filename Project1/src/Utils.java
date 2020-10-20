import java.util.ArrayList;

public class Utils {
    public static byte[] getByteArray(ArrayList<Byte> byteArrayList){
        byte[] bytes=new byte[byteArrayList.size()];
        for (int i=0;i<byteArrayList.size();i++){
            bytes[i]=byteArrayList.get(i);
        }
        return bytes;
    }
}
