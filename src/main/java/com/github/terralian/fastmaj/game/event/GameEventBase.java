package com.github.terralian.fastmaj.game.event;

/**
 * 游戏事件基类，其实现两有事件本身及事件处理器两类，这两类都通过同一定义编码进行关联。
 *
 * @author terra.lian
 */
public interface GameEventBase {

    /**
     * 获取事件编码，每种事件类型都有唯一的编码
     */
    int getEventCode();
}
