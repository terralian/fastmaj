package com.github.terralian.fastmaj.util;

import java.util.Arrays;

/**
 * 排名工具
 * 
 * @author terra.lian
 */
public abstract class RankingHelper {

    /**
     * 根据玩家的分数计算排名
     * 
     * @param playerPoints 玩家分数
     * @return 按玩家分数数组的顺序，返回排名的数组
     */
    public static int[] calcRanking(int[] playerPoints) {
        int[] endRankings = new int[playerPoints.length];
        Arrays.fill(endRankings, 1);
        for (int i = 1; i < playerPoints.length; i++) {
            int pointTarget = playerPoints[i];
            for (int j = 0; j < i; j++) {
                if (playerPoints[j] < pointTarget)
                    endRankings[j]++;
                else
                    endRankings[i]++;
            }
        }
        return endRankings;
    }
}
