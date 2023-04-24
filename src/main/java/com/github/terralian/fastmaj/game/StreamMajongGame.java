package com.github.terralian.fastmaj.game;

import java.util.List;

import com.github.terralian.fastmaj.game.log.IGameLogger;
import com.github.terralian.fastmaj.player.IPlayer;

/**
 * 流式执行游戏，一旦开始游戏就会执行到结束
 *
 * @author terra.lian
 * @see StepMajongGame
 */
public class StreamMajongGame extends StepMajongGame {

    private GameComponent gameComponent;

    public StreamMajongGame() {

    }

    /**
     * 通过游戏配置和游戏组件构建一个流执行游戏，玩家暂时不设置
     *
     * @param gameConfig 游戏配置
     * @param gameComponent 游戏组件
     */
    public StreamMajongGame(GameConfig gameConfig, GameComponent gameComponent) {
        this(null, gameConfig, gameComponent);
    }

    /**
     * 根据完整信息构建一个流执行游戏
     *
     * @param players 玩家集合
     * @param gameConfig 游戏配置
     * @param gameComponent 游戏组件
     */
    @SuppressWarnings("unchecked")
    public StreamMajongGame(List<? extends IPlayer> players, GameConfig gameConfig, GameComponent gameComponent) {
        this.config = gameConfig;
        this.gameComponent = gameComponent;
        this.gameEndValidator = gameComponent.getGameEndValidator();
        this.ryuukyokuResolverManager = gameComponent.getRyuukyokuResolverManager();
        this.playerActionManager = gameComponent.getPlayerActionManager();

        IGameLogger logger = gameComponent.getChainGameLoggerBuilder().build();
        this.gameCore = new GameCore((List<IPlayer>) players, gameConfig, gameComponent.getYamaWorker(),
                gameComponent.getSyatenCalculator(), logger);
    }

    /**
     * 设置牌山种子
     *
     * @param seed 牌山种子
     */
    public StreamMajongGame setYamaSeed(String seed) {
        gameComponent.getYamaWorker().setSeed(seed);
        return this;
    }

    /**
     * 设置玩家集合
     *
     * @param players 玩家集合
     */
    public StreamMajongGame setPlayers(List<IPlayer> players) {
        gameCore.setPlayers(players);
        return this;
    }

    /**
     * 开始游戏
     */
    public void startGame() {
        // 初始化游戏
        super.startGame();
        // 判断游戏是否结束，未结束则进入下一局
        while (true) {
            // 开始对局
            // 校验对局是否结束，是的话结束游戏
            if (nextKyoku() == GameRunningState.END) {
                break;
            }
        }
    }

    @Override
    public GameRunningState nextKyoku() {
        if (super.nextKyoku() == GameRunningState.END) {
            return GameRunningState.END;
        }

        // 当牌山还有剩余牌
        // 上一轮牌河处理动作不是吃碰时，摸一枚牌
        // 摸牌前，若上一轮玩家动作不是暗杠等，则切换到下一位玩家来摸牌
        while (nextDraw() == KyokuState.CONTINUE) {

            // 调用玩家接口获取操作，并执行。
            // 仅当九种九牌时，跳出当前对局
            if (nextTehaiAction() == KyokuState.END) {
                break;
            }

            // 对手牌河动作处理，如吃碰杠及荣和操作
            // 荣和情况下，结束当前对局
            if (nextRiverAction() == KyokuState.END) {
                break;
            }
            // 牌河动作后的规则可选时点，可以对立直步骤2进行处理，新宝牌处理
            optionAfterRiverAction();

            // 流局判定
            if (resolverRyuukyoku() == KyokuState.END) {
                break;
            }
        }
        return GameRunningState.CONTINUE;
    }
}
