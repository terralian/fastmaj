package com.github.terralian.fastmaj.game;

import java.util.List;
import java.util.stream.Collectors;

import com.github.terralian.fastmaj.game.log.IGameLogger;
import com.github.terralian.fastmaj.game.ryuuky.IRyuukyoku;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.player.IPlayer;
import com.github.terralian.fastmaj.player.RivalEnum;
import com.github.terralian.fastmaj.player.space.IPlayerSpaceManager;
import com.github.terralian.fastmaj.player.space.PlayerDefaultSpace;
import com.github.terralian.fastmaj.player.space.PlayerPublicSpace;
import com.github.terralian.fastmaj.river.HaiRiver;
import com.github.terralian.fastmaj.river.IHaiRiver;
import com.github.terralian.fastmaj.tehai.ISyatenCalculator;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.tehai.NakiEnum;
import com.github.terralian.fastmaj.util.Assert;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yama.DrawFrom;
import com.github.terralian.fastmaj.yama.IYama;
import com.github.terralian.fastmaj.yama.IYamaArray;
import com.github.terralian.fastmaj.yama.IYamaWorker;
import com.github.terralian.fastmaj.yama.Yama;
import com.github.terralian.fastmaj.yama.worker.common.SimpleRandomYamaWorker;

/**
 * 默认的游戏核心实现，该核心设计为通过调用内部定义方法，从东一局开始到游戏结束，自动初始化所需信息（牌山、手牌、牌河...）,
 * 变更内其部状态。在对局过程中，未提供可直接修改内核状态的方法，这种设计可保证运行状态的连贯性和一致性。
 *
 * @author terra.lian
 */
public class GameCore implements IGameCore {
    /**
     * 玩家人数
     */
    protected final int playerSize;
    /**
     * 游戏规则
     */
    protected final GameConfig gameConfig;

    /**
     * 玩家空间管理器
     */
    protected IPlayerSpaceManager playerSpaceManager;
    /**
     * 牌山生成器，有值时使用指定的牌山生成器。若未指定，则使用{@link SimpleRandomYamaWorker}
     * <p/>
     * 若需要重复曾经的牌谱牌山，则自定义牌山生成器是关键之一
     */
    protected IYamaWorker yamaWorker;
    /**
     * 向听计算器
     */
    protected ISyatenCalculator syatenCalculator;

    // -------------------------------------
    // 内部状态
    // -------------------------------------

    /**
     * 游戏状态
     */
    private GameState gameState = GameState.GAME_UN_START;

    /**
     * 当前的牌山
     */
    protected IYama yama;
    /**
     * 当局场风
     */
    protected KazeEnum bakaze;
    /**
     * 当前庄家
     */
    protected int oya;
    /**
     * 当前对局数（依照局数本场数，递增）
     */
    protected int round;
    /**
     * 动作数
     */
    private int actionCount;
    /**
     * 本场数，0为实际值，1为展示值，2为是否清空标识（0为清空）
     */
    protected int[] honba;
    /**
     * 供托（立直棒）
     */
    protected Integer kyotaku;
    /**
     * 最后操作的玩家坐席
     */
    protected int lastPlayerPosition;
    /**
     * 是否连庄
     */
    private boolean renchan;
    /**
     * 对局结束类型
     */
    private KyokuEndEnum kyokuEndEnum;

    /**
     * 日志处理器，TODO 日志框架感觉需要和核心拆分，在外部处理
     */
    private IGameLogger gameLogger;

    /**
     * 初始化构建游戏内核
     *
     * @param playerSpaceManager 玩家空间管理器
     * @param gameConfig 游戏规则
     * @param yamaWorker 牌山生成器
     * @param syatenCalculator 向听计算器
     * @param gameLogger 日志处理器
     */
    public GameCore(IPlayerSpaceManager playerSpaceManager, GameConfig gameConfig, IYamaWorker yamaWorker,
            ISyatenCalculator syatenCalculator, IGameLogger gameLogger) {
        this.playerSize = gameConfig.getPlayerSize();
        this.playerSpaceManager = playerSpaceManager;
        this.gameConfig = gameConfig;
        this.yamaWorker = yamaWorker;
        this.syatenCalculator = syatenCalculator;
        this.gameLogger = gameLogger;
    }

