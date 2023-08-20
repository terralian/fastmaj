package com.github.terralian.fastmaj.game;

import com.github.terralian.fastmaj.game.event.GameEvent;
import com.github.terralian.fastmaj.game.event.GameEventCode;
import com.github.terralian.fastmaj.game.event.handler.IGameEventHandler;
import com.github.terralian.fastmaj.game.event.system.CommonSystemEventPool;
import com.github.terralian.fastmaj.util.Assert;
import lombok.Getter;
import lombok.Setter;

/**
 * 以事件驱动的麻将游戏实例
 *
 * @author Terra.Lian
 */
@Getter
@Setter
public class EventMajongGame implements IEventMajongGame {

    /**
     * 游戏规则
     */
    protected GameConfig gameConfig;
    /**
     * 游戏核心
     */
    protected IGameCore gameCore;
    /**
     * 游戏队列
     */
    private IGameEventQueue eventQueue;
    /**
     * 游戏事件处理器的管理器
     */
    private IGameEventHandlerManager gameEventHandlerManager;

    public EventMajongGame(GameConfig gameConfig, IGameCore gameCore, GameComponent gameComponent,
            IGameEventQueue gameEventQueue) {
        this.gameConfig = gameConfig;
        this.gameCore = gameCore;
        this.eventQueue = gameEventQueue;
        this.gameEventHandlerManager = gameComponent.getGameEventHandlerManager();
    }

    /**
     * 启动一局游戏，给队列发送一个游戏开始的事件，后续通过{@link #next()}进行控制
     *
     * @throws IllegalStateException 若游戏已开始，则抛出该异常
     */
    @Override
    public void startGame() {
        eventQueue.addNormal(CommonSystemEventPool.get(GameEventCode.GAME_START));
    }

    /**
     * 判定游戏是否结束
     */
    @Override
    public boolean isGameEnd() {
        return gameCore.getGameState() == GameState.GAME_UN_START;
    }

    /**
     * 执行游戏的下一步，执行前需要调用{@link #isGameEnd()}进行游戏结束判定
     */
    @Override
    public GameEvent next() {
        GameEvent gameEvent = eventQueue.next();
        IGameEventHandler gameEventHandler = gameEventHandlerManager.get(gameEvent);
        Assert.notNull(gameEventHandler, "未找到事件对应的处理器，请检查游戏创建器：" + gameEvent);
        gameEventHandler.handle(gameEvent, gameCore, gameConfig, eventQueue);
        return gameEvent;
    }


}