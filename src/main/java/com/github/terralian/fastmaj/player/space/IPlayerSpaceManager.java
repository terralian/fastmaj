package com.github.terralian.fastmaj.player.space;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * 玩家空间管理器
 *
 * @author Terra.Lian
 */
public interface IPlayerSpaceManager {

    /**
     * 为所有玩家统一设置某项的值
     *
     * @param data 数据
     * @param consumer 消费者
     * @param <T> 值类型
     */
    <T> void setState(List<T> data, BiConsumer<PlayerDefaultSpace, T> consumer);

    /**
     * 为所有玩家统一设置某项的值
     *
     * @param data 数据
     * @param consumer 消费者
     * @param <T> 值类型
     */
    <T> void setState(T[] data, BiConsumer<PlayerDefaultSpace, T> consumer);

    /**
     * 获取某个玩家的玩家空间
     *
     * @param position 玩家坐席
     * @apiNote 该接口获取的数据为实际数据的引用，对该数据的修改也会反馈到内部中。
     */
    PlayerDefaultSpace getDefaultSpace(int position);

    /**
     * 复制一份某位玩家的玩家默认空间
     *
     * @param position 玩家坐席
     * @apiNote 并不是所有时候都需要进行重新构建初始化对象，当玩家一致时可以复用已有的容器
     */
    PlayerDefaultSpace cloneDefaultSpace(int position);

    /**
     * 复制出一份所有玩家的独立空间给某位玩家，这个对象可能会被玩家随手修改而需要保证下次获取时和系统所持有的信息一致。
     *
     * @param position 玩家坐席
     * @apiNote 并不是所有时候都需要进行重新构建初始化对象，当玩家一致时可以复用已有的容器
     */
    List<PlayerPublicSpace> clonePublicSpace(int position);

    /**
     * 重置到游戏开始之初的状态
     */
    void resetGameState();

    /**
     * 重置对局状态，可以进行下一局对局
     */
    void resetKyokuState();
}