    /**
     * 游戏开始
     * <p/>
     * 该方法将会初始化内部状态。
     */
    @Override
    public void startGame() {
        gameState.requireGameUnStart();
        gameState = GameState.GAME_NEW;

        // 牌山
        yama = null;
        // 初始场风
        bakaze = KazeEnum.DON;
        // 初始庄家为下标为0的玩家
        oya = -1;
        // 庄家是否连庄，默认为false
        renchan = false;
        // 本场数
        honba = new int[3];
        // 供托
        kyotaku = 0;
        // 回合数，从-1开始
        round = -1;
        // 无动作
        actionCount = 0;

        playerSpaceManager.resetGameState();
        for (int i = 0; i < playerSize; i++) {
            PlayerDefaultSpace playerSpace = playerSpaceManager.getDefaultSpace(i);
            playerSpace.setPlayerPoint(gameConfig.getStartPoint());
            playerSpace.setHaiRiver(new HaiRiver(i));
        }
        // 初始化对局状态
        resetKyokuStatus();
        // 日志处理
        List<IPlayer> players = playerSpaceManager.getDefaultSpaces()
                .stream()
                .map(PlayerDefaultSpace::getSelf)
                .collect(Collectors.toList());
        gameLogger.gameStart(players, this);
    }

    /**
     * 游戏结束
     * <p/>
     * 调用该方法后，将会标记游戏为结束状态，其他游戏运行时方法将会变成非法操作
     * <p/>
     * 结束对局后将完成日志的构建
     */
    @Override
    public void endGame(int[] increaseAndDecrease) {
        gameState.requireGameStarted();
        gameState = GameState.GAME_UN_START;
        playerSpaceManager.setState(increaseAndDecrease, PlayerPublicSpace::setPlayerPoint);
        // 日志处理
        gameLogger.gameEnd();
    }

    @Override
    public int nextKyoku() {
        gameState.requireKyokuUnStart();
        gameState = GameState.KYOKU_NEW;

        // 对局数增加
        round += 1;
        // 非第一局时，连庄或者下一玩家坐庄
        if (round >= 0 && !renchan) {
            oya++;
            // 当前庄家已经超过人数时，需要切换到下一位庄家
            if (oya >= playerSize) {
                oya = 0;
                bakaze = KazeEnum.next(bakaze);
            }
        }
        // 本场数重置
        honba[0] = honba[1] = honba[2] == 0 ? 0 : honba[1] + 1;
        honba[2] = 0;
        // 庄家是最早出牌人
        lastPlayerPosition = oya;
        // 其他状态重置
        resetKyokuStatus();
        // 初始化牌山
        nextYama();
        // 初始化手牌和牌河
        List<ITehai> tehais = yama.deals(oya);
        playerSpaceManager.setState(tehais, PlayerDefaultSpace::setTehai);
        // 重算向听
        for (int i = 0; i < playerSize; i++) {
            calcPlayerSyaten(i);
        }
        // 日志处理
        gameLogger.kyokuStart(round, bakaze, oya, honba[1], tehais);

        // 返回当前对局数
        return round;
    }

    /**
     * 创建下一个牌山
     */
    protected void nextYama() {
        IYamaArray yamaArray = yamaWorker.getNextYama(1);
        yama = new Yama(yamaArray, gameConfig.isUseRedHai(), playerSize);
    }

    @Override
    public void endKyoku() {
        gameState = GameState.KYOKU_END;
        // 对局结束
        gameLogger.kyokuEnd(round, getPlayerPoints());

        Assert.notNull(kyokuEndEnum, "状态错误，当前对局结束类型不能为空，请确认是否未调用流局或者和了方法");
    }

