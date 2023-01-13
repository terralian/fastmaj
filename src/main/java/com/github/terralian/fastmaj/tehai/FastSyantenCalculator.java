package com.github.terralian.fastmaj.tehai;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.hai.IHai;

/**
 * 快速向听计算器
 * <p>
 * 修改了回溯法设计的{@link SyantenCalculator#calcNormal(java.util.Collection)}方法，改为基于索引查和了型的数（向听数+1） 从而加快速度。
 * 该实现对于大量计算向听数场景十分有效，其性能是十分优异的。其缺点是初始化耗时较久，体感在3-4秒左右，单次计算使用时需要不停初始化索引。
 * 另外索引文件压缩后仅500KB左右，解压缩后会达到40MB，所以本身存在一定的内存占用。
 * <p>
 * 该实现来自于<b>tomohxx</b>，为c++实现，根据<b>comssa56</b>改写的JS版本改写为java版本，其原理及详情可以参考注释中的链接。
 *
 * @author terra.lian
 * @see <a href=
 *      "https://qiita.com/tomohxx/items/75b5f771285e1334c0a5">tomohxx記事</a>
 * @see <a href="https://www.cnblogs.com/syui-terra/p/16673262.html">原理的中文机翻</a>
 * @see <a href="https://github.com/tomohxx/shanten-number">github:
 *      tomohxx/shanten-number</a>
 * @see <a href=
 *      "https://github.com/comssa56/snc_js/blob/main/source/shanten.js">
 *      github: comssa56/snc_js</a>
 */
public class FastSyantenCalculator extends SyantenCalculator {

    private static int[][] mp1 = null;
    private static int[][] mp2 = null;
    static {
        try {
            loadSyantenIndexFile();
        } catch (Exception e) {
            throw new IllegalStateException("初始化FastSyantenCalculator异常", e);
        }
    }

    @Override
    public int calcMin(Collection<IHai> hands) {
        int syanten = 13;

        // 副露，或者暗杠时，即不可能再是七对子或国士的牌型
        if (hands.size() >= 13) {
            syanten = Math.min(syanten, calcKokusi(hands));
            syanten = Math.min(syanten, calcTiitoitu(hands));
        }
        syanten = Math.min(syanten, calcNormal(hands));

        return syanten;
    }

    @Override
    public int calcMin(int[] value34) {
        int syanten = 13;
        // 副露，或者暗杠时，即不可能再是七对子或国士的牌型
        int tehaiSize = Encode34.tehaiSize(value34);
        if (tehaiSize >= 14) {
            syanten = Math.min(syanten, calcKokusi(value34));
            syanten = Math.min(syanten, calcTiitoitu(value34));
        }
        int fixMentu = Math.min(4, Math.max(14 - tehaiSize, 0) / 3);
        syanten = Math.min(syanten, calcNormal(value34, 4 - fixMentu));
        return syanten;
    }

    @Override
    public int calcNormal(Collection<IHai> hands) {
        int mentuSize = hands.size() / 3;
        return calcNormal(Encode34.toEncode34(hands), mentuSize);
    }

    @Override
    public int calcNormal(int[] value34) {
        int tehaiSize = Encode34.tehaiSize(value34);
        int fixMentu = Math.min(4, Math.max(14 - tehaiSize, 0) / 3);
        return calcNormal(value34, 4 - fixMentu);
    }

    /**
     * 计算通常向听
     *
     * @param value34 34编码手牌值
     * @param mentuSize 面子数
     */
    private int calcNormal(int[] value34, int mentuSize) {
        int[] ret = mp1[accum(value34, 1, 9, value34[0])].clone();
        ret = add1(ret, mp1[accum(value34, 10, 18, value34[9])], mentuSize).clone();
        ret = add1(ret, mp1[accum(value34, 19, 27, value34[18])], mentuSize).clone();
        ret = add2(ret, mp2[accum(value34, 28, 34, value34[27])], mentuSize).clone();
        return ret[5 + mentuSize] - 1;
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
     * @param mentuSize 面子数
     */
    private int[] add1(int[] lhs, int[] rhs, int mentuSize) {
        for (int j = mentuSize + 5; j >= 5; --j) {
            int sht = Math.min(lhs[j] + rhs[0], lhs[0] + rhs[j]);

            for (int k = 5; k < j; ++k) {
                sht = min3(sht, lhs[k] + rhs[j - k], lhs[j - k] + rhs[k]);
            }
            lhs[j] = sht;
        }
        for (int j = mentuSize; j >= 0; --j) {
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
    private static void loadSyantenIndexFile() throws IOException {
        String fileName = FastSyantenCalculator.class.getClassLoader().getResource("syanten.zip").getPath();
        File file = new File(fileName);
        try (ZipFile zipFile = new ZipFile(file)) {
            Enumeration<? extends ZipEntry> enums = zipFile.entries();
            Map<String, ZipEntry> map = new HashMap<>();
            while (enums.hasMoreElements()) {
                ZipEntry entry = enums.nextElement();
                map.put(entry.getName(), entry);
            }
            mp1 = parseIndexZipEntry(zipFile.getInputStream(map.get("index_s.csv")));
            mp2 = parseIndexZipEntry(zipFile.getInputStream(map.get("index_h.csv")));
        }
    }

    /**
     * 将索引的压缩文件解析为mp数组
     * 
     * @param is 输入流
     */
    private static int[][] parseIndexZipEntry(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("GBK")));
        List<String> lines = new ArrayList<>();
        String line = null;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }

        int[][] mp = new int[lines.size()][10];
        for (int i = 0; i < lines.size(); i++) {
            line = lines.get(i);
            String[] array = line.split(",");
            for (int j = 0; j < array.length; j++) {
                mp[i][j] = Integer.parseInt(array[j]);
            }
        }
        return mp;
    }
}
