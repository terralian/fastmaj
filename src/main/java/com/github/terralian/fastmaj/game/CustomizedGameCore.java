package com.github.terralian.fastmaj.game;

import java.util.List;

import com.github.terralian.fastmaj.game.log.IGameLogger;
import com.github.terralian.fastmaj.player.IPlayer;
import com.github.terralian.fastmaj.tehai.ISyatenCalculator;
import com.github.terralian.fastmaj.yama.IYamaWorker;
import com.github.terralian.fastmaj.yama.Yama;

/**
 * 可定制的游戏内核，一个例子为：在实际对局中遇到了一个难以判断的难题，现在要看AI会在这方面如何选择，比如南三局1本场，当前庄家为坐席3，牌山为如何如何...
 * <p>
 * 若使用{@link GameCore}实现的话，只能使用完整牌谱信息，从东一局开始执行完整个牌谱，这个过程不仅损耗性能，而且如何处理玩家接口{@link IPlayer}
 * 也是一个难题，于是有了该实现。
 * <p/>
 * 该实现提供了对直接开始指定对局的支持，可通过{@link #nextKyoku(int[])}来设置牌山，并通过调用对应属性的Setter方法设置如场风，点数等信息。
 *
 * @author Terra.Lian
 */
public class CustomizedGameCore extends GameCore {

    /**
     * 当前牌山数组keeper
     */
    private int[] currentYama;

    /**
     * 初始化构建游戏内核
     *
     * @param players 玩家集合（可选）
     * @param gameConfig 游戏规则
     * @param yamaWorker 牌山生成器
     * @param syatenCalculator 向听计算器
     * @param gameLogger 日志处理器
     */
    public CustomizedGameCore(List<IPlayer> players, GameConfig gameConfig, IYamaWorker yamaWorker,
            ISyatenCalculator syatenCalculator, IGameLogger gameLogger) {
        super(players, gameConfig, yamaWorker, syatenCalculator, gameLogger);
    }

    /**
     * 指定牌山开始下一对局
     *
     * @param newYamaArray 牌山数组
     */
    public int nextKyoku(int[] newYamaArray) {
        this.currentYama = newYamaArray;
        return nextKyoku();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void nextYama() {
        yama = new Yama(currentYama, gameConfig.isUseRedHai(), playerSize);
    }

}