    /**
     * 重置对局状态
     */
    private void resetKyokuStatus() {
        // 重置对局状态
        playerSpaceManager.resetKyokuState();
        // 是否连庄设置为false
        renchan = false;
        // 动作数设置0
        actionCount = 0;
        // 清空对局结束类型
        kyokuEndEnum = null;
    }

    /**
     * 当前玩家摸一枚手牌
     * <p/>
     * 可以从牌山或者王牌区摸一张牌，若从王牌区摸牌，<b>并不会自动增加新的宝牌</b>，这是由于不同规则新宝牌的时机并不一致。
     * <p/>
     * 当需要增加新宝牌时，可以调用{@link #nextDoraDisplay()}
     *
     * @param position 玩家坐席
     * @param drawFrom true从牌山，false从王牌区
     */
    @Override
    public IHai draw(int position, DrawFrom drawFrom) {
        gameState.requireForDraw();
        gameState = GameState.WAIT_TEHAI_ACTION;

        ITehai tehai = getTehai(position);
        IHai hai = null;
        switch (drawFrom) {
            case YAMA:
                hai = yama.nextHai();
                break;
            case RINSYAN:
                hai = yama.nextTrumpHai();
                break;
        }
        tehai.draw(hai);

        // 重算向听
        calcCurrentPlayerSyaten(position);
        // 日志处理
        gameLogger.draw(position, hai, drawFrom);
        // 动作数增加
        actionCount++;
        // 最后操作的玩家记录
        lastPlayerPosition = position;

        return hai;
    }

    /**
     * 当前玩家切一张手牌
     *
     * @param hai 要切的牌，通过id比较
     * @return 是否手切（true手切，false模切）
     */
    @Override
    public boolean kiri(int position, IHai hai, boolean reach) {
        gameState.requireWaitTehaiAction();
        gameState = GameState.WAIT_RIVER_ACTION;

        // 牌河增加该牌
        IHaiRiver haiRiver = getHaiRiver(position);
        if (reach) {
            // 两立直，仅在第一巡同巡操作
            boolean isDoubleReach = haiRiver.isEmpty() && haiRiver.isSameFirstJun();
            haiRiver.reach(hai, isDoubleReach);
            // 设置同巡状态
            haiRiver.setSameJun(true);
        } else {
            haiRiver.kiri(hai);
        }

        // 手牌切掉该牌
        ITehai tehai = getTehai(position);
        boolean handKiri = tehai.kiri(hai);

        // 计算向听
        calcCurrentPlayerSyaten(position);
        // 日志处理
        gameLogger.kiri(position, hai, reach, handKiri);
        // 动作数增加
        actionCount++;
        // 最后操作的玩家记录
        lastPlayerPosition = position;

        return handKiri;
    }

    @Override
    public void reach2(int[] increaseAndDecrease) {
        gameState.requireKyokuStarted();
        // 更新分数
        this.playerSpaceManager.setState(increaseAndDecrease, PlayerPublicSpace::setPlayerPoint);
        // 增加场供
        kyotaku++;
        // 日志处理
        gameLogger.reach2(increaseAndDecrease, kyotaku);
    }

    /**
     * 增加新的宝牌指示牌
     *
     * @return 新的宝牌指示牌
     */
    @Override
    public IHai nextDoraDisplay() {
        gameState.requireKyokuStarted();
        IHai nextDoraDisplay = yama.nextDoraDisplay();
        // 日志处理
        gameLogger.nextDoraDisplay(nextDoraDisplay);

        return nextDoraDisplay;
    }

    /**
     * 从玩家的牌河吃一张牌
     *
     * @param position
     * @param fromPosition 被吃牌的玩家
     * @param selfHai1 自家的搭子1
     * @param selfHai2 自家的搭子2
     */
    @Override
    public IHai chii(int position, int fromPosition, IHai selfHai1, IHai selfHai2) {
        gameState.requireWaitRiverAction();
        gameState = GameState.WAIT_TEHAI_ACTION;

        ITehai tehai = getTehai(position);
        IHaiRiver fromRiver = getHaiRiver(fromPosition);
        IHai nakiHai = fromRiver.naki(position, NakiEnum.CHII);
        tehai.chii(nakiHai, selfHai1, selfHai2);
        // 移除同巡标识
        breakSameJun();
        // 计算第一巡同巡
        setSameFirstJun(fromPosition, position);
        // 计算向听
        calcCurrentPlayerSyaten(position);
        // 日志处理
        gameLogger.chii(position, fromPosition, nakiHai, selfHai1, selfHai2);
        // 动作数增加
        actionCount++;
        // 最后操作的玩家记录
        lastPlayerPosition = position;

        return nakiHai;
    }

