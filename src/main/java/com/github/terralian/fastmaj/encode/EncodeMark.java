package com.github.terralian.fastmaj.encode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.github.terralian.fastmaj.hai.HaiPool;
import com.github.terralian.fastmaj.hai.HaiTypeEnum;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.tehai.Tehai;

/**
 * 记号法帮助类
 * <p/>
 * 该类提供一组方法用于判断记号或者转换格式
 * <p/>
 * 关于记号法，这种编码方法通过1位数字为值，1位字母为牌类型的方法来表示一张牌的信息 ，如8s => 八索。
 * 该编码的优点在于对人友好易懂，相较于{@link Encode34}编码法而言，不会丢失红宝牌信息，可以用做持久化存储的备选方案。
 * 缺点是相较于{@link Encode34}编码，其不能直接进行计算，而且转换效率相对较慢一些。
 *
 * @author terra.lian
 */
public abstract class EncodeMark {

    /**
     * [记号法]东
     */
    public static final int DONG = 1, TON = 1;

    /**
     * [记号法]南
     */
    public static final int NAN = 2;

    /**
     * [记号法]西
     */
    public static final int XI = 3, SYA = 3;

    /**
     * [记号法]北
     */
    public static final int BEI = 4, PEI = 4;

    /**
     * [记号法]白
     */
    public static final int BAI = 5, HAKU = 5;

    /**
     * [记号法]发
     */
    public static final int FA = 6, HATU = 6;

    /**
     * [记号法]中
     */
    public static final int ZHONG = 7, TYUN = 7;


    /**
     * 是否红宝牌
     *
     * @param markValue 记号值
     */
    public static boolean isRedHai(int markValue) {
        return markValue == 0;
    }

    /**
     * 获取记号编码的最大值
     *
     * @param type 记号类型
     */
    public static int maxValue(HaiTypeEnum type) {
        return type == HaiTypeEnum.Z ? 7 : 9;
    }


    /**
     * 将字面量和类型转为记号法表示
     *
     * @param literal 字面量
     * @param type 类型
     */
    public static String encode(int literal, HaiTypeEnum type) {
        return literal + type.toString().toLowerCase();
    }

    /**
     * 将一张牌进行编码为记号法输出
     * <p/>
     * 如红5索=0s，5索=5s
     *
     * @param hai 牌
     */
    public static String encode(IHai hai) {
        return encode(hai.getLiteral(), hai.geHaiType());
    }

    /**
     * 将手牌转为记号法编码的字符串
     * <ul>
     * <li>手牌会进行排序
     * <li>若手牌正在等待弃牌，那么最后一枚进牌将会放在最后
     * <li>相同类型中间的牌类型会移除，如1m2m3m = 123m
     * <li>存在最后一枚进牌，则区别对待这枚，其输出会如123z1z，其中1z为最后一枚进牌
     * <li><b>会丢失副露信息</b>
     * </ul>
     *
     * @param tehai 手牌
     */
    public static String encode(ITehai tehai) {
        List<IHai> hais = tehai.getAll();
        IHai drawHai = null;
        // 最后一枚进张不改变排序
        if (hais.size() % 2 == 0) {
            drawHai = tehai.getDrawHai();
            hais.remove(hais.size() - 1);
        }
        // 对手牌其余部分进行排序
        ITehai.sort(hais);

        StringBuilder sb = new StringBuilder();
        HaiTypeEnum lastType = hais.get(0).geHaiType();
        for (IHai hai : hais) {
            if (hai.geHaiType() != lastType) {
                sb.append(lastType.name().toLowerCase());
            }
            sb.append(hai.getLiteral());
            lastType = hai.geHaiType();
        }
        sb.append(lastType.name().toLowerCase());

        // 若存在进牌，则将其放在最后一枚
        if (drawHai != null) {
            sb.append(encode(drawHai));
        }

        return sb.toString();
    }

    /**
     * 转为34类型编码
     *
     * @param value 记号值
     * @param type 记号类型
     */
    public static int toEncode34(int value, HaiTypeEnum type) {
        // 红5转普通5
        if (EncodeMark.isRedHai(value))
            value = 5;

        switch (type) {
            // 0 - 8
            case M:
                return value - 1;
            // 9 - 17
            case P:
                return value + 8;
            // 18 - 26
            case S:
                return value + 17;
            // 27 - 34
            default:
                return value + 26;
        }
    }

    /**
     * 使用一个长度为{@link Encode34#length()}的数组，将字符串转为34编码
     *
     * @param marks 字符串，如123m78p45s123z
     * @return 34编码值数组，[0,0,1,2,0...]
     */
    public static int[] toEncode34(String marks) {
        int[] result = new int[34];
        Arrays.fill(result, 0);

        char[] array = marks.toCharArray();
        HaiTypeEnum haiType = HaiTypeEnum.Z;
        for (int i = array.length - 1; i >= 0; i--) {
            char c = array[i];
            // 是否是数字，非数字则为类型
            if (Character.isDigit(c)) {
                // 字符的数字 - '0'可得到整形的映射
                int literal = c - '0';
                // 0 转为 5
                literal = clearRed(literal);

                result[toEncode34(literal, haiType)]++;
            } else {
                haiType = toTypeEnum(c);
            }
        }
        return result;
    }

    /**
     * 将形如123m78p45s123z形式的字符串转换为手牌
     *
     * @param nameMark 字符串
     */
    public static List<IHai> toHai(String nameMark) {
        char[] array = nameMark.toCharArray();
        HaiTypeEnum currentType = HaiTypeEnum.Z;

        List<IHai> hais = new ArrayList<>();
        for (int i = array.length - 1; i >= 0; i--) {
            char c = array[i];

            if (Character.isDigit(c)) {
                int literal = c - '0';
                hais.add(HaiPool.getByMark(literal, currentType));
            } else {
                currentType = toTypeEnum(c);
            }
        }

        // 由于是从后往前扫描的字符串，获得牌的顺序是反过来的
        Collections.reverse(hais);

        return hais;
    }

    /**
     * 将形如123m78p45s123z形式的字符串转换为手牌
     *
     * @param nameMark 字符串
     * @return 手牌
     */
    public static ITehai toTehai(String nameMark) {
        return new Tehai(toHai(nameMark));
    }

    /**
     * 将字符的记号类型转换为枚举
     *
     * @param typeChar 记号类型字符
     */
    public static HaiTypeEnum toTypeEnum(char typeChar) {
        if (typeChar == 'z' || typeChar == 'Z')
            return HaiTypeEnum.Z;
        else if (typeChar == 's' || typeChar == 'S')
            return HaiTypeEnum.S;
        else if (typeChar == 'p' || typeChar == 'P')
            return HaiTypeEnum.P;
        else if (typeChar == 'm' || typeChar == 'M')
            return HaiTypeEnum.M;
        throw new IllegalArgumentException("未知牌类型：" + typeChar);
    }

    /**
     * 去除红宝牌信息，将字面量转为不含红宝牌的实际值
     *
     * @param literal [1, 9] | [1, 7]
     */
    public static int clearRed(int literal) {
        return literal == 0 ? 5 : literal;
    }
}
