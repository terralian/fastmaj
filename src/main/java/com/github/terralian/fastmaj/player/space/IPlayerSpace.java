package com.github.terralian.fastmaj.player.space;

/**
 * 玩家空间基类，为多种玩家空间
 *
 * @author Terra.Lian
 */
public interface IPlayerSpace {

    /**
     * 将数据复制到玩家空间容器内
     *
     * @param playerSpace 玩家空间
     */
    void copyTo(IPlayerSpace playerSpace);
}
