package com.github.terralian.fastmaj.game;

/**
 * 游戏状态，用于
 * 
 * @author terra.lian
 */
public enum GameState {

    /**
     * 游戏处于{@link IGameCore#startGame()}的初始化状态，未开始对局，此时只能执行{@link IGameCore#nextKyoku()}动作
     */
    GAME_NEW,

    /**
     * 游戏处于{@link IGameCore#nextKyoku()}的初始化状态，此时只能执行{@link IGameCore#draw}动作
     */
    KYOKU_NEW,

    /**
     * 游戏处于{@link IGameCore#draw}或者鸣牌等动作操作后的状态，等待玩家执行{@link IGameCore#kiri}等手牌动作
     */
    WAIT_TEHAI_ACTION,

    /**
     * 在玩家执行{@link IGameCore#kiri}等手牌动作后，游戏会变更到该状态，等待牌河动作，若无人执行
     * 会变更为{@link #WAIT_TEHAI_ACTION}或者 {@link #KYOKU_END}
     */
    WAIT_RIVER_ACTION,

    /**
     * 游戏处于{@link IGameCore#agari}或者{@link IGameCore#ryuukyoku}的结束状态，
     * 只能调用{@link IGameCore#nextKyoku()}。但游戏判定游戏已结束，会自动变更为{@link #GAME_UN_START}状态
     */
    KYOKU_END,

    /**
     * 游戏处于结束状态，需要调用{@link IGameCore#startGame()}重新启动初始化游戏才可运行
     */
    GAME_UN_START;

    // ------------------------------------------------------
    // 断言方法
    // ------------------------------------------------------

    /**
     * 要求游戏处于运行状态
     */
    public void requireGameStarted() {
        if (this == GAME_UN_START) {
            throw new IllegalStateException("游戏不处于已运行状态，不能操作");
        }
    }

    /**
     * 要求游戏不能运行状态
     */
    public void requireGameUnStart() {
        if (this != GAME_UN_START) {
            throw new IllegalStateException("游戏不处于未运行状态，不能操作");
        }
    }

    /**
     * 要求对局处于启动状态（非结束状态）
     */
    public void requireKyokuStarted() {
        requireGameStarted();
        if (this == GameState.KYOKU_END || this == GAME_NEW) {
            throw new IllegalStateException("对局不处于运行状态，不能操作");
        }
    }

    /**
     * 要求对局处于结束状态（游戏为运行状态）
     */
    public void requireKyokuUnStart() {
        requireGameStarted();
        if (this != GameState.KYOKU_END && this != GAME_NEW) {
            throw new IllegalStateException("对局不处于结束状态，不能操作");
        }
    }

    /**
     * 摸牌的状态要求断言校验，需要对局处于{@link #KYOKU_NEW}启动状态或者{@link #WAIT_RIVER_ACTION}等待牌河处理动作状态（跳过）
     */
    public void requireForDraw() {
        requireKyokuStarted();
        if (this != KYOKU_NEW && this != GameState.WAIT_RIVER_ACTION) {
            throw new IllegalStateException("游戏已开始对局，不能直接重新开始对局");
        }
    }

    /**
     * 手牌动作可执行状态要求游戏处于{@link #WAIT_TEHAI_ACTION}
     */
    public void requireWaitTehaiAction() {
        requireKyokuStarted();
        if (this != WAIT_TEHAI_ACTION) {
            throw new IllegalStateException("游戏状态不为" + WAIT_TEHAI_ACTION + "，不能操作牌河动作");
        }
    }

    /**
     * 牌河动作可执行状态要求游戏处于{@link #WAIT_RIVER_ACTION}
     */
    public void requireWaitRiverAction() {
        requireKyokuStarted();
        if (this != WAIT_RIVER_ACTION) {
            throw new IllegalStateException("游戏状态不为" + WAIT_RIVER_ACTION + "，不能操作牌河动作");
        }
    }
}
