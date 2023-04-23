package com.github.terralian.fastmaj.game;

import com.github.terralian.fastmaj.game.log.ChainGameLoggerBuilder;
import com.github.terralian.fastmaj.game.log.IGameLogger;
import com.github.terralian.fastmaj.game.ryuuky.IRyuukyokuResolverManager;
import com.github.terralian.fastmaj.game.ryuuky.RyuukyokuResolverManager;
import com.github.terralian.fastmaj.game.validator.GameEndValidator;
import com.github.terralian.fastmaj.tehai.FastSyatenCalculator;
import com.github.terralian.fastmaj.tehai.ISyatenCalculator;
import com.github.terralian.fastmaj.yama.IYamaWorker;
import com.github.terralian.fastmaj.yama.TenhouYamaWorker;
import lombok.Getter;

/**
 * 游戏运行必要的组件
 *
 * @author terra.lian
 * @since 2023-04-23
 */
@Getter
public class GameComponent {

    /**
     * 游戏结束判定器
     */
    private GameEndValidator gameEndValidator;
    /**
     * 流局可执行事件判定器
     */
    private IRyuukyokuResolverManager ryuukyokuResolverManager;
    /**
     * 玩家动作管理器
     */
    private IPlayerActionManager playerActionManager;
    /**
     * 向听计算器
     */
    private ISyatenCalculator syatenCalculator;

    /**
     * 牌山生成器
     */
    private IYamaWorker yamaWorker;

    /**
     * 日志构建器
     */
    private final ChainGameLoggerBuilder chainGameLoggerBuilder;

    /**
     * 使用天凤的组件配置
     */
    public static GameComponent useTenhou() {
        return useTenhou(null);
    }

    /**
     * 使用天凤的组件配置
     *
     * @param yamaSeed 牌山种子
     */
    public static GameComponent useTenhou(String yamaSeed) {
        GameComponent gameComponent = new GameComponent();
        gameComponent.useCommonDefault();
        gameComponent.yamaWorker = new TenhouYamaWorker(yamaSeed);

        return gameComponent;
    }

    /**
     * 设置牌山种子
     *
     * @param seed 牌山种子
     */
    public GameComponent setYamaSeed(String seed) {
        this.yamaWorker.setSeed(seed);
        return this;
    }

    /**
     * 增加游戏日志打印
     *
     * @param gameLogger 日志打印
     */
    public GameComponent addLogger(IGameLogger gameLogger) {
        this.chainGameLoggerBuilder.addLogger(gameLogger);
        return this;
    }

    /**
     * 通用的默认组件
     */
    private void useCommonDefault() {
        this.gameEndValidator = new GameEndValidator();
        this.ryuukyokuResolverManager = RyuukyokuResolverManager.defaultManager();
        this.playerActionManager = PlayerActionManager.defaultManager();
        this.syatenCalculator = new FastSyatenCalculator();
    }

    private GameComponent() {
        chainGameLoggerBuilder = ChainGameLoggerBuilder.newBuilder();
    }
}
