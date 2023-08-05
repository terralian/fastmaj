package com.github.terralian.fastmaj.agari;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.option.YakuBlameRule;
import com.github.terralian.fastmaj.player.RivalEnum;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.util.Assert;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.IYakuBlame;

/**
 * 包牌规则中{@link YakuBlameRule#ONLY_BLAME_YAKU}的点数计算实现实现
 * <p/>
 * 包牌者仅需要支付包牌役的分数，在复合役满中，非包牌的役的部分分数按通常规则处理
 *
 * @author Terra.Lian
 */
public class OnlyBlameYakuPointCalculator extends AllBlameYakuPointCalculator {

    /**
     * 构建一个{@link AllBlameYakuPointCalculator}实例
     *
     * @param pointCalculator 分数计算器，部分包牌计算需要用到分数计算器进行计算
     */
    public OnlyBlameYakuPointCalculator(IPointCalculator pointCalculator) {
        super(pointCalculator);
    }

    @Override
    public void tsumoTransfer(int position, int oya, int basePoint, int honba, int kyotaku, int[] playerPoints,
            ITehai tehai, List<IYaku> yakus, GameConfig config) {
        // 获取所有的宝牌役（极大部分情况下仅有1个）
        List<IYakuBlame> blameYakus = new ArrayList<>();
        for (IYaku yaku : yakus) {
            if (yaku.isYakuBlame())
                blameYakus.add((IYakuBlame) yaku);
        }
        Assert.notEmpty(blameYakus, "需要匹配到包牌役才能使用IBlameYakuPointCalculator类计算");

        // 先计算包牌部分的役，并扣减所有底分
        for (IYakuBlame blameYaku : blameYakus) {
            RivalEnum rival = blameYaku.deduceBlameRival(tehai);
            // 部分情况下不会存在包牌情况
            if (rival == null) continue;

            // 该包牌役的点数
            int blameBasePoint = pointCalculator.calcBasePoint(Collections.singletonList(blameYaku), 0, 13, config);
            basePoint -= blameBasePoint;
            // 在复合包牌情形下，若不启用复合役满，则仅会有一个役满点数生效，此时就会产生规则冲突。
            // 虽然可以多个包牌平分单个役满分数，暂时先不考虑这种实现。
            Assert.isTrue(basePoint >= 0, "包牌点数总和超过了总点数，是否启用了四杠子包牌规则的同时，未启用复合役满规则?");
            // 单个包牌计算
            tsumoTransfer(position, oya, blameBasePoint, honba, kyotaku, playerPoints, rival);
            // 仅计算一次本场棒和供托
            if (honba > 0 || kyotaku > 0) {
                honba = kyotaku = 0;
            }
        }
        // 没有包牌役成立，或者含非包牌的役满部分点数按通常规则转移
        if (basePoint > 0) {
            pointCalculator.tsumoTransfer(position, oya, basePoint, 0, 0, playerPoints);
        }
    }

    @Override
    public void ronTransfer(int position, int rivalPlayer, int oya, int basePoint, int honba, int kyotaku,
            int[] playerPoints, ITehai tehai, List<IYaku> yakus, GameConfig config) {
        // 获取所有的宝牌役（极大部分情况下仅有1个）
        List<IYakuBlame> blameYakus = new ArrayList<>();
        for (IYaku yaku : yakus) {
            if (yaku.isYakuBlame())
                blameYakus.add((IYakuBlame) yaku);
        }
        Assert.notEmpty(blameYakus, "需要匹配到包牌役才能使用IBlameYakuPointCalculator类计算");
        // 先计算包牌部分的役，并扣减所有底分
        for (IYakuBlame blameYaku : blameYakus) {
            RivalEnum rival = blameYaku.deduceBlameRival(tehai);
            // 部分情况下不会存在包牌情况
            if (rival == null) continue;

            // 该包牌役的点数
            int blameBasePoint = pointCalculator.calcBasePoint(Collections.singletonList(blameYaku), 0, 13, config);
            basePoint -= blameBasePoint;
            // 在复合包牌情形下，若不启用复合役满，则仅会有一个役满点数生效，此时就会产生规则冲突。
            // 虽然可以多个包牌平分单个役满分数，暂时先不考虑这种实现。
            Assert.isTrue(basePoint >= 0, "包牌点数总和超过了总点数，是否启用了四杠子包牌规则的同时，未启用复合役满规则?");
            // 单个包牌计算
            ronTransfer(position, rivalPlayer, oya, basePoint, honba, kyotaku, playerPoints, rival);
            // 仅计算一次本场棒和供托
            if (honba > 0 || kyotaku > 0) {
                honba = kyotaku = 0;
            }
        }
        // 没有包牌役成立，或者含非包牌的役满部分点数按通常规则转移
        if (basePoint > 0) {
            pointCalculator.ronTransfer(position, rivalPlayer, oya, basePoint, honba, kyotaku, playerPoints);
        }
    }
}
