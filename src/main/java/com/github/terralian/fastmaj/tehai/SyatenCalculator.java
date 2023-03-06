package com.github.terralian.fastmaj.tehai;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.hai.IHai;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 向听计算器{@link ISyatenCalculator}的默认实现
 * <p/>
 * 该类基于回溯法设计，基于这种方法设计的优点是无需额外的内存占用，及索引表初始化，在一次使用时速度很快，基本无感。
 * 但是在大批量场景，如AI训练时需要大量计算向听数时，该实现的耗时便会难以忍受。数据量大的情况下可以考虑使用{@link FastSyatenCalculator}
 * <p/>
 * 该实现基本参考<b>麻雀C言語プログラム集</b>，但是原网站已不可用。
 * 该类的代码来源为<b>tenhou-visualizer</b>，在其代码的基础上兼容了鸣牌副露暗杠等形式。
 * 来源代码在对向听数的定义上与接口定义存在一定差异，形如手牌为11122233334444z，其向听数正确值为2，而原实现会计算为1。 这里修复了这个问题。
 * <p/>
 * 
 * @author terra.lian 
 * @see <a href=
 *      "https://web.archive.org/web/20190402234201/http://cmj3.web.fc2.com/index.htm#Syaten">麻雀C言語プログラム集</a>
 * @see <a href=
 *      "https://github.com/CrazyBBB/tenhou-visualizer/blob/master/src/main/java/tenhouvisualizer/domain/MahjongUtils.java">tenhou-visualizer</a>
 */
public class SyatenCalculator implements ISyatenCalculator {

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

        ValueKeeper keeper = new ValueKeeper();
        keeper.setSyaten(13);
        int fixMenzu = Math.min(4, Math.max(14 - tehaiSize, 0) / 3);
        keeper.setMenzu(fixMenzu);
        syaten = Math.min(syaten, calcNormal(value34, keeper));

