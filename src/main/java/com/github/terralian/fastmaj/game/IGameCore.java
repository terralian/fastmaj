package com.github.terralian.fastmaj.game;

import java.util.List;

import com.github.terralian.fastmaj.game.action.river.RiverActionValue;
import com.github.terralian.fastmaj.game.action.tehai.TehaiActionValue;
import com.github.terralian.fastmaj.game.log.IGameLogger;
import com.github.terralian.fastmaj.game.ryuuky.IRyuukyoku;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.player.IPlayer;
import com.github.terralian.fastmaj.river.IHaiRiver;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yama.DrawFrom;

/**
 * 游戏核心
 * <p>
 * 该接口用于管理游戏状态，而不关心游戏的规则，通过一定顺序调用相关接口，即可实现游戏的进行。
 * 
 * 
 * @author terra.lian 
 */
public interface IGameCore {

    /**
     * 游戏开始
     * <p>
     * 该方法将会初始化内部状态。
     */
    void startGame();

    /**
     * 游戏结束
     * <p>
     * 调用该方法后，将会标记游戏为结束状态，其他游戏运行时方法将会变成非法操作
     * <p>
     * 结束对局后将完成日志的构建
     * 
     * @param increaseAndDecrease 最终得分
     */
    void endGame(int[] increaseAndDecrease);

    /**
     * 下一个对局
     * <p>
     * 该方法会自动计算是否切换场风
     * <ul>
     * <li>若连庄，则不改变当前的庄家，增加本场数
     * <li>若不连庄，则切换到下一位玩家坐庄
     * </ul>
     * 
     * @return 总局数（增加本场也增加局数）
     */
    int nextKyoku();

    /**
     * 结束对局
     * <p>
     * 调用该方法后，会将对局设置为结束状态，需要调用{@link #nextKyoku()}开始下一局
     */
    void endKyoku();

    /**
     * 切换到下一位操作玩家
     */
    void nextPlayer();

    /**
     * 切换当前操作玩家到指定玩家
     * 
     * @param position 玩家坐席
     */
    void switchPlayer(int position);

    /**
     * 当前玩家摸一枚手牌
     * <p>
     * 可以从牌山或者王牌区摸一张牌，若从王牌区摸牌，<b>并不会自动增加新的宝牌</b>，这是由于不同规则新宝牌的时机并不一致。
     * <p>
     * 当需要增加新宝牌时，可以调用{@link #nextDoraDisplay()}
     * 
     * @param drawFrom 摸牌来源
     */
    IHai draw(DrawFrom drawFrom);

    /**
     * 当前玩家切一张手牌
     * 
     * @param hai 要切的牌，通过id比较
     * @param reach 是否立直
     * @return 是否手切（true手切，false模切）
     */
    boolean kiri(IHai hai, boolean reach);

    /**
     * 立直（步骤2）玩家在{@link #kiri}动作后，放上一根立直棒
     * <p>
     * 此时已经过荣和校验，立直成立，分数更新
     * 
     * @param increaseAndDecrease
     */
    void reach2(int[] increaseAndDecrease);

    /**
     * 增加新的宝牌指示牌
     * 
     * @return 新的宝牌指示牌
     */
    IHai nextDoraDisplay();

    /**
     * 从玩家的牌河吃一张牌
     * 
     * @param fromPosition 被吃牌的玩家
     * @param selfHai1 自家的搭子1
     * @param selfHai2 自家的搭子2
     */
    IHai chii(int fromPosition, IHai selfHai1, IHai selfHai2);

    /**
     * 从玩家的牌河碰一张牌
     * 
     * @param fromPosition 被碰牌的玩家
     * @param redFirst 红宝牌做搭子优先
     */
    IHai pon(int fromPosition, boolean redFirst);

    /**
     * 从玩家的牌河明杠一张牌
     * 
     * @param fromPosition 被杠的玩家
     */
    IHai minkan(int fromPosition);

    /**
     * 当前玩家加杠
     * 
     * @param hai 操作的牌
     */
    void kakan(IHai hai);

    /**
     * 当前玩家操作暗杠
     * <p>
     * 该行为<b>并不会自动增加新的宝牌</b>，这是由于不同规则新宝牌的时机并不一致。
     * <p>
     * 当需要增加新宝牌时，可以调用{@link #nextDoraDisplay()}
     * 
     * @param hai 暗杠的牌
     */
    void ankan(IHai hai);

    /**
     * 拔北
     * 
     * @param hai 拔北的牌
     */
    void kita(IHai hai);

    /**
     * 和了，包含自摸和荣和
     * <p>
     * 一个对局可能有多个人同时和了，该操作会清空累积的立直棒（本场数仅在切换玩家时会重置）
     * 
     * @param agariPosition 和牌的玩家
     * @param fromPosition 从玩家，当自摸时该值为自身
     * @param yakus 和了的役
     * @param ban 番数
     * @param fu 符数
     * @param score 和了点数
     * @param increaseAndDecrease 玩家的分数更新
     */
    void agari(int agariPosition, int fromPosition, List<IYaku> yakus, int ban, int fu, int score, int[] increaseAndDecrease);

