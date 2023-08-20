package com.github.terralian.fastmaj.game.event.handler;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.IGameEventQueue;
import com.github.terralian.fastmaj.game.event.GameEvent;
import com.github.terralian.fastmaj.game.event.GameEventCode;
import com.github.terralian.fastmaj.game.event.check.KyokuEndCheckEvent;
import com.github.terralian.fastmaj.game.event.system.RyuukyokuEvent;
import com.github.terralian.fastmaj.game.ryuuky.IRyuukyokuResolver;
import com.github.terralian.fastmaj.game.ryuuky.IRyuukyokuResolverManager;

/**
 * 对局结束判定事件处理器，判定当前局是否中途流局或者荒牌流局
 *
 * @author Terra.Lian
 * @apiNote 荣和或者自摸的结束校验不通过该类，而是直接发起对局结束事件
 * @see KyokuEndCheckEvent
 */
public class KyokuEndCheckEventHandler implements IGameEventHandler {

    protected IRyuukyokuResolverManager ryuukyokuResolverManager;

    public KyokuEndCheckEventHandler(IRyuukyokuResolverManager ryuukyokuResolverManager) {
        this.ryuukyokuResolverManager = ryuukyokuResolverManager;
    }

    @Override
    public int handleEventCode() {
        return GameEventCode.KYOKU_END_CHECK;
    }

    @Override
    public void handle(GameEvent gameEvent, IGameCore gameCore, GameConfig gameConfig, IGameEventQueue eventQueue) {
        IRyuukyokuResolver resolver = ryuukyokuResolverManager.resolve(gameConfig, gameCore);
        if (resolver != null) {
            eventQueue.addPriority(new RyuukyokuEvent(resolver));
        }
    }
}
