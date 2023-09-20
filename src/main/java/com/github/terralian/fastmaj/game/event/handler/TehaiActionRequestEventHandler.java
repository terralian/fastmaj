package com.github.terralian.fastmaj.game.event.handler;

import java.util.Set;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.IGameEventQueue;
import com.github.terralian.fastmaj.game.IPlayerActionManager;
import com.github.terralian.fastmaj.game.action.tehai.ITehaiAction;
import com.github.terralian.fastmaj.game.action.tehai.TehaiActionType;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.game.context.PlayerGameContextFactory;
import com.github.terralian.fastmaj.game.event.GameEvent;
import com.github.terralian.fastmaj.game.event.system.SystemEventType;
import com.github.terralian.fastmaj.game.event.system.TehaiActionRequestEvent;
import com.github.terralian.fastmaj.game.event.tehai.TehaiActionEvent;
import com.github.terralian.fastmaj.game.validator.tehai.ITehaiActionValidator;
import com.github.terralian.fastmaj.player.IPlayer;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.util.Assert;

/**
 * 玩家请求动作事件处理器，调用玩家接口请求一次动作，并根据动作执行
 *
 * @author Terra.Lian
 */
public class TehaiActionRequestEventHandler implements ISystemGameEventHandler {

    /**
     * 玩家动作管理器
     */
    protected IPlayerActionManager playerActionManager;

    public TehaiActionRequestEventHandler(IPlayerActionManager playerActionManager) {
        this.playerActionManager = playerActionManager;
    }

    @Override
    public SystemEventType getEventType() {
        return SystemEventType.TEHAI_ACTION_REQUEST;
    }

    @Override
    public void handle(GameEvent gameEvent, IGameCore gameCore, GameConfig gameConfig, IGameEventQueue eventQueue) {
        TehaiActionRequestEvent requestEvent = (TehaiActionRequestEvent) gameEvent;
        int position = requestEvent.getPosition();
        ITehai tehai = gameCore.getTehai(position);
        // 判定可执行动作
        Set<TehaiActionType> enableActions = playerActionManager.validateTehaiActions(position, gameConfig, gameCore);
        Assert.notEmpty(enableActions, "玩家无可用按钮，请检查动作判定器，一般至少切牌是可用的");

        // 请求玩家动作
        IPlayer player = gameCore.getPlayer(position);
        PlayerGameContext playerGameContext = PlayerGameContextFactory.buildByGameCore(position, gameConfig, gameCore); // TODO 工厂类优化
        TehaiActionEvent tehaiActionEvent = player.drawHai(tehai, enableActions, playerGameContext);

        TehaiActionType actionType = tehaiActionEvent.getEventType();
        // 校验动作是否可执行
        ITehaiActionValidator validator = playerActionManager.getTehaiActionValidator(actionType);
        if (!validator.resolveAction(position, gameConfig, gameCore)) {
            throw new IllegalArgumentException("非法手牌动作：" + actionType);
        }

        // 执行玩家动作
        tehaiActionEvent.setPosition(requestEvent.getPosition());
        ITehaiAction tehaiAction = playerActionManager.getTehaiAction(tehaiActionEvent.getEventType());
        if (tehaiAction == null) {
            throw new IllegalStateException("该手牌处理动作无法执行，动作管理器获取该动作为空：" + actionType);
        }
        tehaiAction.doAction(tehaiActionEvent, gameCore, gameConfig);
        // 发出对应事件
        eventQueue.addPriority(tehaiActionEvent);
    }
}
