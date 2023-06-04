package com.github.terralian.fastmaj.encode;

import com.github.terralian.fastmaj.game.KazeEnum;
import com.github.terralian.fastmaj.hai.HaiPool;
import com.github.terralian.fastmaj.hai.HaiTypeEnum;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.tehai.Tehai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 34编码相关方法和编码值
 * <p/>
 * 该类可用于便捷获取各个牌在34编码中所表示的值，也提供了一组用于判断和转换为其他格式的方法
 * <p/>
 * 关于34编码，该编码法将[0,33]映射为34种牌，以实现编码，这种编码法很适合仅需要考虑种类（如5s，8p），不关心像136编码一样关心每一张牌的场合。
 * 依照该编码法可以通过整形值快速判断一个牌的类型，及进行各种操作，如获取宝牌指示牌对应的宝牌。
 * <p/>
 * 34编码法的缺陷是会丢失牌是否为红宝牌信息，所以不适合作为持久化方案。
 *
 * @author terra.lian
 */
public abstract class Encode34 {

    // ------------------------------------
    // 万子
    // ------------------------------------

    /**
     * 1万
     */
    public final static int MAN_1 = 0;
    /**
     * 2万
     */
    public final static int MAN_2 = 1;
    /**
     * 3万
     */
    public final static int MAN_3 = 2;
    /**
     * 4万
     */
    public final static int MAN_4 = 3;
    /**
     * 5万
     */
    public final static int MAN_5 = 4;
    /**
     * 6万
     */
    public final static int MAN_6 = 5;
    /**
     * 7万
     */
    public final static int MAN_7 = 6;
    /**
     * 8万
     */
    public final static int MAN_8 = 7;
    /**
     * 9万
     */
    public final static int MAN_9 = 8;

    // ------------------------------------
    // 筒子
    // ------------------------------------

    /**
     * 1筒
     */
    public final static int PIN_1 = 9;
    /**
     * 2筒
     */
    public final static int PIN_2 = 10;
    /**
     * 3筒
     */
    public final static int PIN_3 = 11;
    /**
     * 4筒
     */
    public final static int PIN_4 = 12;
    /**
     * 5筒
     */
    public final static int PIN_5 = 13;
    /**
     * 6筒
     */
    public final static int PIN_6 = 14;
    /**
     * 7筒
     */
    public final static int PIN_7 = 15;
    /**
     * 8筒
     */
    public final static int PIN_8 = 16;
    /**
     * 9筒
     */
    public final static int PIN_9 = 17;

    // ------------------------------------
    // 索子
    // ------------------------------------

    /**
     * 1索
     */
    public final static int SO_1 = 18;
    /**
     * 2索
     */
    public final static int SO_2 = 19;
    /**
     * 3索
     */
    public final static int SO_3 = 20;
    /**
     * 4索
     */
    public final static int SO_4 = 21;
    /**
     * 5索
     */
    public final static int SO_5 = 22;
    /**
     * 6索
     */
    public final static int SO_6 = 23;
    /**
     * 7索
     */
    public final static int SO_7 = 24;
    /**
     * 8索
     */
    public final static int SO_8 = 25;
    /**
     * 9索
     */
    public final static int SO_9 = 26;

    // ------------------------------------
    // 字牌（罗马音）
    // ------------------------------------

    /**
     * 东
     */
    public final static int DONG = 27, TON = 27;
    /**
     * 南
     */
    public final static int NAN = 28;
    /**
     * 西
     */
    public final static int XI = 29, SYA = 29;
    /**
     * 北
     */
    public final static int BEI = 30, PEI = 30;
    /**
     * 白
     */
    public final static int BAI = 31, HAKU = 31;
    /**
     * 发
     */
    public final static int FA = 32, HATU = 32;
    /**
     * 中
     */
    public final static int ZHONG = 33, TYUN = 33;

    /**
     * 所有幺九牌的编码
     * <p/>
     * 包含数牌的1和9及字牌（东南西北白发中）
     */
    public final static int[] YAOTYU_HAIS = new int[] {
        MAN_1, MAN_9, PIN_1, PIN_9, SO_1, SO_9, //
        TON, NAN, SYA, PEI, HAKU, HATU, TYUN
    };
    
