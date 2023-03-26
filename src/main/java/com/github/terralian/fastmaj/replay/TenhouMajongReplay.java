package com.github.terralian.fastmaj.replay;

import java.util.ArrayList;
import java.util.List;

import com.github.terralian.fastmaj.FastMajong;
import com.github.terralian.fastmaj.encode.Encode136;
import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.GameCore;
import com.github.terralian.fastmaj.game.KazeEnum;
import com.github.terralian.fastmaj.game.PlayerActionManager;
import com.github.terralian.fastmaj.game.StepMajongGame;
import com.github.terralian.fastmaj.game.log.IGameLogger;
import com.github.terralian.fastmaj.game.log.PrintGameLogger;
import com.github.terralian.fastmaj.game.ryuuky.RyuukyokuResolverManager;
import com.github.terralian.fastmaj.hai.HaiPool;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.paifu.IPaifuParser;
import com.github.terralian.fastmaj.paifu.domain.PaifuGame;
import com.github.terralian.fastmaj.paifu.tenhou.ITenhouPaifuParseHandler;
import com.github.terralian.fastmaj.paifu.tenhou.TenhouPaifuParser;
import com.github.terralian.fastmaj.paifu.tenhou.TenhouPaifuStringPool;
import com.github.terralian.fastmaj.player.RiverActionCall;
import com.github.terralian.fastmaj.player.TehaiActionCall;
import com.github.terralian.fastmaj.yama.VirtualYamaWorker;

/**
 * 天凤牌谱游戏回放
 * 
 * @author terra.lian
 */
public class TenhouMajongReplay extends StepMajongGame implements ITenhouPaifuParseHandler {

    /**
     * 牌谱解析器
     */
    protected IPaifuParser<PaifuGame> paifuParser;
    /**
     * 模拟牌山生成器
     */
    protected VirtualYamaWorker yamaWorker;

    /**
     * 游戏日志
     */
    protected IGameLogger gameLogger;
    /**
     * 配置
     */
    protected GameConfig config;

    /**
     * 是否使用红宝牌
     */
    protected boolean useRed = true;
    /**
     * 是否立直步骤1
     */
    protected boolean reachStep1 = false;

    public TenhouMajongReplay() {
        this(GameConfig.defaultRule(), new PrintGameLogger());
    }

    /**
     * 初始化构建{@link TenhouMajongReplay}
     * 
     * @param gameConfig 游戏配置
     * @param gameLogger 游戏日志处理器
     */
    public TenhouMajongReplay(GameConfig gameConfig, IGameLogger gameLogger) {
        paifuParser = new TenhouPaifuParser(this);
        this.config = gameConfig;
        this.gameLogger = gameLogger;
    }

    // --------------------------------------------------------
    // 解析方法
    // --------------------------------------------------------

    /**
     * 获取牌谱解析器
     */
    public IPaifuParser<PaifuGame> getPaifuParser() {
        return paifuParser;
    }

    /**
     * 获取种子
     */
    public String getSeed() {
        return yamaWorker.getSeed();
    }

    // --------------------------------------------------------
    // 分析方法
    // --------------------------------------------------------

    /**
     * 为虚拟牌山增加牌谱种子
     */
    @Override
    public void shuffleMeta(String seed) {
        yamaWorker = new VirtualYamaWorker();
        yamaWorker.setSeed(seed);
    }

    /**
     * 开始游戏，执行游戏初始化
     */
    @Override
    public void startGame(boolean isSanma, int taku, boolean isTonnan, boolean isSoku, boolean isUseAka, boolean isAriAri,
            String[] playerNames, int[] playerRates, String[] playerDans) {
        int playerSize = isSanma ? 3 : 4;
        config.setPlayerSize(playerSize);
        config.setEndBakaze(isTonnan ? KazeEnum.NAN : KazeEnum.DON);
        super.gameCore = new GameCore(playerSize, //
                new ArrayList<>(), //
                config, //
                yamaWorker, //
                FastMajong.doGetSyatenCalculator(), //
                gameLogger);
        setConfig(config);
        setPlayerActionManager(PlayerActionManager.defaultManager());
        setRyuukyokuResolverManager(RyuukyokuResolverManager.defaultManager());
        super.startGame();
    }

    /**
     * 结束游戏动作
     */
    @Override
    public void endGame(int[] playerPoints, int[] playerScores) {
        // 一般情况下若没有系统问题，这里两边的分数应当是相等的
        // 所以为了暴露出系统的缺陷，这里使用本身的分数
        super.endGame(gameCore.getPlayerPoints());
    }

