package com.github.terralian.fastmaj.agari;

import java.util.Collection;
import java.util.List;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.yaku.IYaku;

/**
 * 点数计算
 * 
 * @author terra.lian
 */
public interface IPointCalculator {

    /**
     * 荒牌流局分数转移计算，不区分庄家闲家。
     * 
     * @param tenpais 已听牌的玩家
     * @param basePoint 底分，对每家收的分数一样，这里应该是1000
     * @param playerPoints 玩家的分数
     */
    void ryuukyokuTransfer(Collection<Integer> tenpais, int basePoint, int[] playerPoints);

    /**
     * 流局满贯分数转移计算，根据获得的点数，从其他家拿来对应的得点。庄家和非庄家在分数计算上会不一致
     * <p>
     * 该方法没有计算本场及立直棒，仅通过底风来进行分数增减
     * 
     * @param position 自己的坐席
     * @param oya 庄家坐席
     * @param basePoint 底分，流满算满贯，底分为2000
     * @param playerPoints 玩家的分数
     */
    void ryuukyokuTransfer(int position, int oya, int basePoint, int[] playerPoints);

    /**
     * 游戏结束时的场供处理，若存在场供，则由第一名收取。若同分则按坐席优先。
     * 
     * @param kyotaku 场供
     * @param playerPoints 点数
     */
    void gameEndRyuukyoku(int kyotaku, int[] playerPoints);

    /**
     * 自摸时分数转移计算，会根据庄家闲家区分，包含场供。
     * 
     * @param position 自己的坐席
     * @param oya 庄家坐席
     * @param basePoint 底分，由番数决定，需要未经过取整的实际值
     * @param honba 本场数，一个本场对每加多收100点
     * @param kyotaku 立直棒，一根1000分
     * @param playerPoints 玩家的分数
     */
    void tsumoTransfer(int position, int oya, int basePoint, int honba, int kyotaku, int[] playerPoints);

    /**
     * 荣和时分数转移计算，单对单分数转移。不计包牌规则。
     * 
     * @param position 自己的坐席
     * @param rivalPlayer 放铳的玩家
     * @param oya 庄家坐席
     * @param basePoint 底分，由番数决定，需要未经过取整的实际值
     * @param honba 本场数，一个本场对每加多收100点
     * @param kyotaku 立直棒，一根1000分
     * @param playerPoints 玩家的分数
     */
    void ronTransfer(int position, int rivalPlayer, int oya, int basePoint, int honba, int kyotaku, int[] playerPoints);

    /**
     * 底分计算，不同番数及符数的底分不一样。在不同规则下也不一样（如有无累计役满，有无复合役满）
     * <p>
     * 当底分小于5番时，计算出来的分数为未向上取整的分数，如20符2番，期待为400，实际320。实际使用时需要在实际按庄家闲家倍数计算后，再向上取值。
     * 其中闲家 = CEIL(320) = 400，庄家 = CEIL(320 * 2) =700。
     * 
     * @param yakumanMultiple 役满倍数，复合役满或者多倍役满
     * @param fu 符数
     * @param han 番数
     * @param gameConfig 游戏规则
     */
    int calcBasePoint(int yakumanMultiple, int fu, int han);

    /**
     * 底分计算，不同番数及符数的底分不一样。在不同规则下也不一样（如有无累计役满，有无复合役满）
     * <p>
     * 当底分小于5番时，计算出来的分数为未向上取整的分数，如20符2番，期待为400，实际320。实际使用时需要在实际按庄家闲家倍数计算后，再向上取值。
     * 其中闲家 = CEIL(320) = 400，庄家 = CEIL(320 * 2) =700。
     * 
     * @param yakus 役种，若为役满则全部都是役满。
     * @param fu 符数
     * @param han 番数
     * @param gameConfig 游戏规则
     */
    int calcBasePoint(List<IYaku> yakus, int fu, int han, GameConfig gameConfig);

    /**
     * 将分数向上取整，取整范围为百位以上。如320取整 = 400。
     * 
     * @param point 分数
     */
    int ceilPoint(int point);
}
