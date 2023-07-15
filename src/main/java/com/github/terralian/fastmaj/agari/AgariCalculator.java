package com.github.terralian.fastmaj.agari;

import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.util.EmptyUtil;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.IYakuMatcher;
import com.github.terralian.fastmaj.yaku.hn.NormalDora;
import com.github.terralian.fastmaj.yaku.hn.RedDora;
import com.github.terralian.fastmaj.yaku.hn.UraDora;
import com.github.terralian.fastmaj.yama.DoraHelper;

import java.util.Arrays;
import java.util.List;

/**
 * 默认和了计算器
 *
 * @author terra.lian
 */
public class AgariCalculator implements IAgariCalculator {

    /**
     * 役匹配器，有值时使用指定的役匹配器。若为指定，则使用默认的
     * <p/>
     * 通过更改役匹配器，可以决定规则使用什么役种，比如是否启用人和，古役。
     */
    private final IYakuMatcher yakuMatcher;
    /**
     * 和了时，手牌分割器
     */
    private final ITehaiAgariDivider agariDivider;
    /**
     * 符数计算
     */
    private final IFuCalculator fuCalculator;
    /**
     * 分数计算器
     */
    private final IPointCalculator pointCalculator;

    /**
     * 初始化构建和了计算器
     *
     * @param yakuMatcher 役种计算器
     * @param agariDivider 和了分割器
     * @param fuCalculator 符数计算器
     * @param pointCalculator 分数计算器
     */
    public AgariCalculator(IYakuMatcher yakuMatcher, ITehaiAgariDivider agariDivider, IFuCalculator fuCalculator,
                           IPointCalculator pointCalculator) {
        this.yakuMatcher = yakuMatcher;
        this.agariDivider = agariDivider;
        this.fuCalculator = fuCalculator;
        this.pointCalculator = pointCalculator;
    }

    /**
     * 和了计算
     * 
     * @param tehai 手牌，已包含和了牌
     * @param agariHai 和了牌
     * @param fromPlayer 来自玩家，自摸则和自己一致
     * @param doraHais 宝牌，立直情况下，包含里宝牌
     * @param context 玩家游戏上下文
     */
    @Override
    public AgariInfo calc(ITehai tehai, IHai agariHai, Integer fromPlayer, List<IHai> doraHais, List<IHai> uraDoraHais,
            PlayerGameContext context) {
        int position = context.getPosition();
        // 使用和了分割器对手牌进行分割，分割的结果更容易进行和了役种匹配
        List<DivideInfo> divideInfos = agariDivider.divide(tehai, position != fromPlayer, agariHai);

        AgariInfo bestAgariInfo = null;
        // 循环所有分割方案，找到使分数最大的一种
        for (DivideInfo divideInfo : divideInfos) {
            AgariInfo agariInfo = this.calc(tehai, agariHai, divideInfo, fromPlayer, doraHais, uraDoraHais, context);
            if (bestAgariInfo == null || bestAgariInfo.getScore() < agariInfo.getScore()) {
                bestAgariInfo = agariInfo;
            }
        }
        return bestAgariInfo;
    }
    
    /**
     * 计算其中一种手牌分割的和了信息
     * 
     * @param tehai 手牌
     * @param agariHai 和了牌
     * @param divideInfo 分割信息
     * @param fromPlayer 来自玩家，自摸则和自己一致
     * @param doraHais 宝牌
     * @param context 游戏上下文
     */
    private AgariInfo calc(ITehai tehai, IHai agariHai, DivideInfo divideInfo, Integer fromPlayer, List<IHai> doraHais,
            List<IHai> uraDoraHais,
            PlayerGameContext context) {
        // 玩家坐席
        int position = context.getPosition();
        // 役种
        List<IYaku> yakus = yakuMatcher.match(tehai, divideInfo, context);
        // 悬赏役
        addDoraYaku(yakus, tehai, doraHais, uraDoraHais);
        // 番数
        int han = IYaku.sumHan(yakus, context.isNaki());
        // 符
        int fu = fuCalculator.compute(tehai, agariHai, divideInfo, context);
        // 计算底分
        int basePoint = pointCalculator.calcBasePoint(yakus, fu, han, context.getGameConfig());
        // 立直棒
        int kyotaku = context.getKyotaku();
        // 本场数
        int honba = context.getRealHonba();
        // 玩家的分数
        int[] playerPoints = Arrays.copyOf(context.getPlayerPoints(), context.getPlayerPoints().length);
        // 荣和的场合
        if (position != fromPlayer) {
            pointCalculator.ronTransfer(position, fromPlayer, context.getOya(), basePoint, honba, kyotaku, playerPoints);
        } else {
            pointCalculator.tsumoTransfer(position, context.getOya(), basePoint, honba, kyotaku, playerPoints);
        }

        // 返回和了信息
        AgariInfo agariInfo = new AgariInfo();
        agariInfo.setScore(playerPoints[position] - context.getPlayerPoints()[position]);
        agariInfo.setFu(fu);
        agariInfo.setBan(han);
        agariInfo.setYakus(yakus);
        agariInfo.setDivideInfo(divideInfo);
        agariInfo.setIncreaseAndDecrease(playerPoints);

        return agariInfo;
    }

    /**
     * 组装悬赏役
     *
     * @param yakus 役种集合
     * @param tehai 手牌
     * @param doraHais 宝牌
     * @param uraDoraHais 里宝牌
     */
    protected void addDoraYaku(List<IYaku> yakus, ITehai tehai, List<IHai> doraHais, List<IHai> uraDoraHais) {
        // 役满的话，不计悬赏
        if (EmptyUtil.isNotEmpty(yakus) && yakus.get(0).isYakuman()) {
            return;
        }

        // 宝牌役（包含拔北）
        int doraSize = DoraHelper.countDoraSize(tehai, doraHais) + tehai.countKita();
        if (doraSize > 0) {
            yakus.add(new NormalDora(doraSize));
        }
        // 里宝牌
        int uraDoraSize = DoraHelper.countDoraSize(tehai, uraDoraHais);
        if (uraDoraSize > 0) {
            yakus.add(new UraDora(uraDoraSize));
        }
        // 红宝牌
        int redDora = DoraHelper.countRedDoraSize(tehai);
        if (redDora > 0) {
            yakus.add(new RedDora(redDora));
        }
    }
}