    /**
     * 流局，若庄家听牌则连庄，若庄家未听牌则不连庄。
     * 
     * @param ryuukyoku 流局类
     * @param increaseAndDecrease 玩家的分数更新
     */
    void ryuukyoku(IRyuukyoku ryuukyoku, int[] increaseAndDecrease);

    // --------------------------------------------------------
    // 状态获取
    // --------------------------------------------------------

    /**
     * 玩家人数
     */
    int getPlayerSize();

    /**
     * 获取当前玩家
     */
    IPlayer getPlayer();

    /**
     * 获取玩家
     * 
     * @param position 玩家坐席
     */
    IPlayer getPlayer(int position);

    /**
     * 获取当前的场风
     */
    KazeEnum getBakaze();

    /**
     * 获取庄家的坐席
     */
    int getOya();

    /**
     * 获取玩家的分数
     */
    int[] getPlayerPoints();

    /**
     * 获取当前玩家的手牌
     */
    ITehai getTehai();

    /**
     * 获取某个玩家的手牌
     * 
     * @param position 玩家的坐席
     */
    ITehai getTehai(int position);

    /**
     * 获取所有手牌
     */
    List<ITehai> getTehais();

    /**
     * 获取当前玩家的牌河
     */
    IHaiRiver getHaiRiver();

    /**
     * 获取某个玩家的牌河
     * 
     * @param position 玩家的坐席
     */
    IHaiRiver getHaiRiver(int position);

    /**
     * 获取所有牌河
     */
    List<IHaiRiver> getHaiRivers();

    /**
     * 当前玩家的隐藏状态
     */
    PlayerHideStatus getPlayerHide();

    /**
     * 获取某个的隐藏状态
     * 
     * @param position 玩家的坐席
     */
    PlayerHideStatus getPlayerHide(int position);

    /**
     * 获取所有隐藏状态
     */
    List<PlayerHideStatus> getPlayerHides();

    /**
     * 获取总对局数
     */
    int getRound();

    /**
     * 获取当前动作数。动作数指包含摸牌动作，手牌动作（弃牌）和牌河动作（鸣牌）。<b>结束动作（自摸，流局，荣和）增加动作数</b>
     * 根据动作数值可以在牌谱内对应到具体的某一打（可以在回放时用于是否进行吃碰杠决策）
     */
    int getActionCount();

    /**
     * 获取本场数
     */
    int getHonba();

    /**
     * 获取本场数的实际值
     */
    int getRealHonba();

    /**
     * 获取供托（立直棒）
     */
    int getKyotaku();

    /**
     * 获取当前操作的玩家坐席
     */
    int getPosition();

    /**
     * 获取所有宝牌指示牌
     */
    List<IHai> getDoraDisplays();

    /**
     * 获取宝牌集合
     */
    List<IHai> getDoras();

    /**
     * 里宝牌集合，仅在立直下会翻开里宝牌
     */
    List<IHai> getUraDoras();

    /**
     * 获取牌山的剩余数
     */
    int getYamaCountdown();

    /**
     * 获取最近一枚牌的摸牌来源
     */
    DrawFrom getLastDrawFrom();

    /**
     * 获取种子（牌山）
     */
    String getSeed();

    /**
     * 获取玩家是否立直标识
     */
    List<Boolean> getReachs();

    /**
     * 获取玩家是否同巡标识
     */
    List<Boolean> getSameRounds();

    // --------------------------------------------------------
    // 持有，但是仅在外部使用的部分，用于值的传播
    // --------------------------------------------------------

    /**
     * 设置当前庄家是否连庄
     * 
     * @param renchan 连庄
     * @param clearHonba 是否清除本场数，不清除则增加本场数
     */
    void setRenchan(boolean renchan, boolean clearHonba);

    /**
     * 庄家是否连庄
     */
    boolean isRenchan();

    /**
     * 是否是中途流局，仅当[流局动作触发]到[下一对局]开始时间内会存在值变更
     */
    boolean isHalfwayRyuukyuku();

    /**
     * 设置最近玩家的手牌动作
     * 
     * @param action 手牌动作
     */
    void setLastTehaiAction(TehaiActionValue action);

    /**
     * 获取最近玩家的手牌动作
     */
    TehaiActionValue getLastTehaiAction();

    /**
     * 设置最近的一个牌河动作（不包含荣和）
     * 
     * @param action 牌河动作
     */
    void setLastRiverAction(RiverActionValue action);

    /**
     * 获取最近的一个牌河动作（不包含荣和）
     */
    RiverActionValue getLastRiverAction();

    /**
     * 获取游戏核心的日志处理器
     */
    IGameLogger getGameLogger();

    /**
     * 设置游戏核心的日志处理器
     * 
     * @param gameLogger 游戏日志处理器
     */
    void setGameLogger(IGameLogger gameLogger);
}
