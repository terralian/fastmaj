package com.github.terralian.fastmaj.yama;

import java.security.MessageDigest;
import java.util.Base64;

import com.github.terralian.fastmaj.third.mt19937ar.MersenneTwister;

/**
 * 天凤牌谱的根据种子生成牌山算法
 * <p/>
 * <b>该算法从C -> C# -> JAVA移植</b> 移植前的源码参考下面<code>@seeAlso</code>的链接
 * <p/>
 * 该方法生成的牌山数组和实际的天凤牌谱对应关系为：
 * <ul>
 * <li>数组顺序为实际牌山摸牌顺序的倒序
 * <li>即数组的[0]枚是王牌区最近一栋的下面那枚牌。按实际看，王牌区的第一枚是数组的[1]
 * <li>牌山的宝牌指示牌为[6, 8, 10, 12, 14]枚
 * </ul>
 * 
 * @author terra.lian 
 * @see <a href=
 *      "http://blog.tenhou.net/article/30503297.html">天凤官方的牌山生成算法公开（c）</a>
 * @see <a href=
 *      "https://github.com/nwalker/tenhouviewer/blob/master/Tenhou/WallGenerator.cs">github上的牌山生成算法(c#)</a>
 */
public class TenhouYamaWorker implements IYamaWorker {

    /**
     * 梅森旋转法随机数生成器
     * <p/>
     * 需要根据种子进行初始化，非线程安全
     */
    private MersenneTwister r;

    /**
     * {@link MessageDigest}用于sha512摘要的类缓存
     */
    private MessageDigest sha512Cache;

    /**
     * 种子
     */
    private String seed;

    /**
     * 构建一个空的牌山生成器
     * <p/>
     * 由于此时种子为空，生成器是不可使用的状态
     */
    public TenhouYamaWorker() {
        seed = null;
    }

    /**
     * 根据种子构建一个牌山生成器
     * <p/>
     * 该构造器会同时根据种子进行初始化{@link #initialize(String)}
     * 
     * @param seed 种子
     */
    public TenhouYamaWorker(String seed) {
        this.seed = seed;
        initialize();
    }

    /**
     * 获取牌山
     */
    @Override
    public int[] getNextYama(int nextSize) {
        if (r == null) {
            throw new IllegalMonitorStateException("牌山生成器未进行初始化（无种子）");
        }
        int[] wall = new int[136];
        generate(nextSize, wall);
        return wall;
    }

    /**
     * 根据种子初始化生成器
     * 
     * @param seed 种子
     */
    public void initialize() {
        if (this.seed == null || this.seed.isEmpty()) {
            throw new IllegalArgumentException("种子解析为空");
        }

        int delimiterPos = seed.indexOf(",");
        String seedText = seed.substring(delimiterPos + 1);

        long[] seedValues = new long[624];
        // 当前版本的种子值转换
        if (seed.indexOf("mt19937ar-sha512-n288-base64") == 0) {
            byte[] seedBytes = Base64.getDecoder().decode(seedText);
            // 转换为无符号32位INT数组，由于java没有无符号，使用Long来表示
            for (int i = 0; i < seedValues.length; i++) {
                seedValues[i] = (Byte.toUnsignedLong(seedBytes[i * 4 + 0]) << 0) //
                        | (Byte.toUnsignedLong(seedBytes[i * 4 + 1]) << 8) //
                        | (Byte.toUnsignedLong(seedBytes[i * 4 + 2]) << 16) //
                        | (Byte.toUnsignedLong(seedBytes[i * 4 + 3]) << 24);
            }
        }

        // 旧风格的种子值
        else {
            seedValues = decompositeHexList(seedText);
        }

        // 根据种子值初始化梅森旋转随机数生成器
        if (r == null) {
            r = new MersenneTwister(seedValues);
        } else {
            r.init_by_array(seedValues, seedValues.length);
        }
    }

    /**
     * 兼容较老的天凤牌谱
     * 
     * @param text 实际种子的值
     */
    private long[] decompositeHexList(String text) {
        if (text == null) {
            throw new IllegalArgumentException("yama seed value is empty");
        }

        String[] textArray = text.split(",");
        long[] result = new long[textArray.length];

        for (int i = 0; i < textArray.length; i++) {
            int index = textArray[i].indexOf('.');
            if (index >= 0)
                textArray[i] = textArray[i].substring(0, index);

            result[i] = Long.valueOf(textArray[i]);
        }

        return result;
    }

    /**
     * 生成牌山算法
     * 
     * @param nextSize 循环次数
     */
    private void generate(int nextSize, int[] wall) {
        // 144
        long[] rnd = new long[64 / 4 * 9];

        // SHA512 hash
        for (int j = 0; j < nextSize; j++) {
            long[] src = new long[64 / 4 * 9 * 2];

            for (int i = 0; i < src.length; i++) {
                src[i] = r.genrand_int32();
            }

            byte[] srcbyte = new byte[src.length * 4];
            for (int i = 0; i < src.length; i++) {
                srcbyte[i * 4 + 0] = (byte) ((src[i] >> 0) & 0xFF);
                srcbyte[i * 4 + 1] = (byte) ((src[i] >> 8) & 0xFF);
                srcbyte[i * 4 + 2] = (byte) ((src[i] >> 16) & 0xFF);
                srcbyte[i * 4 + 3] = (byte) ((src[i] >> 24) & 0xFF);
            }

            for (int i = 0; i < 9; ++i) {
                byte[] datatohash = new byte[128];
                for (int k = 0; k < 128; k++)
                    datatohash[k] = srcbyte[k + i * 128];

                byte[] hash = getMessageDigest().digest(datatohash);

                for (int k = 0; k < 16; k++) {
                    rnd[i * 16 + k] = (Byte.toUnsignedLong(hash[k * 4 + 0]) << 0) //
                            | (Byte.toUnsignedLong(hash[k * 4 + 1]) << 8) //
                            | (Byte.toUnsignedLong(hash[k * 4 + 2]) << 16)//
                            | (Byte.toUnsignedLong(hash[k * 4 + 3]) << 24);
                }
            }
        }

        // 初始化牌山
        for (int i = 0; i < wall.length; i++)
            wall[i] = i;

        // 按随机数对牌山进行洗牌
        for (int i = 0; i < wall.length - 1; i++) {
            int Src = i;
            int Dst = (int) (i + ((rnd[i] % (136 - i)) & 0xFFFF));

            // 交换两张牌
            int Swp = wall[Src];
            wall[Src] = wall[Dst];
            wall[Dst] = Swp;
        }

        // 骰子，不使用
        // dice[0] = 1 + (int) (rnd[135] % 6) & 0xFFFF;
        // dice[1] = 1 + (int) (rnd[136] % 6) & 0xFFFF;
    }


    /**
     * 获取摘要算法类
     */
    private MessageDigest getMessageDigest() {
        if (this.sha512Cache == null) {
            try {
                this.sha512Cache = MessageDigest.getInstance("SHA-512");
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }

        return this.sha512Cache;
    }

    @Override
    public String getSeed() {
        return seed;
    }

    /**
     * 设置种子
     * 
     * @param seed 种子
     */
    public void setSeed(String seed) {
        this.seed = seed;
    }
}
