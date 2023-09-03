package com.github.terralian.fastmaj.game;

import com.github.terralian.fastmaj.game.event.ActionEvent;
import com.github.terralian.fastmaj.game.event.GameEvent;
import com.github.terralian.fastmaj.game.event.IGameEventHandler;
import com.github.terralian.fastmaj.game.event.system.GameStartEvent;
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
     * 启动一局游戏，给队列发送一个游戏开始的事件，后续通过{@link #nextEvent()}进行控制
     *
     * @throws IllegalStateException 若游戏已开始，则抛出该异常
     */
    @Override
    public void startGame() {
        eventQueue.addNormal(new GameStartEvent());
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
    public GameEvent nextEvent() {
        GameEvent gameEvent = eventQueue.next();
        // 缓存上一个动作
        if (gameEvent instanceof ActionEvent) {
            ActionEvent actionEvent = (ActionEvent) gameEvent;
            gameCore.getPlayerSpaceManager().getDefaultSpace(actionEvent.getPosition()).addAction(actionEvent);
        }
        IGameEventHandler gameEventHandler = gameEventHandlerManager.get(gameEvent);
        Assert.notNull(gameEventHandler, "未找到事件对应的处理器，请检查游戏创建器：" + gameEvent);
        gameEventHandler.handle(gameEvent, gameCore, gameConfig, eventQueue);
        return gameEvent;
    }

    /**
     * 执行到下一个动作事件，跳过系统事件，并返回被执行的事件
     */
    @Override
    public ActionEvent nextActionEvent() {
        while (true) {
            GameEvent gameEvent = nextEvent();
            if (gameEvent instanceof ActionEvent) {
                return (ActionEvent) gameEvent;
            }
        }
    }
}
