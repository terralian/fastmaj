package com.github.terralian.fastmaj.agari;

import java.util.List;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.context.IPlayerGameContext;
import com.github.terralian.fastmaj.game.option.YakuBlameRule;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Terra.Lian
 */
@Getter
@Setter
public class PointCalculatorManager implements IPointCalculatorManager {

    /**
     * 通常的点数计算器
     */
    private IPointCalculator pointCalculator;
    /**
     * 用于计算{@link YakuBlameRule#ONLY_BLAME_YAKU}的点数计算器
     */
    private IBlameYakuPointCalculator onlyBlameYakuPointCalculator;
    /**
     * 用于计算{@link YakuBlameRule#ALL_YAKU}的点数计算器
     */
    private IBlameYakuPointCalculator allBlameYakuPointCalculator;

    /**
     * 构建一个{@link PointCalculatorManager}，该构建会指定使用{@link OnlyBlameYakuPointCalculator}
     * 及{@link AllBlameYakuPointCalculator}作为包牌计算器
     *
     * @param pointCalculator 通常点数计算器
     */
    public PointCalculatorManager(IPointCalculator pointCalculator) {
        this.pointCalculator = pointCalculator;
        this.onlyBlameYakuPointCalculator = new OnlyBlameYakuPointCalculator(pointCalculator);
        this.allBlameYakuPointCalculator = new AllBlameYakuPointCalculator(pointCalculator);
    }

    @Override
    public void dispatchTransfer(ITehai tehai, Integer fromPlayer, List<IYaku> matchYakus, int han, int fu,
            int[] playerPoints,
            IPlayerGameContext context) {
        // 玩家坐席
        int position = context.getPosition();
        // 计算底分
        int basePoint = pointCalculator.calcBasePoint(matchYakus, fu, han, context.getGameConfig());
        // 当前场上存在的立直棒
        int kyotaku = context.getKyotaku();
        // 当前本场数
        int honba = context.getRealHonba();
        // 游戏配置
        GameConfig config = context.getGameConfig();

        // 包牌规则计算
        if (IBlameYakuPointCalculator.containBlameYaku(matchYakus, config)) {
            // 选择一种规则的包牌计算器进行计算
            IBlameYakuPointCalculator useCalculator = config.getBlameRule() == YakuBlameRule.ONLY_BLAME_YAKU  //
                    ? onlyBlameYakuPointCalculator //
                    : allBlameYakuPointCalculator;
            if (position != fromPlayer) {
                useCalculator.ronTransfer(position, fromPlayer, context.getOya(), basePoint, honba, kyotaku,
                        playerPoints, tehai, matchYakus, config);
            } else {
                useCalculator.tsumoTransfer(position, context.getOya(), basePoint, honba, kyotaku, playerPoints, tehai,
                        matchYakus, config);
            }
            return;
        }
        //通常规则计算
        if (position != fromPlayer) {
            pointCalculator.ronTransfer(position, fromPlayer, context.getOya(), basePoint, honba, kyotaku, playerPoints);
        } else {
            pointCalculator.tsumoTransfer(position, context.getOya(), basePoint, honba, kyotaku, playerPoints);
        }
    }
}
