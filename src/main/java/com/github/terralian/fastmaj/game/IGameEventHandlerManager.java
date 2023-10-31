package com.github.terralian.fastmaj.game;

import com.github.terralian.fastmaj.game.event.GameEvent;
import com.github.terralian.fastmaj.game.event.IGameEventHandler;

/**
 * {@link IGameEventHandlerManager} 管理器，提供对事件处理器的初始化及调度
 *
 * @author terra.lian
 */
public interface IGameEventHandlerManager {

    /**
     * 覆盖某种事件的默认处理器，使用传入的作为代替
     *
     * @param gameEventHandler 游戏事件处理器
     */
    void override(IGameEventHandler gameEventHandler);

    /**
     * 根据游戏事件获取其对应的处理器
     *
     * @param gameEvent 游戏事件
     */
    IGameEventHandler get(GameEvent gameEvent);
}