    /**
     * 开始对局动作，为虚拟牌山生成器设置牌山，初始化对局
     */
    @Override
    public void startKyoku(int[] playerPoints, List<List<Integer>> playerHaipais, int oya, int bakaze, int kyoku, int honba, int kyotaku,
            int firstDoraDisplay, int[] yama) {
        // 设置牌山
        yamaWorker.setYama(yama);
        super.nextKyoku();
    }

    @Override
    public void endKyoku(int[] playerPoints) {
        gameCore.endKyoku();
    }

    /**
     * 摸牌事件
     */
    @Override
    public void draw(int position, int tsumoHai) {
        super.nextDraw();
    }

    /**
     * 弃牌事件，若先前发生了立直事件1，则弃牌作为立直处理。否则为普通弃牌
     */
    @Override
    public void kiri(int position, int kiriHai) {
        IHai hai = HaiPool.getById(kiriHai, useRed);
        // 立直
        if (reachStep1) {
            reachStep1 = false;
            TehaiActionCall actionCall = TehaiActionCall.newReach(hai);
            super.nextTehaiAction(actionCall);
        } else {
            // 非立直
            TehaiActionCall actionCall = TehaiActionCall.newKiri(hai);
            super.nextTehaiAction(actionCall);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void chi(int position, int from, int[] selfHai, int nakiHai) {
        IHai[] selfHais = Encode136.toHais(selfHai, useRed);
        RiverActionCall actionCall = RiverActionCall.newChii(selfHais);
        super.nextRiverAction(position, actionCall);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pon(int position, int from, int[] selfHai, int nakiHai) {
        IHai[] selfHais = Encode136.toHais(selfHai, useRed);
        RiverActionCall actionCall = RiverActionCall.newPon(selfHais);
        super.nextRiverAction(position, actionCall);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void minkan(int position, int from, int[] selfHai, int nakiHai) {
        IHai[] selfHais = Encode136.toHais(selfHai, useRed);
        RiverActionCall actionCall = RiverActionCall.newMinkan(selfHais);
        super.nextRiverAction(position, actionCall);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void annkan(int position, int[] selfHai) {
        TehaiActionCall actionCall = TehaiActionCall.newAnnkan(HaiPool.getById(selfHai[0], useRed));
        super.nextTehaiAction(actionCall);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void kakan(int position, int from, int[] selfHai, int nakiHai, int addHai) {
        TehaiActionCall actionCall = TehaiActionCall.newKakan(HaiPool.getById(addHai, useRed));
        super.nextTehaiAction(actionCall);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void kita(int position) {
        TehaiActionCall actionCall = TehaiActionCall.newKita(HaiPool.getByValue(Encode34.BEI));
        super.nextTehaiAction(actionCall);
    }

    /**
     * 立直步骤1，立直宣言，对于这里来讲立直宣言和弃牌是同一个动作，这里设置立直标识
     */
    @Override
    public void reach1(int position) {
        reachStep1 = true;
    }

    /**
     * 立直步骤2，放置立直棒，扣减分数
     */
    @Override
    public void reach2(int position, int[] playerPoints) {
        reach2();
    }

    /**
     * 和了，包含荣和和自摸处理
     */
    @Override
    public void agari(int position, int from, List<String> yaku, int han, int hu, int score, int[] increaseAndDecrease) {
        if (position != from) {
            RiverActionCall actionCall = RiverActionCall.RON;
            super.nextRiverAction(position, actionCall);
        } else {
            TehaiActionCall actionCall = TehaiActionCall.newTsumo();
            super.nextTehaiAction(actionCall);
        }
    }

    /**
     * 流局，包含九种九牌和其他流局
     */
    @Override
    public void ryuukyoku(int[] increaseAndDecrease, String type) {
        if (TenhouPaifuStringPool.RYUUKYOKU_TYPE_99.equals(type)) {
            TehaiActionCall actionCall = TehaiActionCall.newRyuukyoku();
            super.nextTehaiAction(actionCall);
        } else if (TenhouPaifuStringPool.RYUUKYOKU_TYPE_RON_3.equals(type)) {
            ron3RyuukyokuResolver.execute(config, gameCore);
        } else {
            resolverRyuukyoku();
        }
    }

    /**
     * 获取日志处理器
     */
    public IGameLogger getGameLogger() {
        return gameLogger;
    }

    /**
     * 设置日志处理器
     * 
     * @param gameLogger 日志处理器
     */
    public void setGameLogger(IGameLogger gameLogger) {
        this.gameLogger = gameLogger;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PaifuGame getParseData() {
        return new PaifuGame();
    }
}
