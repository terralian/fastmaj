package com.github.terralian.fastmaj.player;

/**
 * 玩家帮助类
 *
 * @author terra.lian
 * @since 2023-04-24
 */
public abstract class PlayerHelper {

    /**
     * 获取当前玩家的下一名玩家，如玩家4 -> 玩家1
     *
     * @param playerSize 玩家人数
     * @param currentPosition 当前玩家坐席
     */
    public static int nextPlayer(int playerSize, int currentPosition) {
        return currentPosition == playerSize - 1 ? 0 : currentPosition + 1;
    }
}
