package com.github.terralian.fastmaj.agari;

import java.util.List;

import com.github.terralian.fastmaj.game.context.IPlayerGameContext;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.util.Assert;
import com.github.terralian.fastmaj.util.EmptyUtil;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.IYakuMatcher;
import com.github.terralian.fastmaj.yaku.hn.NormalDora;
import com.github.terralian.fastmaj.yaku.hn.RedDora;
import com.github.terralian.fastmaj.yaku.hn.UraDora;
import com.github.terralian.fastmaj.yama.DoraHelper;
import lombok.Getter;
import lombok.Setter;

/**
 * 默认和了计算器
 *
 * @author terra.lian
 */
@Getter
@Setter
public class AgariCalculator implements IAgariCalculator {

    /**
     * 无役对象
     */
    private static final AgariInfo NOT_AGARI = new AgariInfo().setScore(0);

    /**
     * 役匹配器，有值时使用指定的役匹配器。若为指定，则使用默认的
     * <p/>
     * 通过更改役匹配器，可以决定规则使用什么役种，比如是否启用人和，古役。
     */
    private IYakuMatcher yakuMatcher;
    /**
     * 和了时，手牌分割器
     */
    private ITehaiAgariDivider agariDivider;
    /**
     * 符数计算
     */
    private IFuCalculator fuCalculator;
    /**
     * 分数计算管理器
     */
    private IPointCalculatorManager pointCalculatorManager;

    /**
     * 初始化构建和了计算器
     *
     * @param yakuMatcher 役种计算器
     * @param agariDivider 和了分割器
     * @param fuCalculator 符数计算器
     * @param pointCalculatorManager 分数计算器管理器
     */
    public AgariCalculator(IYakuMatcher yakuMatcher, ITehaiAgariDivider agariDivider, IFuCalculator fuCalculator,
            IPointCalculatorManager pointCalculatorManager) {
        this.yakuMatcher = yakuMatcher;
        this.agariDivider = agariDivider;
        this.fuCalculator = fuCalculator;
        this.pointCalculatorManager = pointCalculatorManager;
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
    public AgariInfo calculate(ITehai tehai, IHai agariHai, Integer fromPlayer, List<IHai> doraHais,
            List<IHai> uraDoraHais,
            IPlayerGameContext context) {
        int position = context.getPosition();
        // 使用和了分割器对手牌进行分割，分割的结果更容易进行和了役种匹配
        List<DivideInfo> divideInfos = agariDivider.divide(tehai, position != fromPlayer, agariHai);
        Assert.notEmpty(divideInfos, "手牌进行和了分割失败，请确认当前手牌是否尚未和了?");

        // 仅有单个分割的情况（大多数）
        AgariInfo bestAgariInfo = calculateSingleDivide(tehai, agariHai, divideInfos.get(0), fromPlayer, doraHais, uraDoraHais, context);
        // 含多个分割的情况，循环剩余的分割方案，找到使分数最大的一种
        for (int i = 1; i < divideInfos.size(); i++) {
            AgariInfo agariInfo = calculateSingleDivide(tehai, agariHai, divideInfos.get(i), fromPlayer, doraHais, uraDoraHais, context);
            if (bestAgariInfo.getScore() < agariInfo.getScore()) {
                bestAgariInfo = agariInfo;
            }
        }
        // 判定是否匹配正确
        Assert.isTrue(bestAgariInfo != NOT_AGARI, "未从手牌分割匹配到任意一个启和役，请检查");

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
    private AgariInfo calculateSingleDivide(ITehai tehai, IHai agariHai, DivideInfo divideInfo, Integer fromPlayer,
            List<IHai> doraHais,
            List<IHai> uraDoraHais,
            IPlayerGameContext context) {
        // 玩家坐席
        int position = context.getPosition();
        // 匹配役种
        List<IYaku> matchYakus = yakuMatcher.match(tehai, divideInfo, (PlayerGameContext) context); // TODO 修改为IPlayerGameContext
        // 比如三色同顺时，123m11223344p123s，在分割为11p雀头，234p顺子时会无役
        if (EmptyUtil.isEmpty(matchYakus)) {
            return NOT_AGARI;
        }
        // 为役种增加悬赏役
        addDoraYaku(matchYakus, tehai, doraHais, uraDoraHais);
        // 计算番数
        int han = IYaku.sumHan(matchYakus, context.isNaki());
        // 计算符数
        int fu = fuCalculator.compute(tehai, agariHai, divideInfo, context);
        // 玩家的点数
        int[] playerPoints = context.copyPlayerPoints();
        // 点数转移
        pointCalculatorManager.dispatchTransfer(tehai, fromPlayer, matchYakus, han, fu, playerPoints, context);
        // 返回和了信息
        return new AgariInfo() //
                .setScore(playerPoints[position] - context.getPublicSpace(position).getPlayerPoint()) //
                .setFu(fu) //
                .setBan(han) //
                .setYakus(matchYakus) //
                .setDivideInfo(divideInfo) //
                .setIncreaseAndDecrease(playerPoints);
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
