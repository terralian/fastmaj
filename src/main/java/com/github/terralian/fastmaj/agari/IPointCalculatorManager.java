package com.github.terralian.fastmaj.agari;

import java.util.List;

import com.github.terralian.fastmaj.game.context.IPlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;

/**
 * 用于管理和了计算中的点数计算器。由于点数计算中存在包牌逻辑，而依照不同规则包牌计算还存在差异。
 * 因此，当前存在基于通常规则计算{@link IPointCalculator}，也有基于包牌规则计算的{@link IBlameYakuPointCalculator}，
 * 所以需要一个接口根据规则及当前的状态集来调度不同的分数计算器，实现一个完整的分数计算逻辑。
 *
 * @author terra.lian
 */
public interface IPointCalculatorManager {

    /**
     * 根据参数协调调度不同的计算器，完成一次分数转移
     *
     * @param tehai 手牌
     * @param fromPlayer 来源玩家
     * @param matchYakus 匹配的役种
     * @param han 番
     * @param fu 符数
     * @param playerPoints 玩家的分数
     * @param context 游戏上下文
     */
    void dispatchTransfer(ITehai tehai, Integer fromPlayer, List<IYaku> matchYakus, int han, int fu, int[] playerPoints,
            IPlayerGameContext context);
}
