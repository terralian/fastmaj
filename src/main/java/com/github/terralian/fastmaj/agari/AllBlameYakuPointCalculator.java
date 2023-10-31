package com.github.terralian.fastmaj.agari;

import java.util.List;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.option.YakuBlameRule;
import com.github.terralian.fastmaj.player.RivalEnum;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.IYakuBlame;

/**
 * 包牌规则中{@link YakuBlameRule#ALL_YAKU}的点数计算实现实现
 * <p/>
 * 若存在包牌役，即使在复合役满的情况下，包牌的玩家也需要支付所有点数。
 *
 * @author terra.lian
 */
public class AllBlameYakuPointCalculator implements IBlameYakuPointCalculator {

    protected final IPointCalculator pointCalculator;

    /**
     * 构建一个{@link AllBlameYakuPointCalculator}实例
     *
     * @param pointCalculator 分数计算器，部分包牌计算需要用到分数计算器进行计算
     */
    public AllBlameYakuPointCalculator(IPointCalculator pointCalculator) {
        this.pointCalculator = pointCalculator;
    }

    @Override
    public void tsumoTransfer(int position, int oya, int basePoint, int honba, int kyotaku, int[] playerPoints,
            ITehai tehai, List<IYaku> yakus, GameConfig config) {
        // 确定包牌的役和被包牌的对象
        boolean hasMatch = false;
        for (IYaku yaku : yakus) {
            if (!yaku.isYakuBlame()) continue;
            IYakuBlame blameYaku = (IYakuBlame) yaku;
            RivalEnum rival = blameYaku.deduceBlameRival(tehai);
            if (rival == null) continue;
            // 按全点数包牌规则，一个玩家包牌了，那么他需要支付所有点数，但是若启用四杠子包牌，那么有可能同时有两个人包牌
            // 这时候在全点数包牌规则下就无法进行计算。
            if (hasMatch) {
                throw new IllegalStateException("全点数包牌规则下，不能在一次和了中包多种役满");
            }
            hasMatch = true;
            // 单个包牌计算
            tsumoTransfer(position, oya, basePoint, honba, kyotaku, playerPoints, rival);
        }
        // 没有包牌役成立，则使用通常规则转移
        if (!hasMatch) {
            pointCalculator.tsumoTransfer(position, oya, basePoint, honba, kyotaku, playerPoints);
        }
    }

    /**
     * 仅考虑一种包牌役时的自摸包牌分数计算，此时已知晓是哪个对手需要进行包牌
     */
    protected void tsumoTransfer(int position, int oya, int basePoint, int honba, int kyotaku, int[] playerPoints,
            RivalEnum rival) {
        int blamePosition = RivalEnum.calc(position, rival);
        // 此时相当于对手放铳，所有点数由包牌的玩家支付
        pointCalculator.ronTransfer(position, blamePosition, oya, basePoint, honba, kyotaku, playerPoints);
    }

    @Override
    public void ronTransfer(int position, int rivalPlayer, int oya, int basePoint, int honba, int kyotaku,
            int[] playerPoints, ITehai tehai, List<IYaku> yakus, GameConfig config) {
        // 确定包牌的役和被包牌的对象
        boolean hasMatch = false;
        for (IYaku yaku : yakus) {
            if (!yaku.isYakuBlame()) continue;
            IYakuBlame blameYaku = (IYakuBlame) yaku;
            RivalEnum rival = blameYaku.deduceBlameRival(tehai);
            if (rival == null) continue;
            // 按全点数包牌规则，一个玩家包牌了，那么他需要支付所有点数，但是若启用四杠子包牌，那么有可能同时有两个人包牌
            // 这时候在全点数包牌规则下就无法进行计算。
            if (hasMatch) {
                throw new IllegalStateException("全点数包牌规则下，不能在一次和了中包多种役满");
            }
            hasMatch = true;
            // 单个包牌计算
            ronTransfer(position, rivalPlayer, oya, basePoint, honba, kyotaku, playerPoints, rival);
        }
        // 没有包牌役成立，则使用通常规则转移
        if (!hasMatch) {
            pointCalculator.ronTransfer(position, rivalPlayer, oya, basePoint, honba, kyotaku, playerPoints);
        }
    }

    /**
     * 仅考虑一种包牌役时的荣和包牌分数计算，此时已知晓是哪个对手需要进行包牌
     */
    protected void ronTransfer(int position, int rivalPlayer, int oya, int basePoint, int honba, int kyotaku,
            int[] playerPoints, RivalEnum rival) {
        int blamePosition = RivalEnum.calc(position, rival);
        // 包牌时的包牌对象放铳
        if (rivalPlayer == blamePosition) {
            pointCalculator.ronTransfer(position, blamePosition, oya, basePoint, honba, kyotaku, playerPoints);
            return;
        }
        // 包牌时非包牌对手放铳，两者都需要支付一半的分数，其中包牌者也支付本场棒
        // 每人支付一半
        int lostPoint = basePoint / 2;
        // 包牌者先计算，含本场棒
        pointCalculator.ronTransfer(position, blamePosition, oya, lostPoint, honba, kyotaku, playerPoints);
        // 再计算放铳者
        pointCalculator.ronTransfer(position, rivalPlayer, oya, lostPoint, 0, 0, playerPoints);
    }
}
