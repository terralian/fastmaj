package com.github.terralian.fastmaj.game.validator;

import java.util.Arrays;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.KazeEnum;
import com.github.terralian.fastmaj.util.RankingUtil;

/**
 * 游戏结束判定，用于 判定一局游戏是否已经结束。基于规则的不同，结束的条件也不同，大体功能如下：
 * <ul>
 * <li>在一局胜负的模式下，只要进行校验即游戏结束。
 * <li>一般情况下，校验玩家分数是否小于0，若小于0则提前结束。
 * <li>若当前庄家未连庄，并且已经为最后一局(结束风+1 | 北)，游戏结束。
 * <li>若当前庄家连庄，并且分数超过精算点，游戏结束。
 * </ul>
 * 
 * @author terra.lian
 */
public class GameEndValidator implements IGameValidator {

    /**
     * 判定一场游戏是否游戏结束
     * 
     * @param gameConfig 游戏规则
     * @param gameCore 游戏核心
     */
    public boolean validate(GameConfig gameConfig, IGameCore gameCore) {
        // 单局胜负，直接结束
        if (gameConfig.getSingleKyoku()) {
            return true;
        }
        // 当玩家分数存在小于0时，游戏结束
        int[] playerPoints = gameCore.getPlayerPoints();
        if (Arrays.stream(playerPoints).anyMatch(k -> k < 0)) {
            return true;
        }

        // 当前场风
        KazeEnum bakaze = gameCore.getBakaze();
        // 当前庄家
        int oya = gameCore.getOya();
        // 结束的场风
        KazeEnum endBakaze = gameConfig.getEndBakaze();
        // 中途流局，对局继续
        if (gameCore.isHalfwayRyuukyuku()) {
            return false;
        }

        // 东风战未到东4，东南战未到南4局，对局继续
        if (bakaze.getOrder() < endBakaze.getOrder()
                || (bakaze.getOrder() == endBakaze.getOrder() && oya < gameConfig.getPlayerSize() - 1)) {
            return false;
        }
        // 西入后的最后一局，若庄家未连庄则直接结束
        if (bakaze == KazeEnum.next(endBakaze) && oya == gameCore.getPlayerSize() - 1 && !gameCore.isRenchan()) {
            return true;
        }
        // 东四或者南四这样的末局，当庄家分数超过结束线，并且排名第一则直接结束
        int[] ranking = RankingUtil.calcRanking(playerPoints);
        Integer endPointLine = gameConfig.getEndPointLine();
        if (playerPoints[oya] >= endPointLine && ranking[oya] == 1) {
            return true;
        }
        // 或者庄家未连庄，有人分数大于等于结束线，则直接结束
        if (!gameCore.isRenchan()) {
            for (int i = 0; i < playerPoints.length; i++) {
                if (i != oya && playerPoints[i] >= endPointLine) {
                    return true;
                }
            }
        }
        // 游戏未结束
        return false;
    }
}
