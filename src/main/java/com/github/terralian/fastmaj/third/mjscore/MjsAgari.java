package com.github.terralian.fastmaj.third.mjscore;

import java.util.TreeMap;

/**
 * mjscore分割计算类
 * <p/>
 * 该包中包含mjscore的和牌牌分割及部分役的计算，采用
 * 
 * @see <a href="http://hp.vector.co.jp/authors/VA046927/mjscore/">mjscore</a>
 * @see <a href="http://hp.vector.co.jp/authors/VA046927/mjscore/mjalgorism.html">原理</a>
 */
class MjsAgari {

    // 初始化数据树
    private static final TreeMap<Integer, int[]> tbl;
    static {
        tbl = new TreeMap<>();
        MjsAgariTbl.init(tbl);
    }

    // 计算索引
    private static int calc_key(int[] n, int[] pos) {
        int p = -1;
        int x = 0;
        int pos_p = 0;
        boolean b = false;
        int i;
        for (i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                if (n[i * 9 + j] == 0) {
                    if (b) {
                        b = false;
                        x |= 1 << p;
                        p++;
                    }
                } else {
                    p++;
                    b = true;
                    pos[pos_p++] = i * 9 + j;
                    switch (n[i * 9 + j]) {
                        case 2:
                            x |= 3 << p;
                            p += 2;
                            break;
                        case 3:
                            x |= 15 << p;
                            p += 4;
                            break;
                        case 4:
                            x |= 63 << p;
                            p += 6;
                            break;
                    }
                }
            }
            if (b) {
                b = false;
                x |= 1 << p;
                p++;
            }
        }
        for (i = 27; i <= 33; i++) {
            if (n[i] > 0) {
                p++;
                pos[pos_p++] = i;
                switch (n[i]) {
                    case 2:
                        x |= 3 << p;
                        p += 2;
                        break;
                    case 3:
                        x |= 15 << p;
                        p += 4;
                        break;
                    case 4:
                        x |= 63 << p;
                        p += 6;
                        break;
                }
                x |= 1 << p;
                p++;
            }
        }
        return x;
    }

    /**
     * 和牌分割
     * 
     * @param value34 34编码法表示的值
     * @param pos 14长度的空数组，用于值传递，后续获取实际结果时外部需要用到，也可以通过{@link #allocatePos()}创建
     */
    public static int[] agari(int[] value34, int[] pos) {
        int key = calc_key(value34, pos);
        return tbl.get(key);
    }

    /**
     * 获取POS参数的工厂方法
     */
    public static int[] allocatePos() {
        return new int[14];
    }

    private MjsAgari() {}
}
