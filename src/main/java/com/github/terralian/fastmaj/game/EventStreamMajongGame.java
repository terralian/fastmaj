package com.github.terralian.fastmaj.game;

import java.util.List;

import com.github.terralian.fastmaj.game.log.IGameLogger;
import com.github.terralian.fastmaj.player.IPlayer;
import com.github.terralian.fastmaj.player.space.IPlayerSpaceManager;
import com.github.terralian.fastmaj.player.space.PlayerSpaceManager;

/**
 * 以事件驱动的流式麻将游戏，当调用{@link #startGame()}后，将会自动进行至游戏结束
 *
 * @author terra.lian
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
    public EventStreamMajongGame(List<? extends IPlayer> players, GameConfig gameConfig, GameComponent gameComponent) {
        this(gameConfig, null, gameComponent, new GameEventQueue());
        IGameLogger logger = gameComponent.getChainGameLoggerBuilder().build();
        IPlayerSpaceManager playerSpaceManager = new PlayerSpaceManager(players);
        this.gameCore = new GameCore(playerSpaceManager, gameConfig, gameComponent.getYamaWorker(),
                gameComponent.getSyatenCalculator(), logger);
    }

    @Override
    public void startGame() {
        super.startGame();
        do {
            nextEvent();
        } while (!isGameEnd());
    }
}