    /**
     * 所有牌的幺九属性
     * <p/>
     * 为True表示幺九牌，为False表示非幺九
     */
    private static final boolean[] IS_YAOTYU_ARRAY = new boolean[] {
        true, false, false, false, false, false, false, false, true, //
        true, false, false, false, false, false, false, false, true, //
        true, false, false, false, false, false, false, false, true, //
        true, true, true, true, true, true, true,
    };

    // ------------------------------------
    // 编码转换方法
    // ------------------------------------

    /**
     * 将34编码法的值转换为记号法的值
     * 
     * @param value34 34编码法的值
     * @return 记号法的值 [1 - 9] | [1 - 7]
     */
    public static int toMarkValue(int value34) {
        if (value34 <= MAN_9)
            return value34 + 1;
        else if (value34 <= PIN_9)
            return value34 - 8;
        else if (value34 <= SO_9)
            return value34 - 17;
        return value34 - 26;
    }

    /**
     * 将34编码法的值转换为记号法的值
     * <p/>
     * 当为红宝牌时，无论是否为红5，都会返回是红宝牌（0），这样做是为了兼容特殊规则下红1，红2的场景
     * 
     * @param value34 34编码法的值
     * @param isRedHai 是否红宝牌，只要该值为true，则无论是什么牌，都是红宝牌
     * @return 记号法的值 [1 - 9] | [1 - 7] | 0
     */
    public static int toMarkValue(int value34, boolean isRedHai) {
        if (isRedHai)
            return 0;
        return toMarkValue(value34);
    }

    /**
     * 将34编码法的值转换为记号法的记号类型
     * 
     * @param value34 34编码法的值
     * @return 记号法类型的枚举
     */
    public static HaiTypeEnum toMarkType(int value34) {
        if (value34 <= MAN_9)
            return HaiTypeEnum.M;
        else if (value34 <= PIN_9)
            return HaiTypeEnum.P;
        else if (value34 <= SO_9)
            return HaiTypeEnum.S;
        return HaiTypeEnum.Z;
    }

    /**
     * 将34编码法的值转换为记号法
     * <p/>
     * 例子： <code>8 -> 9m</code>
     * 
     * @param value34 34编码法的值
     * @return 记号法，如9s
     */
    public static String toMarkString(int value34) {
        int v = toMarkValue(value34);
        HaiTypeEnum type = toMarkType(value34);
        return v + type.toString().toLowerCase();
    }

    /**
     * 将34编码数组转为手牌
     * <p/>
     * <b>该方法的参数并不是长度为34的数组，而是14枚手牌，按34编码存储为每个元素的数组</b>
     *
     * @param haiValues 手牌值集合
     */
    public static ITehai toTehai(int[] haiValues) {
        List<IHai> hais = new ArrayList<>();
        for (int haiValue : haiValues) {
            hais.add(HaiPool.getByValue(haiValue));
        }
        return new Tehai(hais);
    }

    /**
     * 将34编码集合转为手牌
     * <p/>
     * 该方法的参数并不是长度为34的数组，而是14枚手牌，按34编码存储为每个元素的数组
     * 
     * @param encode34Tehai 34编码的手牌集合
     */
    public static ITehai toTehai(List<Integer> encode34Tehai) {
        List<IHai> hais = new ArrayList<>();
        for (Integer haiValue : encode34Tehai) {
            hais.add(HaiPool.getByValue(haiValue));
        }
        return new Tehai(hais);
    }

    /**
     * 将136编码的值，重新编码为34编码法
     * 
     * @param value136 通过136编码的值，[0, 135]
     * @return [0, 33]
     */
    public static int toEncode34(int value136) {
        return value136 / 4;
    }

    /**
     * 将风牌转换为编码34
     * 
     * @param kaze 风牌
     */
    public static int toEncode34(KazeEnum kaze) {
        return kaze.getOrder() + TON;
    }

    /**
     * 将手牌集合转换为34编码数组
     *
     * @param hais 手牌
     */
    public static int[] toEncode34(Collection<IHai> hais) {
        int[] value = new int[TYUN + 1];
        for (IHai hai : hais) {
            value[hai.getValue()]++;
        }
        return value;
    }

    /**
     * 同类型的下一枚牌
     * <p/>
     * 该方法可以用做查询宝牌指示牌的下一枚牌
     * 
     * @param encode34 34编码格式的牌
     */
    public static int nextHai(int encode34) {
        if (encode34 == MAN_9 || encode34 == SO_9 || encode34 == PIN_9) {
            return encode34 - 8;
        } else if (encode34 == BEI) {
            return TON;
        } else if (encode34 == ZHONG) {
            return BAI;
        }
        return encode34 + 1;
    }

