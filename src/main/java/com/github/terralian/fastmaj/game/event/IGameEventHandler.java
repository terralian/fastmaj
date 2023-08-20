package com.github.terralian.fastmaj.game.event;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.IGameEventQueue;

/**
 * 事件处理器，不同的事件有着不同的处理方式，如动作的执行器、游戏结束的校验器等。
 * <p/>
 * 事件处理器需要根据事件的内容修改游戏内核的属性信息，又需要根据内核的状态或者计算的结果产生新的状态。
 *
 * @author Terra.Lian
 */
public interface IGameEventHandler extends GameEventBase {

    /**
     * 获取事件类型，每种系统默认定义的实际都有其对应的类型
     */
    Enum getEventType();

    /**
     * 进行事件处理
     *
     * @param gameEvent 事件（实现类）
     * @param gameCore 游戏内核
     * @param gameConfig 游戏配置
     * @param eventQueue 事件队列
     */
    void handle(GameEvent gameEvent, IGameCore gameCore, GameConfig gameConfig, IGameEventQueue eventQueue);
}
