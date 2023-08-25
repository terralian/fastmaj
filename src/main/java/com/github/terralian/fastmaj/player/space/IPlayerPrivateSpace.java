package com.github.terralian.fastmaj.player.space;

import com.github.terralian.fastmaj.player.IPlayer;
import com.github.terralian.fastmaj.tehai.ITehai;

/**
 * 玩家私有空间信息，这些空间仅对玩家可见
 *
 * @author Terra.Lian
 */
public interface IPlayerPrivateSpace extends IPlayerSpace {

    /**
     * 表示玩家本人，返回一个可以进行事件处理的玩家接口
     */
    IPlayer getSelf();

    /**
     * 玩家的手牌信息
     */
    ITehai getTehai();

    /**
     * 向听数
     */
    int getSyaten();

    /**
     * 是否振听
     */
    boolean isFuriten();

    // TODO 振听类型
}