    /**
     * 从玩家的牌河碰一张牌
     *
     * @param fromPosition 被碰牌的玩家
     * @param redFirst 红宝牌做搭子优先
     */
    @Override
    public IHai pon(int position, int fromPosition, boolean redFirst) {
        gameState.requireWaitRiverAction();
        gameState = GameState.WAIT_TEHAI_ACTION;

        ITehai tehai = getTehai(position);
        IHaiRiver fromRiver = getHaiRiver(fromPosition);
        IHai nakiHai = fromRiver.naki(position, NakiEnum.PON);

        tehai.pon(nakiHai, redFirst, RivalEnum.calc(position, fromPosition));
        // 移除同巡标识
        breakSameJun();
        // 计算第一巡同巡
        setSameFirstJun(fromPosition, position);
        // 计算向听
        calcCurrentPlayerSyaten(position);
        // 日志处理
        gameLogger.pon(position, fromPosition, nakiHai);
        // 动作数增加
        actionCount++;
        // 最后操作的玩家记录
        lastPlayerPosition = position;

        return nakiHai;
    }

    /**
     * 从玩家的牌河明杠一张牌
     *
     * @param fromPosition 被杠的玩家
     */
    @Override
    public IHai minkan(int position, int fromPosition) {
        gameState.requireWaitRiverAction();
        gameState = GameState.WAIT_RIVER_ACTION;

        ITehai tehai = getTehai(position);
        IHaiRiver fromRiver = getHaiRiver(fromPosition);
        IHai nakiHai = fromRiver.naki(position, NakiEnum.KAN);
        tehai.minkan(nakiHai, RivalEnum.calc(position, fromPosition));
        // 移除同巡标识
        breakSameJun();
        // 计算第一巡同巡
        setSameFirstJun(fromPosition, position);
        // 计算向听
        calcCurrentPlayerSyaten(position);
        // 日志处理
        gameLogger.minkan(position, fromPosition, nakiHai);
        // 动作数增加
        actionCount++;
        // 最后操作的玩家记录
        lastPlayerPosition = position;

        return nakiHai;
    }

    /**
     * 当前玩家加杠
     *
     * @param hai 操作的牌
     */
    @Override
    public void kakan(int position, IHai hai) {
        gameState.requireWaitTehaiAction();
        gameState = GameState.WAIT_RIVER_ACTION;

        ITehai tehai = getTehai(position);
        tehai.kakan(hai);
        // 计算向听
        calcCurrentPlayerSyaten(position);
        // 日志处理
        gameLogger.kakan(position, hai);
        // 动作数增加
        actionCount++;
        // 最后操作的玩家记录
        lastPlayerPosition = position;
    }

    /**
     * 当前玩家操作暗杠
     * <p/>
     * 该行为<b>并不会自动增加新的宝牌</b>，这是由于不同规则新宝牌的时机并不一致。
     * <p/>
     * 当需要增加新宝牌时，可以调用{@link #nextDoraDisplay()}
     *
     * @param hai 暗杠的牌
     */
    @Override
    public void annkan(int position, IHai hai) {
        gameState.requireWaitTehaiAction();
        gameState = GameState.WAIT_RIVER_ACTION;

        ITehai tehai = getTehai(position);
        tehai.annkan(hai);
        // 计算第一巡同巡
        setSameFirstJun(position, position);
        // 计算向听
        calcCurrentPlayerSyaten(position);
        // 日志处理
        gameLogger.annkan(position, hai);
        // 动作数增加
        actionCount++;
        // 最后操作的玩家记录
        lastPlayerPosition = position;
    }

