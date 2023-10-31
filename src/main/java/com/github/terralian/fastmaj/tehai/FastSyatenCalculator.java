package com.github.terralian.fastmaj.tehai;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.util.AssertHelper;

/**
 * 快速向听计算器
 * <p/>
 * 修改了回溯法设计的{@link SyatenCalculator#calcNormal(java.util.Collection)}方法，改为基于索引查和了型的数（向听数+1） 从而加快速度。
 * 该实现对于大量计算向听数场景十分有效，其性能是十分优异的，使用序列化存储与读取索引文件，使其的初始化性能也达到可以接受的地步（约500ms)。
 * <p/>
 * 序列化文件压缩后有900KB，解压后会达到96MB大小，所以占用一定内存。
 * <p/>
 * 该实现来自于<b>tomohxx</b>，为c++实现，根据<b>comssa56</b>改写的JS版本改写为java版本，其原理及详情可以参考注释中的链接。
 *
 * @author terra.lian
 * @see <a href=
 * "https://qiita.com/tomohxx/items/75b5f771285e1334c0a5">tomohxx記事</a>
 * @see <a href="https://www.cnblogs.com/syui-terra/p/16673262.html">原理的中文机翻</a>
 * @see <a href="https://github.com/tomohxx/shanten-number">github:
 * tomohxx/shanten-number</a>
 * @see <a href=
 * "https://github.com/comssa56/snc_js/blob/main/source/shanten.js">
 * github: comssa56/snc_js</a>
 */
public class FastSyatenCalculator extends SyatenCalculator {

    private static int[][] mp1 = null;
    private static int[][] mp2 = null;

    static {
        try {
            loadSyatenIndexFile();
        } catch (Exception e) {
            throw new IllegalStateException("初始化FastSyatenCalculator异常", e);
        }
    }

    @Override
    public int calcMin(Collection<IHai> hands) {
        int syaten = 13;

        // 副露，或者暗杠时，即不可能再是七对子或国士的牌型
        if (hands.size() >= 13) {
            syaten = Math.min(syaten, calcKokusi(hands));
            syaten = Math.min(syaten, calcTiitoitu(hands));
        }
        syaten = Math.min(syaten, calcNormal(hands));

        return syaten;
    }

    @Override
    public int calcMin(int[] value34) {
        int syaten = 13;
        // 副露，或者暗杠时，即不可能再是七对子或国士的牌型
        int tehaiSize = Encode34.tehaiSize(value34);
        if (tehaiSize >= 13) {
            syaten = Math.min(syaten, calcKokusi(value34));
            syaten = Math.min(syaten, calcTiitoitu(value34));
        }
        int fixMenzu = Math.min(4, Math.max(14 - tehaiSize, 0) / 3);
        syaten = Math.min(syaten, calcNormal(value34, 4 - fixMenzu));
        return syaten;
    }

    @Override
    public int calcNormal(Collection<IHai> hands) {
        int menzuSize = hands.size() / 3;
        return calcNormal(Encode34.toEncode34(hands), menzuSize);
    }

    @Override
    public int calcNormal(int[] value34) {
        int tehaiSize = Encode34.tehaiSize(value34);
        int fixMenzu = Math.min(4, Math.max(14 - tehaiSize, 0) / 3);
        return calcNormal(value34, 4 - fixMenzu);
    }

    /**
     * 计算通常向听
     *
     * @param value34 34编码手牌值
     * @param menzuSize 面子数
     */
    private int calcNormal(int[] value34, int menzuSize) {
        int[] ret = mp1[accum(value34, 1, 9, value34[0])].clone();
        ret = add1(ret, mp1[accum(value34, 10, 18, value34[9])], menzuSize).clone();
        ret = add1(ret, mp1[accum(value34, 19, 27, value34[18])], menzuSize).clone();
        ret = add2(ret, mp2[accum(value34, 28, 34, value34[27])], menzuSize).clone();
        return ret[5 + menzuSize] - 1;
    }

    /**
     * 累积一个牌类型的值
     *
     * @param v 值
     * @param from 下标从（花色的第二枚）
     * @param to 下标到，不包含自身
     * @param base 基础值（花色的第一枚）
     */
    private int accum(int[] v, int from, int to, int base) {
        int ret = base;
        // 1 - 8
        for (int i = from; i < to; ++i) {
            ret = 5 * ret + v[i];
        }
        return ret;
    }

    /**
     * 数牌
     *
     * @param lhs
     * @param rhs
     * @param menzuSize 面子数
     */
    private int[] add1(int[] lhs, int[] rhs, int menzuSize) {
        for (int j = menzuSize + 5; j >= 5; --j) {
            int sht = Math.min(lhs[j] + rhs[0], lhs[0] + rhs[j]);

            for (int k = 5; k < j; ++k) {
                sht = min3(sht, lhs[k] + rhs[j - k], lhs[j - k] + rhs[k]);
            }
            lhs[j] = sht;
        }
        for (int j = menzuSize; j >= 0; --j) {
            int sht = lhs[j] + rhs[0];

            for (int k = 0; k < j; ++k) {
                sht = Math.min(sht, lhs[k] + rhs[j - k]);
            }
            lhs[j] = sht;
        }

        return lhs;
    }

    /**
     * 字牌
     *
     * @param lhs
     * @param rhs
     * @param m 面子数
     */
    private int[] add2(int[] lhs, int[] rhs, int m) {
        int j = m + 5;
        int sht = Math.min(lhs[j] + rhs[0], lhs[0] + rhs[j]);

        for (int k = 5; k < j; ++k) {
            sht = min3(sht, lhs[k] + rhs[j - k], lhs[j - k] + rhs[k]);
        }
        lhs[j] = sht;

        return lhs;
    }

    /**
     * 求3个数中的最小值
     *
     * @param a 参数
     * @param b 参数
     * @param c 参数
     */
    private int min3(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }

    // ------------------------------------------------------
    // 静态读取
    // ------------------------------------------------------

    /**
     * 读取索引文件
     */
    private static void loadSyatenIndexFile() throws IOException, ClassNotFoundException {
        URL indexUrl = FastSyatenCalculator.class.getClassLoader().getResource("syaten.zip");
        AssertHelper.notNull(indexUrl, "向听数索引压缩文件丢失");
        String fileName = indexUrl.getPath();
        File file = new File(fileName);
        try (ZipFile zipFile = new ZipFile(file)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            AssertHelper.isTrue(entries.hasMoreElements(), "向听数索引文件异常");
            ZipEntry zipEntry = entries.nextElement();
            try (ObjectInputStream oi = new ObjectInputStream(new BufferedInputStream(zipFile.getInputStream(zipEntry)))) {
                IndexSerializable serializable = (IndexSerializable) oi.readObject();
                FastSyatenCalculator.mp1 = serializable.getMp1();
                FastSyatenCalculator.mp2 = serializable.getMp2();
            }
        }
    }

    /**
     * 序列化文件
     */
    public static class IndexSerializable implements Serializable {
        private int[][] mp1;
        private int[][] mp2;

        public int[][] getMp1() {
            return mp1;
        }

        public void setMp1(int[][] mp1) {
            this.mp1 = mp1;
        }

        public int[][] getMp2() {
            return mp2;
        }

        public void setMp2(int[][] mp2) {
            this.mp2 = mp2;
        }
    }
}
