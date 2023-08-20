package com.github.terralian.fastmaj.game.event.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.IGameEventQueue;
import com.github.terralian.fastmaj.game.IPlayerActionManager;
import com.github.terralian.fastmaj.game.action.river.RiverActionType;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.game.context.PlayerGameContextFactory;
import com.github.terralian.fastmaj.game.event.GameEvent;
import com.github.terralian.fastmaj.game.event.GameEventCode;
import com.github.terralian.fastmaj.game.event.river.RiverActionEvent;
import com.github.terralian.fastmaj.game.event.river.RiverActionRequestEvent;
import com.github.terralian.fastmaj.game.event.system.CommonSystemEventPool;
import com.github.terralian.fastmaj.game.event.tehai.TehaiActionEvent;
import com.github.terralian.fastmaj.player.IPlayer;
import com.github.terralian.fastmaj.player.RivalEnum;

/**
 * @author Terra.Lian
 */
public class RiverActionRequestEventHandler implements IGameEventHandler {

    /**
     * 玩家动作管理器
     */
    protected IPlayerActionManager playerActionManager;

    public RiverActionRequestEventHandler(IPlayerActionManager playerActionManager) {
        this.playerActionManager = playerActionManager;
    }

    @Override
    public int handleEventCode() {
        return GameEventCode.REQUEST_RIVER_ACTION;
    }

    @Override
    public void handle(GameEvent gameEvent, IGameCore gameCore, GameConfig gameConfig, IGameEventQueue eventQueue) {
        RiverActionRequestEvent requestEvent = (RiverActionRequestEvent) gameEvent;
        TehaiActionEvent fromEvent = requestEvent.getFromEvent();
        // 循环所有玩家，获取其可执行动作集合
        List<RiverActionEvent> actions = new ArrayList<>();
        for (int i = 0; i < gameConfig.getPlayerSize(); i++) {
            // 跳过当前处理手牌的玩家
            if (gameCore.getPosition() == i) {
                continue;
            }

            PlayerGameContext playerGameContext = PlayerGameContextFactory.buildByGameCore(i, gameConfig, gameCore);
            Set<RiverActionType> enableActions = playerActionManager.validateRiverActions(i, //
                    fromEvent, gameConfig, gameCore, playerGameContext
            );
            // 没有可执行动作则跳过
            if (enableActions.isEmpty()) {
                continue;
            }

            // 为玩家执行动作，校验该动作合法，并加入到集合
            IPlayer player = gameCore.getPlayer(i);
            RiverActionEvent nakiAction = player.nakiOrRon(gameCore.getPosition(), fromEvent.getActionType(),
                    enableActions, playerGameContext);
            // 玩家跳过执行
            if (nakiAction == null || nakiAction.getRiverType() == RiverActionType.SKIP) {
                continue;
            }
            if (!enableActions.contains(nakiAction.getRiverType())) {
                throw new IllegalStateException("玩家" + (i + 1) + "不可执行的牌河动作：" + nakiAction.getRiverType());
            }
            // 设置无需由玩家设置的项
            nakiAction.setFromHai(fromEvent.getIfHai()).setPosition(i).setFrom(gameCore.getPosition());
            actions.add(nakiAction);
        }

        // 没有人处理
        if (actions.isEmpty()) {
            return;
        }

        // 由于被牌河事件打断，移除队列中的新摸牌事件
        eventQueue.removeNormal(GameEventCode.DRAW);

        // 有动作的情况，先对动作排序，取优先级最高的
        // 若优先级相同，需要以手牌动作玩家的视角，按下家，对家，上家的顺序优先处理
        // 对于部分规则仅允许一人和牌时，取第一动作即可
        actions.sort((a, b) -> {
            if (a.getOrder() == b.getOrder()) {
                return RivalEnum.compare(fromEvent.getPosition(), a.getPosition(), b.getPosition());
            }
            return a.getOrder() - b.getOrder();
        });

        // 根据获取的动作发起一个或多个事件，多人事件如多人荣和
        RiverActionEvent firstAction = actions.get(0);

        // 发起一个流局校验（三家和了）
        // 对局结束会发起一个游戏结束判定，判定没过的话会发起下一局事件
        // 对局没结束就继续消费下一个事件
        boolean isRon = firstAction.getRiverType() == RiverActionType.RON;
        if (isRon) {
            eventQueue.addPriority(CommonSystemEventPool.get(GameEventCode.RON3_RYUUKYOKU_CHECK));
        }
        for (RiverActionEvent action : actions) {
            if (firstAction.getRiverType() != action.getRiverType()) {
                break;
            }
            if (isRon) {
                eventQueue.addPriority(action);
            } else {
                eventQueue.addNormal(action);
            }
        }

        gameCore.setLastRiverAction(firstAction);
    }
}
