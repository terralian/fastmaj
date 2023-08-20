package com.github.terralian.fastmaj.game;

import java.util.HashMap;
import java.util.Map;

import com.github.terralian.fastmaj.game.action.DrawAction;
import com.github.terralian.fastmaj.game.action.GameEndAction;
import com.github.terralian.fastmaj.game.action.river.ChiiAction;
import com.github.terralian.fastmaj.game.action.river.MinKanAction;
import com.github.terralian.fastmaj.game.action.river.PonAction;
import com.github.terralian.fastmaj.game.action.river.RonAction;
import com.github.terralian.fastmaj.game.action.tehai.AnnkanAction;
import com.github.terralian.fastmaj.game.action.tehai.KakanAction;
import com.github.terralian.fastmaj.game.action.tehai.KiriAction;
import com.github.terralian.fastmaj.game.action.tehai.KitaAction;
import com.github.terralian.fastmaj.game.action.tehai.ReachAction;
import com.github.terralian.fastmaj.game.action.tehai.Ryuukyoku99Action;
import com.github.terralian.fastmaj.game.action.tehai.TsumoAction;
import com.github.terralian.fastmaj.game.event.GameEvent;
import com.github.terralian.fastmaj.game.event.handler.BreakSameJunEventHandler;
import com.github.terralian.fastmaj.game.event.handler.GameStartEventHandler;
import com.github.terralian.fastmaj.game.event.handler.IGameEventHandler;
import com.github.terralian.fastmaj.game.event.handler.KyokuEndCheckEventHandler;
import com.github.terralian.fastmaj.game.event.handler.KyokuEndEventHandler;
import com.github.terralian.fastmaj.game.event.handler.KyokuStartEventHandler;
import com.github.terralian.fastmaj.game.event.handler.NextDoraEventHandler;
import com.github.terralian.fastmaj.game.event.handler.ReachSetEventHandler;
import com.github.terralian.fastmaj.game.event.handler.RiverActionRequestEventHandler;
import com.github.terralian.fastmaj.game.event.handler.RyuukyokuEventHandler;
import com.github.terralian.fastmaj.game.event.handler.TehaiActionRequestEventHandler;
import com.github.terralian.fastmaj.game.ryuuky.Ron3RyuukyokuResolver;
import com.github.terralian.fastmaj.game.validator.GameEndValidator;

/**
 * {@link IGameEventHandlerManager}默认实现
 *
 * @author Terra.Lian
 */
public class GameEventHandlerManager implements IGameEventHandlerManager {

    private final Map<Integer, IGameEventHandler> eventHandlerMap;

    public GameEventHandlerManager(GameComponent gameComponent) {
        this.eventHandlerMap = new HashMap<>();
        this.initEventHandlerMap(gameComponent);
    }

    public GameEventHandlerManager(Map<Integer, IGameEventHandler> eventHandlerMap) {
        this.eventHandlerMap = eventHandlerMap;
    }

    @Override
    public void override(IGameEventHandler gameEventHandler) {
        eventHandlerMap.put(gameEventHandler.handleEventCode(), gameEventHandler);
    }

    @Override
    public IGameEventHandler get(GameEvent gameEvent) {
        return eventHandlerMap.get(gameEvent.getCode());
    }

    /**
     * 初始化事件处理器
     */
    private void initEventHandlerMap(GameComponent gameComponent) {
        override(new GameStartEventHandler());
        override(new GameEndAction());
        override(new GameEndValidator());

        override(new KyokuStartEventHandler());
        override(new KyokuEndEventHandler());
        override(new KyokuEndCheckEventHandler(gameComponent.getRyuukyokuResolverManager()));
        override(new RyuukyokuEventHandler());
        override(new Ron3RyuukyokuResolver());

        override(new ReachSetEventHandler());
        override(new NextDoraEventHandler());
        override(new RiverActionRequestEventHandler(gameComponent.getPlayerActionManager()));
        override(new TehaiActionRequestEventHandler(gameComponent.getPlayerActionManager()));
        override(new BreakSameJunEventHandler());

        override(new DrawAction());

        override(new AnnkanAction());
        override(new KakanAction());
        override(new KiriAction());
        override(new KitaAction());
        override(new ReachAction());
        override(new Ryuukyoku99Action());
        override(new TsumoAction(gameComponent.getAgariCalculator()));

        override(new ChiiAction());
        override(new MinKanAction());
        override(new PonAction());
        override(new RonAction(gameComponent.getAgariCalculator()));
    }
}
