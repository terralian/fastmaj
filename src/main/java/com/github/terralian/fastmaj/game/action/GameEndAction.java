package com.github.terralian.fastmaj.game.action;

import com.github.terralian.fastmaj.agari.IPointCalculator;
import com.github.terralian.fastmaj.agari.PointCalculator;
import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.IGameEventQueue;
import com.github.terralian.fastmaj.game.event.GameEvent;
import com.github.terralian.fastmaj.game.event.handler.IGameEventHandler;
import com.github.terralian.fastmaj.game.event.system.SystemEventCode;

/**
 * 游戏结束动作，除了调用核心结束游戏外，还需要处理剩余的场供
 *
 * @author terra.lian
 */
public class GameEndAction implements IGameAction, IGameEventHandler {

    /**
     * 点数计算器
     */
    private final IPointCalculator calculator;

    /**
     * 构建一个游戏结束动作
     */
    public GameEndAction() {
        this.calculator = new PointCalculator();
    }

    /**
     * 执行游戏结束动作
     *
     * @param config 游戏规则
     * @param gameCore 游戏核心
     */
    public void doAction(GameConfig config, IGameCore gameCore) {
        // 剩余场供处理
        int[] playerPoints = gameCore.getPlayerPoints();
        calculator.gameEndRyuukyoku(gameCore.getKyotaku(), gameCore.getPlayerPoints());
        gameCore.endGame(playerPoints);
    }

    /**
     * @return
     */
    @Override
    public int handleEventCode() {
        return SystemEventCode.GAME_END;
    }

    /**
     * @param gameEvent 事件（实现类）
     * @param gameCore 游戏内核
     * @param gameConfig 游戏配置
     * @param eventQueue 事件队列
     */
    @Override
    public void handle(GameEvent gameEvent, IGameCore gameCore, GameConfig gameConfig, IGameEventQueue eventQueue) {
        doAction(gameConfig, gameCore);
    }
}
