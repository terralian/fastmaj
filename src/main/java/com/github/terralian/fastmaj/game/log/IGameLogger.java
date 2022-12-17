package com.github.terralian.fastmaj.game.log;

import java.util.List;

import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.KazeEnum;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.player.IPlayer;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yama.DrawFrom;

/**
 * 游戏日志/牌谱处理器，该接口用于游戏内各种事件发生时，进行日志记录打印或者整理为牌谱文件。
 * 
 * @author terra.lian
 * @since 2022-10-28
 */
public interface IGameLogger {

    // ------------------------------------------------
    // 流程事件
    // ------------------------------------------------

    /**
     * 游戏开始事件
     *
     * @param players 游戏玩家
     * @param gameCore 游戏核心
     */
    default void gameStart(List<IPlayer> players, IGameCore gameCore) {
    }

    /**
     * 对局开始事件
     *
     * @param round 第几局
     * @param bakaze 场风
     * @param oya 庄家
     * @param honba 本场数
     * @param haipais 配牌
     */
    default void kyokuStart(int round, KazeEnum bakaze, int oya, int honba, List<ITehai> haipais) {
    }

    /**
     * 对局结束事件
     *
     * @param playerPoints 玩家分数
     */
    default void kyokuEnd(int round, int[] playerPoints) {
    }

    /**
     * 游戏结束事件
     */
    default void gameEnd() {
    }

    // ------------------------------------------------
    // 动作事件
    // ------------------------------------------------

    /**
     * 摸一枚牌
     *
     * @param position 玩家坐席
     * @param hai 摸的牌
     * @param drawFrom 摸牌来源
     */
    default void draw(int position, IHai hai, DrawFrom drawFrom) {
    }

    /**
     * 弃一枚牌
     *
     * @param position 玩家坐席
     * @param hai 摸的牌
     * @param reach 是否立直
     * @param handKiri 是否手切
     */
    default void kiri(int position, IHai hai, boolean reach, boolean handKiri) {
    }

    /**
     * 立直的第二步骤
     *
     * @param increaseAndDecrease 当前玩家分数（扣减后）
     * @param kyotaku 场供
     */
    default void reach2(int[] increaseAndDecrease, int kyotaku) {
    }

    /**
     * 动作吃
     *
     * @param position 动作的玩家
     * @param fromPosition 来源玩家
     * @param chiiHai 吃的牌
     * @param selfHai1 自己的搭子1
     * @param selfHai2 自己的搭子2
     */
    default void chii(int position, int fromPosition, IHai chiiHai, IHai selfHai1, IHai selfHai2) {
    }

    /**
     * 动作碰
     *
     * @param position 动作的玩家
     * @param fromPosition 来源玩家
     * @param ponHai 碰的牌
     */
    default void pon(int position, int fromPosition, IHai ponHai) {
    }

    /**
     * 动作杠
     *
     * @param position 动作的玩家
     * @param fromPosition 来源玩家
     * @param minkan 杠的牌
     */
    default void minkan(int position, int fromPosition, IHai minkan) {
    }

    /**
     * 动作加杠
     *
     * @param position 动作的玩家
     * @param kanHai 加杠的牌
     */
    default void kakan(int position, IHai kanHai) {
    }

    /**
     * 动作暗杠
     *
     * @param position 动作的玩家
     * @param annkanHai 暗杠的牌
     */
    default void annkan(int position, IHai annkanHai) {
    }

    /**
     * 动作拔北
     *
     * @param position 动作的玩家
     * @param hai 拔北的牌
     */
    default void kita(int position, IHai hai) {
    }

    /**
     * 荣和
     *
     * @param position 来源玩家
     * @param fromPosition 从玩家
     * @param yaku 和了的役
     * @param ban 番数
     * @param fu 符数
     * @param score 和了点数
     * @param increaseAndDecrease 玩家的分数更新
     */
    default void ron(int position, int fromPosition, List<IYaku> yaku, int ban, int fu, int score, int[] increaseAndDecrease) {
    }

    /**
     * 自摸
     *
     * @param position 来源玩家
     * @param yakus 和了的役
     * @param ban 番数
     * @param fu 符数
     * @param score 和了点数
     * @param increaseAndDecrease 玩家的分数更新
     */
    default void tsumo(int position, List<IYaku> yakus, int ban, int fu, int score, int[] increaseAndDecrease) {
    }

    /**
     * 流局
     *
     * @param ryuukyokuName 流局形式
     * @param increaseAndDecrease 流局后的分数
     */
    default void ryuukyoku(String ryuukyokuName, int[] increaseAndDecrease) {
    }

    // ------------------------------------------------
    // 系统事件
    // ------------------------------------------------

    /**
     * 手牌处理动作执行玩家切换
     *
     * @param prevPlayer 上一个玩家
     * @param nextPlayer 下一个玩家
     */
    default void switchPlayer(int prevPlayer, int nextPlayer) {
    }

    /**
     * 增加新的宝牌指示牌
     *
     * @param doraDisplay 新的宝牌指示牌
     */
    default void nextDoraDisplay(IHai doraDisplay) {
    }
}
