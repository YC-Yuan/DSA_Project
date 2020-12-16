import java.util.ArrayList;

public class Test {
    public static ArrayList<java.lang.String> keys=new ArrayList<>();

    public static void main(String[]args){
        final int length=10000;
        StringBuilder sb=new StringBuilder();
        sb.append(length).append("\n");
        for (int i=0;i<length;i++){
            sb.append(getOrder(getRandomInt(10)));
        }
        System.out.println(sb.toString());
    }

    public static int getRandomInt(int seed){
        return (int)(Math.random()*seed);
    }

    public static java.lang.String getOrder(int seed){
        StringBuilder sb=new StringBuilder();
        if (keys.size()==0) seed=0;
        switch (seed){
            case 0://PUT
                java.lang.String randomKey = getRandomKey(getRandomInt(50));
                keys.add(randomKey);
                sb.append("PUT ").append(randomKey).append(" ").append(getRandomInt(1000));
                break;
            case 1://ADD
                sb.append("ADD ").append(keys.get(getRandomInt(keys.size()))).append(" ").append(getRandomInt(1000));
                break;
            case 2://QUERY
                sb.append("QUERY ").append(keys.get(getRandomInt(keys.size())));
                break;
            case 3://DEL
                sb.append("DEL ").append(keys.get(getRandomInt(keys.size())));
                break;
            case 4://ADDBEGINWITH
                sb.append("ADDBEGINWITH ").append(getRandomKey(getRandomInt(3))).append(" ").append(getRandomInt(1000));
                break;
            case 5://QUERYBEGINWITH
                sb.append("QUERYBEGINWITH ").append(getRandomKey(getRandomInt(3)));
                break;
            case 6://DELBEGINWITH
                sb.append("DELBEGINWITH ").append(getRandomKey(getRandomInt(3)));
                break;
            case 7://ADDCONTAIN
                sb.append("ADDCONTAIN ").append(getRandomKey(getRandomInt(2))).append(' ').append(getRandomInt(500));
                break;
            case 8://QUERYCONTAIN
                sb.append("QUERYCONTAIN ").append(getRandomKey(getRandomInt(2)));
                break;
            case 9://DELCONTAIN
                sb.append("DELCONTAIN ").append(getRandomKey(getRandomInt(2)));
                break;
        }
        sb.append("\n");
        return sb.toString();
    }

    public static java.lang.String getRandomKey(int length){
        length++;
        StringBuilder stringBuilder=new StringBuilder();
        for (int i=0;i<length;i++){
            stringBuilder.append((char)('a'+getRandomInt(26)));
        }
        return stringBuilder.toString();
    }
}