    /**
     * 拔北
     *
     * @param hai 拔北的牌
     */
    @Override
    public void kita(int position, IHai hai) {
        gameState.requireWaitTehaiAction();
        gameState = GameState.WAIT_RIVER_ACTION;

        ITehai tehai = getTehai(position);
        tehai.kita(hai);
        // 计算第一巡同巡
        setSameFirstJun(position, position);
        // 计算向听
        calcCurrentPlayerSyaten(position);
        // 日志处理
        gameLogger.kita(position, hai);
        // 动作数增加
        actionCount++;
        // 最后操作的玩家记录
        lastPlayerPosition = position;
    }

    /**
     * 和了
     * <p/>
     * 一个对局可能有多个人同时和了
     *
     * @param agariPosition 和牌的玩家
     * @param fromPosition 从玩家
     * @param yakus 和了的役
     * @param ban 番数
     * @param fu 符数
     * @param score 和了点数
     * @param increaseAndDecrease 玩家的分数更新
     */
    @Override
    public void agari(int agariPosition, int fromPosition, List<IYaku> yakus, int ban, int fu, int score,
            int[] increaseAndDecrease) {
        gameState.requireKyokuStarted();

        // 更新分数
        this.playerSpaceManager.setState(increaseAndDecrease, PlayerPublicSpace::setPlayerPoint);
        // 供托清空
        this.kyotaku = 0;
        // 本场实际值清空
        // 多人荣和时，仅第一人会计算本场数，后续和牌的人不会计算
        this.honba[0] = 0;
        // 日志处理
        if (agariPosition == fromPosition) {
            kyokuEndEnum = KyokuEndEnum.TSUMO;
            gameLogger.tsumo(fromPosition, yakus, ban, fu, score, increaseAndDecrease);
        } else {
            kyokuEndEnum = KyokuEndEnum.RON;
            gameLogger.ron(agariPosition, fromPosition, yakus, ban, fu, score, increaseAndDecrease);
        }
        // 最后操作的玩家记录
        lastPlayerPosition = agariPosition;
    }

    /**
     * 流局
     *
     * @param increaseAndDecrease 玩家的分数更新
     */
    @Override
    public void ryuukyoku(IRyuukyoku ryuukyoku, int[] increaseAndDecrease) {
        gameState.requireKyokuStarted();
        gameState = GameState.KYOKU_END;
        // 更新分数
        this.playerSpaceManager.setState(increaseAndDecrease, PlayerPublicSpace::setPlayerPoint);
        // 流局类型
        kyokuEndEnum = ryuukyoku.isHalfway() ? KyokuEndEnum.HALF_RYUUKYOKU : KyokuEndEnum.RYUUKYOKU;
        // 日志处理
        gameLogger.ryuukyoku(ryuukyoku.getRyuukyokuName(), increaseAndDecrease);
        // 对局结束
        gameLogger.kyokuEnd(round, increaseAndDecrease);
    }

    // --------------------------------------------------------
    // 状态获取
    // --------------------------------------------------------

    /**
     * 获取游戏状态
     */
    @Override
    public GameState getGameState() {
        return gameState;
    }

