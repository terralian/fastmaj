package com.github.terralian.fastmaj.game;

import com.github.terralian.fastmaj.agari.AgariCalculator;
import com.github.terralian.fastmaj.agari.FuCalculator;
import com.github.terralian.fastmaj.agari.IAgariCalculator;
import com.github.terralian.fastmaj.agari.IFuCalculator;
import com.github.terralian.fastmaj.agari.IPointCalculator;
import com.github.terralian.fastmaj.agari.IPointCalculatorManager;
import com.github.terralian.fastmaj.agari.IRonYakuMatcher;
import com.github.terralian.fastmaj.agari.ITehaiAgariDivider;
import com.github.terralian.fastmaj.agari.PointCalculator;
import com.github.terralian.fastmaj.agari.PointCalculatorManager;
import com.github.terralian.fastmaj.agari.RonYakuMatcher;
import com.github.terralian.fastmaj.game.log.ChainGameLoggerBuilder;
import com.github.terralian.fastmaj.game.log.IGameLogger;
import com.github.terralian.fastmaj.game.ryuuky.IRyuukyokuResolverManager;
import com.github.terralian.fastmaj.game.ryuuky.RyuukyokuResolverManager;
import com.github.terralian.fastmaj.game.validator.GameEndValidator;
import com.github.terralian.fastmaj.tehai.FastSyatenCalculator;
import com.github.terralian.fastmaj.tehai.ISyatenCalculator;
import com.github.terralian.fastmaj.tehai.IYuukouhaiCalculator;
import com.github.terralian.fastmaj.tehai.YuukouhaiCalculator;
import com.github.terralian.fastmaj.third.mjscore.MjscoreAdapter;
import com.github.terralian.fastmaj.yaku.IYakuMatcher;
import com.github.terralian.fastmaj.yaku.YakuMatcher;
import com.github.terralian.fastmaj.yama.IYamaWorker;
import com.github.terralian.fastmaj.yama.worker.tenhou.TenhouYamaWorker;
import lombok.Getter;

/**
 * 游戏运行必要的组件
 *
 * @author terra.lian
 * @since 2023-04-23
 */
@Getter
public class GameComponent {

    // ----------------------------------------
    // 计算器
    // ----------------------------------------

    /**
     * 向听计算器
     */
    private ISyatenCalculator syatenCalculator;
    /**
     * 役种匹配器
     */
    private IYakuMatcher yakuMatcher;
    /**
     * 和了手牌分割器
     */
    private ITehaiAgariDivider agariDivider;
    /**
     * 有效牌计算器
     */
    private IYuukouhaiCalculator yuukouhaiCalculator;
    /**
     * 荣和计算器
     */
    private IRonYakuMatcher ronYakuMatcher;
    /**
     * 符计算器
     */
    private IFuCalculator fuCalculator;
    /**
     * 分数计算器
     */
    private IPointCalculator pointCalculator;
    /**
     * 分数计算管理器
     */
    private IPointCalculatorManager pointCalculatorManager;
    /**
     * 和了计算器
     */
    private IAgariCalculator agariCalculator;

    // ----------------------------------------
    // 判定器 - 管理器 - 其他组件
    // ----------------------------------------
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
     * 日志构建器
     */
    private final ChainGameLoggerBuilder chainGameLoggerBuilder;
    /**
     * 牌山生成器
     */
    private IYamaWorker yamaWorker;

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
        // 先构建计算器
        this.syatenCalculator = new FastSyatenCalculator();
        this.yakuMatcher = new YakuMatcher();
        this.agariDivider = new MjscoreAdapter();
        this.fuCalculator = new FuCalculator();
        this.pointCalculator = new PointCalculator();
        this.pointCalculatorManager = new PointCalculatorManager(pointCalculator);
        this.yuukouhaiCalculator = new YuukouhaiCalculator(this.syatenCalculator);
        this.ronYakuMatcher = new RonYakuMatcher(this.yakuMatcher, this.agariDivider);
        this.agariCalculator = new AgariCalculator(this.yakuMatcher, this.agariDivider, this.fuCalculator,
                this.pointCalculatorManager);

        // 再构建其他组件
        this.gameEndValidator = new GameEndValidator();
        this.ryuukyokuResolverManager = RyuukyokuResolverManager.defaultManager();
        this.playerActionManager = PlayerActionManager.defaultManager(this);
    }

    private GameComponent() {
        chainGameLoggerBuilder = ChainGameLoggerBuilder.newBuilder();
    }
}