    // ------------------------------------
    // 判断方法
    // ------------------------------------
    
    /**
     * 判断是否是34编码中的幺九牌
     * <p/>
     * 幺九牌包含数牌中的19和所有字牌
     * 
     * @param value34 34编码法的值
     */
    public static boolean isYaotyuHai(int value34) {
        return IS_YAOTYU_ARRAY[value34];
    }

    /**
     * 判断是否是34编码中的中张牌
     * <p/>
     * 中张牌为数牌中的2到8，也可以认为是非幺九牌
     * 
     * @param value34 34编码法的值
     */
    public static boolean isMiddleHai(int value34) {
        return !IS_YAOTYU_ARRAY[value34];
    }

    /**
     * 判断两张牌是否相邻
     *
     * @param a 牌A
     * @param b 牌B
     */
    public static boolean isAdjoin(IHai a, IHai b) {
        return a.geHaiType() == b.geHaiType() && Math.abs(a.getValue() - b.getValue()) == 1;
    }

    /**
     * 是否是顺子组成，即在顺子三枚里
     * 
     * @param shunzuFirst 顺子的第一枚牌
     * @param target 目标牌
     */
    public static boolean isInShunzu(IHai shunzuFirst, IHai target) {
        if (shunzuFirst.getLiteral() >= 8) {
            throw new IllegalArgumentException("参数错误，该牌不可能为顺子的第一枚：" + shunzuFirst);
        }
        if (shunzuFirst.geHaiType() != target.geHaiType()) {
            return false;
        }
        int a = shunzuFirst.getValue();
        int b = target.getValue();
        return a == b || (a + 1) == b || (a + 2) == b;
    }

    /**
     * 集合是否包含某张牌，使用34编码进行比较，会丢失红宝牌信息。
     * 
     * @param hais 手牌集合
     * @param target 目标
     */
    public static boolean contains(Collection<IHai> hais, IHai target) {
        for (IHai hai : hais) {
            if (hai.valueEquals(target)) {
                return true;
            }
        }
        return false;
    }

    // ------------------------------------
    // 比较方法
    // ------------------------------------

    /**
     * 判断牌是否是某个风牌，如equals(东, KazeEnum.DON)
     *
     * @param hai 目标牌
     * @param kaze 目标风
     */
    public static boolean equals(IHai hai, KazeEnum kaze) {
        return toEncode34(kaze) == hai.getValue();
    }

    /**
     * 多张牌中获取最小的那张，该方法通过{@link IHai#getValue()}进行34编码比较
     *
     * @param hais 需要比较的牌
     */
    public static IHai min(IHai... hais) {
        IHai minHai = hais[0];
        for (int i = 1; i < hais.length; i++) {
            IHai target = hais[i];
            if (minHai.getValue() > target.getValue()) {
                minHai = target;
            }
        }
        return minHai;
    }

    /**
     * 获取最小编码牌的值
     * 
     * @return 最小牌的值（一万）
     */
    public static int min() {
        return MAN_1;
    }

    /**
     * 获取最大编码牌的值
     * 
     * @return 最大牌的值（中）
     */
    public static int max() {
        return TYUN;
    }

    /**
     * 判断牌a是否大于牌b
     * <p/>
     * 仅根据编码值进行比较，跨花色的情况下也成立，如：1p > 1m。
     * 
     * @param a 牌a
     * @param b 牌b
     */
    public static boolean isBigger(IHai a, IHai b) {
        return a.getValue() > b.getValue();
    }

    /**
     * 获取编码的长度
     */
    public static int length() {
        return TYUN + 1;
    }

    /**
     * 获取一个数组中的手牌数
     * <p/>
     * 如[1, 2, 0, 0 ... ] 共3枚手牌
     * 
     * @param array 34编码数组
     */
    public static int tehaiSize(int[] array) {
        int tehaiSize = 0;
        for (int i : array)
            if (i > 0)
                tehaiSize += i;
        return tehaiSize;
    }

    /**
     * 初始化好一个34编码的数组
     */
    public static int[] newArray() {
        int[] value = new int[length()];
        Arrays.fill(value, 0);
        return value;
    }
}
