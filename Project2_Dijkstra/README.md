# DSA荣誉课程PJ2文档

19302010020 袁逸聪

## 项目与文档结构

项目需求为地铁路线查询，输入站点序列，输出乘坐路线、用时、换乘次数

应选择用时尽可能少的路线，用时相同时选择换成次数尽可能少的路线，都相同则任意输出一个

给出的数据为各线路时刻表，可以根据站点时刻的差值建立关系图，用图最短路径算法找到答案

故流程分为读取与建表、最短路径算法、输出。文档将讨论每个环节尝试的做法，最后论述性能改进

## 读取与建表

原始文件Excel给出15条线路，其中4号线为环线、3号线4号线有并线、10号线11号线有分差

为方便读表统一化，将10号线和11号线的并线部分拆开成3条汇聚在分岔点的线路，仍记为10或11号线

### 存储结构

对每个站点，需要存储名称、编号、线路、可通往的站点与时间

因而设计Station类如下

```Java
public class Station implements Comparable<Station> {
    public String name;
    public int index;//站点编号
    public HashSet<Integer> line = new HashSet<>();
    public Vector<Station> neighborStation = new Vector<>();//按顺序存储相邻站点
    public Vector<Integer> neighborTime = new Vector<>();//存储相邻站点时间
}
```

共需存储324个站点，故创建Info类用于存储静态变量

```Java
public class Info {
    public static int mapSize = 324;
    public static HashMap<String,Integer> map = new HashMap<>();
    public static Station[] stations = new Station[mapSize];
}
```

map用于将站点名与站点数组index对应

### 建图过程

对每条线路，按行读入站点与时间

先查询是否已创建站点，若没有，则根据循环index新建站点赋值给站点数组

若已创建站点，则获取站点index后面使用

除了每条线路的第一站外，每读取一站，都根据时间计算差值，与前一站构建联系(互相加入对方的邻居站，并设定交通时间)

### 数据读取方式选择

excel无法直接读入，找到两种办法，java提供有poi库与jxl库用于读取数据

poi库功能强大，版本较新；而jxl仅更新到2006年，且只能应用于xls文件，故最初选择了poi

但是，poi的读取时间消耗更大(仅解析文件，在我的笔记本上需要跑1.5~2秒，而后面的读取建表过程仅100ms左右)，尝试jxl发现只需大约700ms解析

考虑到数据量如此之小，直接读取excel可能不是明智选择

因而又尝试了转化为txt读取，19个excel表格转化为19个txt文件，即便未做任何数据预处理，也将读表时间压缩到数十毫秒级别

最终采用了txt读取

## 最短路径算法

最开始，考虑到中间站点的查询，打算依靠floyd算法一劳永逸。但floyd算法的线路常常发生重大改变，很难对每个路径维护“当前所在线路”，判断如3、4并线这样的换成问题往往需要遍历（虽然大多数时候只需要考察一两个站点）路径，比较麻烦。

另外，floyd算法完成了对所有324*324条线路的计算，在小数据集的测试中造成大量浪费，并不实惠。（即便数据集扩大，也不需要额外计算而只是查表，故更长的测试输入将带来优势）

floyd算法在笔记本上的运行时间大约为1000ms以上，改进空间较小

故又尝试了dijkstra算法，经过了许多层改进，加之比起floyd对运算结果的运用率更高，达到了更好的效果（在轻薄本上，performance读取、运算、输出时间在300ms以内）

本节先解释路径算法中的基本思路

### 换乘数处理

由于比较规则为先时间、后换乘，可以认为时间具有更大的权重，无论换乘多少次都无法超越1分钟的时间浪费

因而将换成记录在距离的小数部分，每次发生换乘+0.01，即可完成比较

考虑到浮点数的运算误差，直接使用浮点数比较并不方便。故将距离*100，以最后两位为换乘数，用int运算避免误差问题

### 换乘的判断与处理

换乘的判断来自于自然思路，沿着路径一站一站向前取线路交集，取到空集或遍历结束都说明前一次的线路集合为可能的线路。

在dijkstra算法中，不需要每次实时判断当前线路，因为路径是一站一站扩张的，能够维护一个集合表征当前路径，随着路径扩张时刻改变。（集合元素可以＞1，所以实时维护并不意味着3、4并线时需要直接选出所乘路径）

如果发生换乘，需要在距离上加1(0.01分钟，前面说过对距离先乘了100避免浮点数误差)

如果不发生换乘，则需要从路径Vector中删除中间站，同一条线路上的中间站并不需要输出给用户看

```Java
neighbor.path = new Vector<>(current.path);//先复制之前的路
HashSet<Integer> tmpLine = new HashSet<>(current.currentLine);
tmpLine.retainAll(neighbor.line);//没换乘，但是收束了
neighbor.currentLine = tmpLine;//记录收束结果，如果换成会记为空，但没事，之后覆盖
if (tmpLine.isEmpty()) {//换乘了的情况，新节点肯定不是中间节点
                        neighbor.distance += 1;
    neighbor.currentLine = new HashSet<>(neighbor.line);
    neighbor.currentLine.retainAll(current.line);
} else {//不换乘，删除中间节点
    if (neighbor.path.size() > 1) neighbor.path.remove(neighbor.path.size() - 1);
    //不论如何，不换乘的新节点一定是中间节点了
}
//不论换不换乘，总要加入新的站
neighbor.path.add(neighbor);
```

## 输出

在Info中存储计算结果，即距离、换乘与路径

为便于操作，路径以Vector<Station>存储，而输出时需要转化为字符串

两站点中输出各种线路，有getLineName()确定

```Java
public static Integer getLineName(Station start,Station end) {
        HashSet<Integer> lineA = new HashSet<>(start.line);
        for (Integer line : end.line) {
            if (lineA.contains(line)) return line;
        }
        return 0;
    }
```

即任意输出两个站点间可行的一条线路

此时不需要考虑3、4号线的复杂抉择，因为这个过程已经在算法部分解决。Vector中存储的站点，是确定发生了换乘，必然参与输出的站点

## 性能改进

### 二级缓存

写完dijkstra版本后我发现将Vector<Station>转化为String的函数占用了大量时间，因为字符串是耗时操作

原本的缓存架构中，只考虑了最短路径算法的缓存，即算完一个点即存储它到所有点的路径，下次查询时直接取用

但是，远远不能保证从此点出发的所有路径都被考虑，缓存时只要存Vector即可(我们无法预知计算结果会不会被用到，因而存储是必须的。但相比于字符串拼接，存储对象的引用消耗小得多)，不必将所有String都拼接完成

这个转变将字符串拼接函数的调用次数从数万级降到了13000+次，但仍然可以进一步改进

因为同一个路径也可能被查询多次，如果缓存只有Vector，就可能发生同一个Vector被多次转化为String。由此形成二级缓存，将确实被查询过，已经拼接了的字符串也储存，将拼接函数调用次数降低到了12340次

### 字符串分割

对于多个站点的输入，最初采用split()函数分割。但效率较低

自行测试发现，Java所提供了专门进行字符串分割的StringTokenizer类，耗时大约是split()的一般。博客指出，自己用indexOf实现的分割比tokenizer效率更高，但我实测后发现二者不相上下，indexOf的方式甚至有时更慢，最终还是采取了tokenizer

### 换乘判断

在前文换成判断的代码块中，所展示的部分其实也经历了优化。

为了可能减少了对Set的复制与交集操作，弃用单独封装的isSameLine()先判断后操作，而是让操作和判断同步进行，利用操作结果本身判断是否采用。节省了一半的复制和取交集操作