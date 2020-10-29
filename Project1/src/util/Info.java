package util;

public class Info {
    //单位Byte
    public static long originalSize = 0;
    //单位Mill
    public static long timeConsuming = 0;
    //单位Byte
    public static long compressedSize = 0;

    public static void init() {
        originalSize = 0;
        timeConsuming = 0;
        compressedSize = 0;
    }

    //获取原文件大小
    public static double getOriginalSize() {
        return originalSize / 1024.0 / 1024.0;
    }

    //获取压缩后文件大小
    public static double getCompressedSize() {
        return compressedSize / 1024.0 / 1024.0;
    }

    //获取时间消耗
    public static double getTime() {
        return timeConsuming / 1000.0;
    }

    //获取压缩比
    public static double getRatio() {
        return (double) compressedSize / originalSize * 100;
    }

    //获取平均速度
    public static double getSpeed() {
        return (originalSize / 1024.0 / 1024.0) / (timeConsuming / 1000.0);
    }
}