    /**
     * 获取玩家人数
     */
    @Override
    public int getPlayerSize() {
        return playerSize;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IPlayer getPlayer(int position) {
        return playerSpaceManager.getState(position, PlayerDefaultSpace::getSelf);
    }

    /**
     * 设置玩家信息
     *
     * @param players 玩家集合
     */
    @Override
    public void setPlayers(List<IPlayer> players) {
        playerSpaceManager.setState(players, PlayerDefaultSpace::setSelf);
    }

    /**
     * 获取当前的场风
     */
    @Override
    public KazeEnum getBakaze() {
        return this.bakaze;
    }

    /**
     * 设置当前场风
     *
     * @param bakaze 场风
     */
    @Override
    public void setBakaze(KazeEnum bakaze) {
        this.bakaze = bakaze;
    }

    /**
     * 获取庄家的坐席
     */
    @Override
    public int getOya() {
        return this.oya;
    }

    /**
     * 设置当前庄家
     *
     * @param oya 当前庄家
     */
    @Override
    public void setOya(int oya) {
        this.oya = oya;
    }

    /**
     * 获取玩家的分数
     */
    @Override
    public int[] getPlayerPoints() {
        return playerSpaceManager.getDefaultSpaces() //
                .stream() //
                .mapToInt(PlayerPublicSpace::getPlayerPoint) //
                .toArray();
    }

    /**
     * 设置当前玩家分数
     *
     * @param playerPoints 玩家分数
     */
    @Override
    public void setPlayerPoints(int[] playerPoints) {
        playerSpaceManager.setState(playerPoints, PlayerPublicSpace::setPlayerPoint);
    }

    /**
     * 获取某个玩家的手牌
     *
     * @param position 玩家的坐席
     */
    @Override
    public ITehai getTehai(int position) {
        return playerSpaceManager.getState(position, PlayerDefaultSpace::getTehai);
    }

    /**
     * 获取所有手牌
     */
    @Override
    public List<ITehai> getTehais() {
        return playerSpaceManager.getDefaultSpaces() //
                .stream() //
                .map(PlayerDefaultSpace::getTehai) //
                .collect(Collectors.toList());
    }

    /**
     * 获取某个玩家的牌河
     *
     * @param position 玩家的坐席
     */
    @Override
    public IHaiRiver getHaiRiver(int position) {
        return playerSpaceManager.getState(position, PlayerPublicSpace::getHaiRiver);
    }

    /**
     * 获取所有牌河
     */
    @Override
    public List<IHaiRiver> getHaiRivers() {
        return playerSpaceManager.getDefaultSpaces()
                .stream() //
                .map(PlayerPublicSpace::getHaiRiver) //
                .collect(Collectors.toList());
    }

    /**
     * 获取总对局数
     */
    @Override
    public int getRound() {
        return this.round;
    }

    /**
     * 获取动作数
     */
    @Override
    public int getActionCount() {
        return actionCount;
    }

    /**
     * 获取本场数（展示值）
     */
    @Override
    public int getDisplayHonba() {
        return honba[1];
    }

    /**
     * 获取本场数（实际值）
     */
    @Override
    public int getRealHonba() {
        return honba[0];
    }

    /**
     * 获取供托（立直棒）
     */
    @Override
    public int getKyotaku() {
        return this.kyotaku;
    }

    /**
     * 设置供托数（立直棒）
     *
     * @param kyotaku 供托数
     */
    @Override
    public void setKyotaku(int kyotaku) {
        this.kyotaku = kyotaku;
    }

    /**
     * 获取所有宝牌指示牌
     */
    @Override
    public List<IHai> getDoraDisplays() {
        return yama.getDoraDisplay();
    }

    @Override
    public List<IHai> getDoras() {
        return yama.getDoras();
    }

    @Override
    public List<IHai> getUraDoras() {
        return yama.getUraDoras();
    }

    /**
     * 获取牌山的剩余数
     */
    @Override
    public int getYamaCountdown() {
        return yama.getCountdown();
    }

    /**
     * 获取牌山种子
     */
    @Override
    public String getSeed() {
        return yamaWorker.getSeed();
    }

    /**
     * 获取玩家是否立直标识
     */
    @Override
    public List<Boolean> getReachs() {
        return playerSpaceManager.getDefaultSpaces() //
                .stream() //
                .map(PlayerPublicSpace::getHaiRiver) //
                .map(IHaiRiver::isReach) //
                .collect(Collectors.toList());
    }

    @Override
    public int getSyaten(int position) {
        return playerSpaceManager.getDefaultSpace(position).getSyaten();
    }

    @Override
    public boolean isFuriten(int position) {
        return playerSpaceManager.getDefaultSpace(position).isFuriten();
    }

    // --------------------------------------------------------
    // 持有，但是仅在外部使用的部分，用于值的传播
    // --------------------------------------------------------

    /**
     * 设置当前庄家是否连庄
     */
    @Override
    public void setRenchan(boolean renchan, boolean clearHonba) {
        // 多次荣和时，以庄家的那次是否连庄作为效果，主要3种情形
        // 1. 庄家先荣和，子家再荣和，实际值需要清空，需要连庄，下一局实际值 = 展示值
        // 2. 子家先荣和，庄家再荣和，实际值需要清空，需要连庄，下一局实际值 = 展示值
        // 3. 只有子家荣和，实际值清空，不连庄，展示值改为0
        if (!this.renchan) {
            this.renchan = renchan;
        }
        if (clearHonba) {
            honba[0] = 0;
        }
        // 清空实际值，并且没有连庄时，将展示值也清空
        // 若非这些情况，则不需要
        if (clearHonba && !this.renchan) {
            honba[2] = 0;
        } else {
            honba[2] = 1;
        }
    }

    /**
     * 庄家是否连庄
     */
    @Override
    public boolean isRenchan() {
        return renchan;
    }

    /**
     * 破坏同巡状态
     */
    @Override
    public void breakSameJun() {
        playerSpaceManager.foreach(s -> s.getHaiRiver().setSameJun(false));
    }

    @Override
    public KyokuEndEnum getKyoKuEndType() {
        return kyokuEndEnum;
    }

    @Override
    public IGameLogger getGameLogger() {
        return this.gameLogger;
    }

    @Override
    public void setGameLogger(IGameLogger gameLogger) {
        this.gameLogger = gameLogger;
    }

    @Override
    public IPlayerSpaceManager getPlayerSpaceManager() {
        return playerSpaceManager;
    }

    // --------------------------------------------------------
    // 其他
    // --------------------------------------------------------

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        // 东1局 几本场
        sb.append(bakaze.getKazeName()).append(oya + 1).append("局");
        if (honba[1] > 0) {
            sb.append(honba[1]).append("本场");
        }
        sb.append(", ");
        // 剩余牌数：
        sb.append("剩余牌数").append(yama.getCountdown()).append(", ");
        // 当前回合
        sb.append("最后玩家：").append(lastPlayerPosition).append("]");


        return sb.toString();
    }

