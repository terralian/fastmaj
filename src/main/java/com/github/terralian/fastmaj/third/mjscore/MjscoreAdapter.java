package com.github.terralian.fastmaj.third.mjscore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.agari.ITehaiAgariDivider;
import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.hai.HaiPool;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.tehai.ITehaiLock;
import com.github.terralian.fastmaj.util.CollectionUtil;

/**
 * 和了分割对MSCORE的适配
 * <p>
 * 支持副露，暗杠等固定手牌的变化
 * 
 * @author terra.lian 
 */
public class MjscoreAdapter implements ITehaiAgariDivider {

    @Override
    public List<DivideInfo> divide(ITehai tehai) {
        return divide(tehai, true, tehai.getDrawHai());
    }

    @Override
    public List<DivideInfo> divide(ITehai tehai, boolean isRon, IHai agariHai) {
        // 使用非固定的手牌部分进行拆分
        int[] value34 = Encode34.toEncode34(tehai.getHand());
        int[] pos = MjsAgari.allocatePos();
        int[] results = MjsAgari.agari(value34, pos);

        List<DivideInfo> divides = new ArrayList<>(2);
        // 国士或者其他情况下，拆分结果为空，此时，也需要一个返回值
        if (results == null) {
            setKokusiDivide(divides, value34);
            return divides;
        }

        // 转换返回值
        for (int r : results) {
            // 雀头
            int jantou = pos[(r >> 6) & 0xF];

            // 刻子
            int numKotsu = r & 0x7;
            Set<IHai> handKotsuFirst = new LinkedHashSet<>();
            for (int i = 0; i < numKotsu; i++) {
                IHai hai = HaiPool.getByValue(pos[(r >> (10 + i * 4)) & 0xF]);
                handKotsuFirst.add(hai);
            }
            
            // 顺子
            int numShuntsu = (r >> 3) & 0x7;
            List<IHai> handShuntsuFirst = new ArrayList<>();
            for (int i = 0; i < numShuntsu; i++) {
                IHai hai = HaiPool.getByValue(pos[(r >> (10 + numKotsu * 4 + i * 4)) & 0xF]);
                handShuntsuFirst.add(hai);
            }
            List<IHai> allShuntsuFirst = new ArrayList<>(handShuntsuFirst);
            allShuntsuFirst.addAll(tehai.getLock().getChiFirst());


            DivideInfo divide = new DivideInfo();
            divide.setJantou(HaiPool.getByValue(jantou)) //
                    .setHandKotsuFirst(handKotsuFirst)//
                    .setAllShuntsuFirst(allShuntsuFirst) //
                    .setHandShuntsuFirst(handShuntsuFirst) //
                    .setTiitoitu((r & (1 << 26)) != 0) //
                    .setTyuuren((r & (1 << 27)) != 0) //
                    .setRyanpeikou((r & (1 << 29)) != 0) //
                    .setIipeikou((r & (1 << 30)) != 0);

            fixIipeikou(tehai, divide);
            partitionkotsu(tehai, divide, isRon, agariHai);
            divides.add(divide);
        }

        return divides;
    }

    /**
     * 根据分割出的刻子，拆分为暗刻，明刻
     * 
     * @param tehai 手牌
     * @param agariDivide 分割
     * @param isRon 是否荣和
     * @param agariHai 和了的牌
     */
    private void partitionkotsu(ITehai tehai, DivideInfo agariDivide, boolean isRon, IHai agariHai) {
        ITehaiLock tehaiLock = tehai.getLock();

        Set<IHai> annkotsu = new HashSet<>(agariDivide.getHandKotsuFirst());
        Set<IHai> allKanKotsu = new HashSet<>(tehaiLock.getNotChiFirst());
        
        // 所有的刻子/杠子
        allKanKotsu.addAll(agariDivide.getHandKotsuFirst());
        // 明刻包含杠
        Set<IHai> minkotsu = new HashSet<>(tehaiLock.getPonFirst());

        // 移除红宝牌信息
        IHai clearRed = agariHai.isRedDora() //
                ? HaiPool.getByValue(agariHai.getValue()) //
                : agariHai;
        // 荣和情况下要二次判定和了的牌是否是暗刻
        if (isRon && annkotsu.contains(clearRed)) {
            // 若和了的牌是顺子组成的一个，则无需处理
            List<IHai> hais = agariDivide.getHandShuntsuFirst();
            boolean inShuntsu = hais.stream().anyMatch(k -> Encode34.isInShuntsu(k, clearRed));
            if (!inShuntsu) {
                annkotsu.remove(clearRed);
                minkotsu.add(clearRed);
            }
        }

        agariDivide.setAllKanKotsuFirst(allKanKotsu);
        agariDivide.setAnnKotsuFirst(annkotsu);
        agariDivide.setMinKotsuFirst(minkotsu);
    }

    /**
     * 使用手牌部分，而非包含固定区的所有牌部分进行计算会存在一杯口的不判定问题。
     * 当手牌进行过暗杠操作，手牌的面子数小于4，mjscore会当做非门清状态，而不判断一杯口。
     * <p>
     * 使用该方法对一杯口的问题进行补全。
     * 
     * @param tehai 手牌
     * @param agariDivide 拆分的结果
     */
    private void fixIipeikou(ITehai tehai, DivideInfo agariDivide) {
        // 仅暗杠下会存在一杯口判定错误的问题
        // 暗杠下也不会存在二杯口，则不会有一杯口和二杯口的冲突
        if (tehai.isNaki() || !tehai.isAnnkan()) {
            return;
        }
        List<IHai> handShuntsuFirst = agariDivide.getHandShuntsuFirst();
        if (handShuntsuFirst.size() < 2) {
            return;
        }
        if (CollectionUtil.containsDuplicate(handShuntsuFirst)) {
            agariDivide.setIipeikou(true);
        }
    }

    /**
     * 国士情况下的手牌分割（大部分值为空）
     * 
     * @param divides
     * @param value34
     */
    private void setKokusiDivide(List<DivideInfo> divides, int[] value34) {
        int jantou = -1;
        for (int i = 0; i < Encode34.YAOTYU_HAIS.length; i++) {
            int v = value34[Encode34.YAOTYU_HAIS[i]];
            if (v == 0) {
                return;
            } else if (v > 1) {
                jantou = v;
            }
        }
        // 无雀头
        if (jantou < 0) {
            return;
        }

        DivideInfo divide = new DivideInfo();
        divide.setJantou(HaiPool.getByValue(jantou)) //
                .setHandKotsuFirst(Collections.emptySet())//
                .setAllKanKotsuFirst(Collections.emptySet()) //
                .setAnnKotsuFirst(Collections.emptySet()) //
                .setMinKotsuFirst(Collections.emptySet()) //
                .setAllShuntsuFirst(Collections.emptyList()) //
                .setHandShuntsuFirst(Collections.emptyList()) //
                .setTiitoitu(false) //
                .setTyuuren(false) //
                .setRyanpeikou(false) //
                .setIipeikou(false);
        divides.add(divide);
    }
}
