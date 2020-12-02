package service;

import java.lang.reflect.Array;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class StationInfo {
    public static int mapSize = 325;
    public static HashMap<String,Integer> map = new HashMap<>();
    public static Station[] stations = new Station[mapSize];

    public static int [][] timeTable=new int[mapSize][mapSize];
    public static int [][] changeTable=new int[mapSize][mapSize];
    public static StringBuilder [][] pathTable=new StringBuilder[mapSize][mapSize];

    public static String infoPath;
    public static int infoDistance;
    public static int infoChange;

}
