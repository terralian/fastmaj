package com.github.terralian.fastmaj.test.game;

import com.github.terralian.fastmaj.replay.TenhouQueueReplayPlayerBuilder;

/**
 * 队列玩家构造，用于验证单元测试
 * 
 * @author terra.lian
 */
class TenhouTestPlayerBuilder extends TenhouQueueReplayPlayerBuilder {

    private int[] playerPoints;

    @Override
    public void endGame(int[] playerPoints, int[] playerScores) {
        super.endGame(playerPoints, playerScores);
        this.playerPoints = playerPoints;
    }

    public int[] getPlayerPoints() {
        return playerPoints;
    }
}