    // --------------------------------------------------------
    // 私有方法
    // --------------------------------------------------------

    /**
     * 设置第一巡是否同巡
     *
     * @param fromPosition 弃牌玩家
     * @param actionPosition 动作玩家，暗杠时弃牌和暗杠玩家相同
     */
    private void setSameFirstJun(int fromPosition, int actionPosition) {
        // 若是暗杠等操作，应当是本身玩家起始，若是鸣牌等操作，应当是来源玩家的下一名玩家
        // 通过自风换算，
        KazeEnum fromKaze = KazeEnum.jiKaze(fromPosition, oya);
        KazeEnum startKaze = fromPosition == actionPosition ? fromKaze : KazeEnum.next(fromKaze);
        int startPlayer = KazeEnum.getKazePlayer(startKaze.getOrder(), oya);
        IHaiRiver startPlayerRiver = getHaiRiver(startPlayer);
        if (!startPlayerRiver.isEmpty() || !startPlayerRiver.isSameFirstJun()) {
            return;
        }
        for (int i = startKaze.getOrder(); i < playerSize; i++) {
            int p = KazeEnum.getKazePlayer(i, oya);
            getHaiRiver(p).setSameFirstJun(false);
        }
    }

    /**
     * 重新计算当前玩家的向听数，TODO，需要当局缓存来降低计算
     */
    private void calcCurrentPlayerSyaten(int position) {
        calcPlayerSyaten(position);
    }

    /**
     * 计算向听数
     *
     * @param p 玩家坐席
     */
    private void calcPlayerSyaten(int p) {
        playerSpaceManager.getDefaultSpace(p).setSyaten(syatenCalculator.calcMin(getTehai(p).getHand()));
    }
}
