package com.github.terralian.fastmaj.player.space;

import com.github.terralian.fastmaj.tehai.ITehai;

/**
 * 玩家私有空间信息，这些空间仅对玩家可见
 *
 * @author terra.lian
 */
public interface IPlayerPrivateSpace extends IPlayerSpace {

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
