import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Compress {
    public static void main(String[] args) throws IOException {
        //根据路径拿到要压缩的文件
        String inputFile="C:\\Users\\AAA\\Desktop\\DSA仓库\\testcases\\testcase02NormalSingleFile\\1.txt";
        InputStream inputStream=new FileInputStream(inputFile);

        //遍历文件夹中所有内容
        int byteRead;
        ArrayList<Character> charList=new ArrayList<>();
        while((byteRead=inputStream.read()) != -1){
            charList.add((char) byteRead);
        }
        inputStream.close();
        if (charList.size()==0){
            //空文件
        }else{
            //统计次数、构建huffman tree、构建编码字典
            HashMap<Character, Integer> map = Tree.useTree(Tree.buildTree(Statistic.statistics(charList.toArray(new Character[0]))));

            String outPutFile="C:\\Users\\AAA\\Desktop\\DSA仓库\\testcases\\1test.txt";
            OutputStream outputStream = new FileOutputStream(outPutFile);
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(outputStream);

            objectOutputStream.writeObject(Tree.buildTree(Statistic.statistics(charList.toArray(new Character[0]))));

            //对每个内容，读取文件名、文件内容(char[])
            if (map.isEmpty()) return;//空文件
        }

    }
}
