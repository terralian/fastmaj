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
import com.github.terralian.fastmaj.game.event.GameEventCode;
import com.github.terralian.fastmaj.game.event.tehai.TehaiActionEvent;
import com.github.terralian.fastmaj.game.event.tehai.TehaiActionRequestEvent;
import com.github.terralian.fastmaj.game.validator.tehai.ITehaiActionValidator;
import com.github.terralian.fastmaj.player.IPlayer;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.util.Assert;

/**
 * 玩家请求动作事件处理器，调用玩家接口请求一次动作，并根据动作执行
 *
 * @author Terra.Lian
 */
public class TehaiActionRequestEventHandler implements IGameEventHandler {

    /**
     * 玩家动作管理器
     */
    protected IPlayerActionManager playerActionManager;

    public TehaiActionRequestEventHandler(IPlayerActionManager playerActionManager) {
        this.playerActionManager = playerActionManager;
    }

    @Override
    public int handleEventCode() {
        return GameEventCode.REQUEST_TEHAI_ACTION;
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
        PlayerGameContext playerGameContext = PlayerGameContextFactory.buildByGameCore(position, gameConfig, gameCore);
        TehaiActionEvent tehaiActionEvent = player.drawHai(tehai, enableActions, playerGameContext);

        TehaiActionType actionType = tehaiActionEvent.getActionType();
        // 校验动作是否可执行
        ITehaiActionValidator validator = playerActionManager.getTehaiActionValidator(actionType);
        if (!validator.resolveAction(position, gameConfig, gameCore)) {
            throw new IllegalArgumentException("非法手牌动作：" + actionType);
        }

        // 执行玩家动作
        tehaiActionEvent.setPosition(gameCore.getPosition());
        ITehaiAction tehaiAction = playerActionManager.getTehaiAction(tehaiActionEvent.getActionType());
        if (tehaiAction == null) {
            throw new IllegalStateException("该手牌处理动作无法执行，动作管理器获取该动作为空：" + actionType);
        }
        tehaiAction.doAction(tehaiActionEvent, gameCore, gameConfig);
        // 执行对应事件
        IGameEventHandler eventHandler = (IGameEventHandler) tehaiAction;
        eventHandler.handle(tehaiActionEvent, gameCore, gameConfig, eventQueue);

        // 动作执行完成后，作为上一轮的手牌动作
        gameCore.setLastTehaiAction(tehaiActionEvent);

        // 清除上一轮牌河动作缓存
        // 当无人鸣牌时，相当于无效操作
        // 当有人鸣牌时，相当于在其鸣牌后，即将打一枚牌时清空鸣牌动作
        // 此时已经经过了流局、摸牌判定，上一轮的牌河动作已经失去作用
        // 放在这里也是最兼容回放的做法（回放没有流程判定，鸣牌后就是弃牌操作）
        gameCore.setLastRiverAction(null);
    }
}