        return syaten;
    }


    @Override
    public int calcTiitoitu(int[] value34) {
        // 向听
        int syaten;
        // 对子数
        int toituSize = 0;
        // 牌种类数
        int haiTypeSize = 0;

        for (int i = 0; i < 34; i++) {
            if (value34[i] > 0) haiTypeSize++;
            if (value34[i] > 1) toituSize++;
        }

        // 七对子最大向听为6向听
        // 13枚牌，换6张必然听牌
        syaten = 6 - toituSize;

        // 七对子至少7种牌才能听牌
        // 手上4个对子，原本只需要摸2枚其他类型的，即可听牌
        // 若是暗刻杠重复的话，则需要先补全其他类型的部分，这个操作即增加了向听数
        if (haiTypeSize < 7) {
            syaten += 7 - haiTypeSize;
        }

        return syaten;
    }

    @Override
    public int calcKokusi(int[] value34) {
        // 向听数
        int syaten = 13;
        // 幺九牌对子
        int toitu = 0;

        // 幺九牌
        int[] yaotyuHais = Encode34.YAOTYU_HAIS;
        for (int yaotyuIndex : yaotyuHais) {
            if (value34[yaotyuIndex] > 0) syaten--;
            if (value34[yaotyuIndex] > 1) toitu = 1;
        }
        // 国士有一个对子，当手牌存在一个幺九对子，向听减一
        syaten -= toitu;

        return syaten;
    }

    @Override
    public int calcNormal(Collection<IHai> hands) {
        ValueKeeper keeper = new ValueKeeper();
        keeper.setSyaten(13);
        int fixMenzu = Math.min(4, Math.max(14 - hands.size(), 0) / 3);
        keeper.setMenzu(fixMenzu);
        int[] value34 = Encode34.toEncode34(hands);
        return calcNormal(value34, keeper);
    }

    @Override
    public int calcNormal(int[] value34) {
        ValueKeeper keeper = new ValueKeeper();
        keeper.setSyaten(13);
        // 计算固定的面子数
        int tehaiSize = Encode34.tehaiSize(value34);
        int fixMenzu = Math.min(4, Math.max(14 - tehaiSize, 0) / 3);
        keeper.setMenzu(fixMenzu);

        return calcNormal(value34, keeper);
    }

    /**
     * 实际的通常向听计算方法
     * 
     * @param value34 手牌（34编码）
     * @param keeper 值缓存类
     */
    private int calcNormal(int[] value34, ValueKeeper keeper) {
        // 统计原来手牌中4枚，经过副露暗杠固定后，还有剩余的数量
        // 这部分手牌，在面子+搭子 >=4 时，不能作为雀头候补
        Set<Integer> invalidToituKouho = new HashSet<>();
        for (int i = 0; i < value34.length; i++) {
            if (value34[i] == 4)
                invalidToituKouho.add(i);
        }
        keeper.setInvalidToituKouho(invalidToituKouho);

        // 先切雀头
        for (int i = 0; i < 34; i++) {
            if (value34[i] >= 2) {
                keeper.plusToitu();
                value34[i] -= 2;
                menzuCutLoop(keeper, 0, value34);
                value34[i] += 2;
                keeper.subToitu();
            }
        }

        // 直接切面（雀头也按候补搭子算）
        menzuCutLoop(keeper, 0, value34);

        return keeper.getSyaten();
    }

    /**
     * 对手牌进行切面子递归，切完时，切搭子
     * 
     * @param keeper 值缓存类
     * @param i 索引
     * @param tehai 手牌
     */
    private void menzuCutLoop(ValueKeeper keeper, int i, int[] tehai) {
        // 跳过为0的部分
        while (i < 34 && tehai[i] == 0)
            i++;
        // 循环到末尾，开始切搭子
        if (i == 34) {
            taazuCutLoop(keeper, 0, tehai);
            return;
        }

        // 切暗刻
        if (tehai[i] >= 3) {
            keeper.plusMenzu();
            tehai[i] -= 3;
            menzuCutLoop(keeper, i, tehai);
            tehai[i] += 3;
            keeper.subMenzu();
        }

        // 当i非字牌，且 i为[1, 7]， i的后面两张都有牌时，切面子
        if (i < 27 && i % 9 <= 6 && tehai[i + 1] >= 1 && tehai[i + 2] >= 1) {
            keeper.plusMenzu();
            tehai[i]--;
            tehai[i + 1]--;
            tehai[i + 2]--;
            menzuCutLoop(keeper, i, tehai);
            tehai[i]++;
            tehai[i + 1]++;
            tehai[i + 2]++;
            keeper.subMenzu();
        }

        // 切下一张
        menzuCutLoop(keeper, i + 1, tehai);
    }

    /**
     * 切搭子部分并进行向听计算
     * 
     * @param keeper 值缓存类
     * @param i 索引
     * @param tehai 手牌
     */
    private void taazuCutLoop(ValueKeeper keeper, int i, int[] tehai) {
        // 跳过
        while (i < 34 && tehai[i] == 0)
            i++;

        // 计算向听数，取最小值
        if (i == 34) {
            int tmp = 8 - keeper.getMenzu() * 2 - keeper.getKouho() - keeper.getToitu();
            tmp = checkToituKouhoValid(tmp, tehai, keeper);
            int syaten = Math.min(keeper.getSyaten(), tmp);

            keeper.setSyaten(syaten);
            return;
        }

        if (keeper.getMenzu() + keeper.getKouho() < 4) {
            // 对子
            if (tehai[i] > 2 || (tehai[i] == 2 && !keeper.getInvalidToituKouho().contains(i))) {
                keeper.plusKouho();
                tehai[i] -= 2;
                taazuCutLoop(keeper, i, tehai);
                tehai[i] += 2;
                keeper.subKouho();
            }

            // 两面或边张
            if (i < 27 && i % 9 <= 7 && tehai[i + 1] >= 1) {
                keeper.plusKouho();
                tehai[i]--;
                tehai[i + 1]--;
                taazuCutLoop(keeper, i, tehai);
                tehai[i]++;
                tehai[i + 1]++;
                keeper.subKouho();
            }

            // 坎张
            if (i < 27 && i % 9 <= 6 && tehai[i + 2] >= 1) {
                keeper.plusKouho();
                tehai[i]--;
                tehai[i + 2]--;
                taazuCutLoop(keeper, i, tehai);
                tehai[i]++;
                tehai[i + 2]++;
                keeper.subKouho();
            }
        }

        // 切下一张
        taazuCutLoop(keeper, i + 1, tehai);
    }

    /**
     * 判断雀头候补是否有效，无效则需要将向听数+1，换雀头听牌
     * 
     * @param currentSyaten 当前计算的向听数
     * @param tehai 当前扣减完剩余的手牌
     * @param keeper 值缓存
     */
    private int checkToituKouhoValid(int currentSyaten, int[] tehai, ValueKeeper keeper) {
        // 雀头为0，手上有4枚的情况，需要判断雀头候补是否有效
        if (currentSyaten <= 1 && keeper.getToitu() == 0 && keeper.getInvalidToituKouho().size() > 0) {
            for (int i = 0; i < 34; i++) {
                if (tehai[i] > 0 && !keeper.getInvalidToituKouho().contains(i)) {
                    return currentSyaten;
                }
            }
            return currentSyaten + 1;
        }
        return currentSyaten;
    }

    /**
     * 计算值缓存类
     */
    @Getter
    @Setter
    @Accessors(chain = true)
    private static class ValueKeeper {
        /** 面子数 */
        private int menzu;
        /** 对子数 */
        private int toitu;
        /** 候补数 */
        private int kouho;
        /** 向听数 */
        private int syaten;
        /** 无效对子候补 */
        private Set<Integer> invalidToituKouho;

        /**
         * 面子加1
         */
        public void plusMenzu() {
            menzu++;
        }

        /**
         * 面子减1
         */
        public void subMenzu() {
            menzu--;
        }

        /**
         * 对子加1
         */
        public void plusToitu() {
            toitu++;
        }

        /**
         * 对子减1
         */
        public void subToitu() {
            toitu--;
        }

        /**
         * 候补加1
         */
        public void plusKouho() {
            kouho++;
        }

        /**
         * 候补减1
         */
        public void subKouho() {
            kouho--;
        }
    }
}
