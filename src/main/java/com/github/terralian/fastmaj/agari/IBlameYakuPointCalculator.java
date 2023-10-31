package com.github.terralian.fastmaj.agari;

import java.util.List;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.option.YakuBlameRule;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.IYakuBlame;

/**
 * 包牌的分数计算器
 * <p/>
 * 关于本场棒的支付，虽然有规则为非包牌者支付，但是由于天凤和雀魂都采取包牌者支付，以及没有看过规则实例，
 * 这里先仅使用本场棒也由包牌者支付的规则。
 *
 * @author terra.lian
 */
public interface IBlameYakuPointCalculator {

    /**
     * 匹配的役中是否存在包牌役，若规则中启用包牌，且存在包牌役则返回true，将进行包牌计算（可能）
     *
     * @param matchYakus 匹配的役
     * @param config 游戏设置
     */
    static boolean containBlameYaku(List<IYaku> matchYakus, GameConfig config) {
        // 不启用包牌的话跳过
        if (config.getBlameRule() == YakuBlameRule.DISABLED) return false;
        // 无役满的话也跳过（仅有役满情形下才会存在包牌役）
        if (!matchYakus.get(0).isYakuman()) return false;
        // 判定匹配的役中是否存在包牌役
        for (IYaku yaku : matchYakus) {
            if (!(yaku instanceof IYakuBlame)) {
                continue;
            }
            IYakuBlame blameYaku = (IYakuBlame) yaku;
            if (blameYaku.usingBlameRule(config)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 自摸时分数转移计算，会根据庄家闲家区分，包含场供。
     * <p/>
     * 使用该方法需要已确认存在包牌役，无包牌役会抛出异常
     *
     * @param position 自己的坐席
     * @param oya 庄家坐席
     * @param basePoint 底分，由番数决定，需要未经过取整的实际值
     * @param honba 本场数，一个本场对每加多收100点
     * @param kyotaku 立直棒，一根1000分
     * @param playerPoints 玩家的分数
     * @param tehai 手牌
     * @param yakus 匹配的役
     * @param config 游戏配置
     */
    void tsumoTransfer(int position, int oya, int basePoint, int honba, int kyotaku, int[] playerPoints, ITehai tehai,
            List<IYaku> yakus, GameConfig config);

    /**
     * 包牌时的荣和时分数转移计算.
     * <p/>
     * 使用该方法需要已确认存在包牌役，无包牌役会抛出异常
     *
     * @param position 自己的坐席
     * @param rivalPlayer 放铳的玩家
     * @param oya 庄家坐席
     * @param basePoint 底分，由番数决定，需要未经过取整的实际值
     * @param honba 本场数，一个本场对每加多收100点
     * @param kyotaku 立直棒，一根1000分
     * @param playerPoints 玩家的分数
     * @param tehai 手牌
     * @param yakus 匹配的役
     * @param config 游戏配置
     */
    void ronTransfer(int position, int rivalPlayer, int oya, int basePoint, int honba, int kyotaku, int[] playerPoints,
            ITehai tehai, List<IYaku> yakus, GameConfig config);

}
