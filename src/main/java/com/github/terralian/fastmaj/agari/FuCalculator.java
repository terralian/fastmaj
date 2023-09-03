package com.github.terralian.fastmaj.agari;

import java.util.List;

import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.game.context.IPlayerGameContext;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.tehai.ITehaiLock;
import com.github.terralian.fastmaj.yaku.h1.Pinfu;

/**
 * 符数计算，在和牌时计算符数，会校验向听数，即仅在和牌时才能计算符数
 *
 * @author 作者: terra.lian
 */
public class FuCalculator implements IFuCalculator {

    private final Pinfu pinfu;

    public FuCalculator() {
        pinfu = new Pinfu();
    }

    @Override
    public int compute(ITehai tehai, IHai agariHai, List<DivideInfo> divideInfos, IPlayerGameContext context) {
        int fu = 0;
        for (DivideInfo divideInfo : divideInfos) {
            fu = Math.max(fu, compute(tehai, agariHai, divideInfo, context));
        }
        return fu;
    }

    @Override
    public int compute(ITehai tehai, IHai agariHai, DivideInfo divideInfo, IPlayerGameContext context) {
        // 【特殊】七对子 固定为25符
        if (divideInfo.isTiitoitu()) {
            return 25;
        }

        // 底符20符
        int fu = 20;
        // 是否荣和
        boolean ron = context.isEndByRon();
        // 门前荣和10符
        if (ron && !tehai.isNaki()) {
            fu += 10;
        }

        // 【特殊】平和自摸固定20符，若荣和加10符
        if (pinfu.match(tehai, divideInfo, context)) {
            return fu;
        }

        // 雀头字牌，客风0符，三元牌2符，自风场风各加2符
        IHai jantou = divideInfo.getJantou();
        if (jantou.isJiHai()) {
            fu += jantou.isSanGenHai() ? 2 : getJantouKazeFu(jantou, context);
        }

        ITehaiLock tehaiLock = tehai.getLock();
        // 暗杠（中张16，幺九32）
        for (IHai hai : tehaiLock.getAnnkanFirst()) {
            fu += hai.isYaotyuHai() ? 32 : 16;
        }
        // 明杠（中张8，幺九16）
        for (IHai hai : tehaiLock.getMinkanFirst()) {
            fu += hai.isYaotyuHai() ? 16 : 8;
        }
        // 暗刻明杠（中张4，幺九8）
        for (IHai hai : divideInfo.getAnnkoFirst()) {
            fu += hai.isYaotyuHai() ? 8 : 4;
        }
        // 明刻 （中张2，幺九4）
        for (IHai hai : divideInfo.getMinkoFirst()) {
            fu += hai.isYaotyuHai() ? 4 : 2;
        }
        // 顺子 0符

        // 自摸2符
        if (!ron) {
            fu += 2;
        }
        // 单骑2符
        if (jantou.valueEquals(agariHai)) {
            fu += 2;
        } else {
            for (IHai hai : divideInfo.getHandShunzuFirst()) {
                if (hai.geHaiType() != agariHai.geHaiType()) {
                    continue;
                }
                // 坎张2符
                if (hai.getValue() + 1 == agariHai.getValue()) {
                    fu += 2;
                    break;
                }
                // 边张2符
                else if (hai.getLiteral() == 1 && hai.getValue() + 2 == agariHai.getValue()) {
                    fu += 2;
                    break;
                } else if (hai.getLiteral() == 7 && hai.valueEquals(agariHai)) {
                    fu += 2;
                    break;
                }
            }
        }

        // 【特殊】副露后20符时，符30符
        if (fu == 20 && tehai.isNaki()) {
            fu = 30;
        }
        // 进位
        return (int) (Math.ceil(fu / 10.0) * 10);
    }

    /**
     * 获取风符（自风，场风2符，连风牌再+2符）
     *
     * @param jantou 雀头牌
     * @param context 玩家游戏上下文
     */
    private int getJantouKazeFu(IHai jantou, IPlayerGameContext context) {
        int fu = 0;
        if (Encode34.equals(jantou, context.getBakaze()))
            fu += 2;
        if (Encode34.equals(jantou, context.getJikaze()))
            fu += 2;
        return fu;
    }
}
