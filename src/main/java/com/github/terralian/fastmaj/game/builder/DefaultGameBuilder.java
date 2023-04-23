package com.github.terralian.fastmaj.game.builder;

import java.util.List;

import com.github.terralian.fastmaj.FastMajong;
import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.GameCore;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.IPlayerActionManager;
import com.github.terralian.fastmaj.game.PlayerActionManager;
import com.github.terralian.fastmaj.game.StreamMajongGame;
import com.github.terralian.fastmaj.game.action.DrawAction;
import com.github.terralian.fastmaj.game.log.ChainGameLoggerBuilder;
import com.github.terralian.fastmaj.game.log.IGameLogger;
import com.github.terralian.fastmaj.game.ryuuky.IRyuukyokuResolverManager;
import com.github.terralian.fastmaj.game.ryuuky.RyuukyokuResolverManager;
import com.github.terralian.fastmaj.game.validator.GameEndValidator;
import com.github.terralian.fastmaj.player.IPlayer;
import com.github.terralian.fastmaj.tehai.ISyatenCalculator;
import com.github.terralian.fastmaj.util.EmptyUtil;
import com.github.terralian.fastmaj.yama.IYamaWorker;
import com.github.terralian.fastmaj.yama.SimpleRandomYamaWorker;
import com.github.terralian.fastmaj.yama.seed.random.IdWorker;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 默认的游戏构建器，除{@link IPlayer}外，其他都有默认配置
 * 
 * @author terra.lian
 */
@Setter
@Getter
@Accessors(chain = true)
public class DefaultGameBuilder {
    /**
     * 游戏配置
     */
    private GameConfig config = GameConfig.defaultRule();
    /**
     * 游戏核心
     */
    private IGameCore gameCore;
    /**
     * 摸牌动作
     */
    private DrawAction drawAction = new DrawAction();
    /**
     * 游戏结束判定器
     */
    private GameEndValidator gameEndValidator = new GameEndValidator();
    /**
     * 流局可执行事件判定器
     */
    private IRyuukyokuResolverManager ryuukyokuResolverManager = RyuukyokuResolverManager.defaultManager();
    /**
     * 玩家动作管理器
     */
    private IPlayerActionManager playerActionManager = PlayerActionManager.defaultManager();
    /**
     * 牌山生成器
     */
    private IYamaWorker yamaWorker = new SimpleRandomYamaWorker(IdWorker.getId());
    /**
     * 向听计算器
     */
    private ISyatenCalculator syatenCalculator = FastMajong.doGetSyatenCalculator();
    /**
     * 链式构建器
     */
    private ChainGameLoggerBuilder chainGameLoggerBuilder = ChainGameLoggerBuilder.newBuilder();

    /**
     * 构建{@link StreamMajongGame}
     * 
     * @param players 玩家接口
     */
    @SuppressWarnings("unchecked")
    public StreamMajongGame build(List<? extends IPlayer> players) {
        if (EmptyUtil.isEmpty(players)) {
            throw new IllegalArgumentException("需要传入玩家实例来处理游戏事件");
        }
        if(gameCore == null) {
            gameCore = new GameCore((List<IPlayer>) players,
                    config, //
                    yamaWorker, //
                    syatenCalculator, //
                    chainGameLoggerBuilder.build() //
            );
        }
        StreamMajongGame majongGame = new StreamMajongGame();
        majongGame.setGameCore(gameCore);
        majongGame.setConfig(config);
        majongGame.setPlayerActionManager(playerActionManager);
        majongGame.setRyuukyokuResolverManager(ryuukyokuResolverManager);

        return majongGame;
    }

    /**
     * 增加日志处理器
     * 
     * @param gameLogger 日志处理器
     */
    public DefaultGameBuilder addGameLogger(IGameLogger gameLogger) {
        chainGameLoggerBuilder.addLogger(gameLogger);
        return this;
    }
}
