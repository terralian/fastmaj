package com.github.terralian.fastmaj.game.event.handler;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.IGameEventQueue;
import com.github.terralian.fastmaj.game.event.DrawEvent;
import com.github.terralian.fastmaj.game.event.GameEvent;
import com.github.terralian.fastmaj.game.event.GameEventCode;
import com.github.terralian.fastmaj.yama.DrawFrom;

/**
 * 对局开始事件
 *
 * @author Terra.Lian
 */
public class KyokuStartEventHandler implements IGameEventHandler {

    @Override
    public int handleEventCode() {
        return GameEventCode.KYOKU_START;
    }

    /**
     * 对局开始处理：执行游戏内核对局初始化，产生一个当前庄家的摸牌事件
     */
    @Override
    public void handle(GameEvent gameEvent, IGameCore gameCore, GameConfig gameConfig, IGameEventQueue eventQueue) {
        gameCore.nextKyoku();
        eventQueue.addNormal(new DrawEvent(gameCore.getOya(), DrawFrom.YAMA, gameEvent));
    }
}
