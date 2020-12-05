package service;

public class ShowTime {
    private long start;
    public static long readCost,floydCost;
    public static ShowTime global;

    public ShowTime() {
        start = System.currentTimeMillis();
    }

    public void updateTime() {
        start = System.currentTimeMillis();
    }

    public long getTime() {
        return (System.currentTimeMillis() - start) ;
    }

    public void printTime(String prefix) {
        System.out.println(prefix + (System.currentTimeMillis() - start) + "mills");
    }

    public String getPrintTime(String prefix){
        return prefix + (System.currentTimeMillis() - start) + "mills";
    }
}

