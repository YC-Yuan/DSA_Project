import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Statistic {

    public static void main(String[] args) throws IOException {
        //根据路径拿到要压缩的文件
        //String inputFile = "C:\\Users\\AAA\\Desktop\\DSA仓库\\testcases\\testcase02NormalSingleFile\\1.txt";
        String inputFile = "C:\\Users\\AAA\\Desktop\\DSA仓库\\testcases\\testcase03XLargeSingleFile\\1.jpg";
        //String inputFile="C:\\Users\\AAA\\Desktop\\DSA仓库\\testcases\\testcase01EmptyFile\\empty.txt";
        BufferedInputStream in=new BufferedInputStream(new FileInputStream(inputFile));

        int inSize=in.available();
        System.out.println("inputFile.length() = " + inSize);

        //遍历文件夹中所有内容
        long startTime=System.currentTimeMillis();
        byte[] bytes=new byte[inSize];
        for(int i=0;i<inSize;i++){
            bytes[i]=(byte)in.read();
        }
        in.close();
        System.out.println("read running time:" +(System.currentTimeMillis()-startTime));
    }

    /**
     * 1.统计字符频率
     * 输入byte[]，用HashMap(key为字符,value为频率)存储字符出现频率，因为需要根据字符内容查询频率
     * 而构建huffman tree需要对频率排序，即以value排序。故而无法使用以key排序的TreeMap(最终都要取出到List中排序)
     * 因此以哈希表构建的HashMap是效率较优的选择
     * <p>
     * 2.根据频率排序
     * 由于HashMap本身无序，逐个放入PriorityQueue排序，排序方式耦合在Node的定义中
     * 树的构建则将根据PriorityQueue<Node>进行
     */
    public static PriorityQueue<Tree.Node> statistics(byte[] bytes) {
        if (bytes == null || bytes.length == 0) return null;
        long start = System.currentTimeMillis();
        Map<Byte, Integer> map = new HashMap<Byte, Integer>();
        for (byte b : bytes) {
            Byte aByte = b;
            if (map.containsKey(aByte)) {
                map.put(aByte, map.get(aByte) + 1);
            } else {
                map.put(aByte, 1);
            }
        }

        //将统计结果遍历放入优先队列排序
        Byte[] keys = map.keySet().toArray(new Byte[0]);
        PriorityQueue<Tree.Node> queue = new PriorityQueue<>();

        for (Byte b : keys) {
            Tree.Node node = new Tree.Node(b, map.get(b));
            queue.add(node);
        }
        System.out.println("Statistic size:" + bytes.length + ",Queue length:" + queue.size());
        System.out.println("Statistic running time:" + (System.currentTimeMillis() - start) + "mills");
        //此处用于打印queue检测排序效果，queue用for循环遍历顺序有问题，必须poll
//        while(!queue.isEmpty()){
//            Tree.Node node = queue.poll();
//            System.out.println("queue.poll() c:"+node.getC()+" f:"+node.getF());
//        }
        return queue;
    }
}
