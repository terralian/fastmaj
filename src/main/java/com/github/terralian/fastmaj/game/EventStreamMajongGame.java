package com.github.terralian.fastmaj.game;

import java.util.List;

import com.github.terralian.fastmaj.game.log.IGameLogger;
import com.github.terralian.fastmaj.player.IPlayer;

/**
 * @author Terra.Lian
 */
public class EventStreamMajongGame extends EventMajongGame {

    public EventStreamMajongGame(GameConfig gameConfig, IGameCore gameCore, GameComponent gameComponent,
            IGameEventQueue gameEventQueue) {
        super(gameConfig, gameCore, gameComponent, gameEventQueue);
    }

    /**
     * 根据完整信息构建一个流执行游戏
     *
     * @param players 玩家集合
     * @param gameConfig 游戏配置
     * @param gameComponent 游戏组件
     */
    @SuppressWarnings("unchecked")
    public EventStreamMajongGame(List<? extends IPlayer> players, GameConfig gameConfig, GameComponent gameComponent) {
        this(gameConfig, null, gameComponent, new GameEventQueue());
        IGameLogger logger = gameComponent.getChainGameLoggerBuilder().build();
        this.gameCore = new GameCore((List<IPlayer>) players, gameConfig, gameComponent.getYamaWorker(),
                gameComponent.getSyatenCalculator(), logger);
    }

    @Override
    public void startGame() {
        super.startGame();
        do {
            next();
        } while (!isGameEnd());
    }
}
