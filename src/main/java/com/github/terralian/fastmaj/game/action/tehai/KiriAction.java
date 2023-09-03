package com.github.terralian.fastmaj.game.action.tehai;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.IGameEventQueue;
import com.github.terralian.fastmaj.game.KyokuState;
import com.github.terralian.fastmaj.game.event.DrawEvent;
import com.github.terralian.fastmaj.game.event.GameEvent;
import com.github.terralian.fastmaj.game.event.system.KyokuEndCheckEvent;
import com.github.terralian.fastmaj.game.event.system.RiverActionRequestEvent;
import com.github.terralian.fastmaj.game.event.tehai.TehaiActionEvent;
import com.github.terralian.fastmaj.player.PlayerHelper;
import com.github.terralian.fastmaj.yama.DrawFrom;

/**
 * 一个弃牌动作
 *
 * @author terra.lian
 */
public class KiriAction implements ITehaiAction {

    @Override
    public KyokuState doAction(TehaiActionEvent actionParam, IGameCore gameCore, GameConfig gameOptions) {
        if (!gameCore.getTehai(actionParam.getPosition()).canKiri(actionParam.getIfHai())) {
            throw new IllegalArgumentException("参数错误，玩家" + (actionParam.getPosition() + 1) //
                    + "的手牌不能进行模切操作：" + actionParam.getIfHai() + " 当前玩家手牌："
                    + gameCore.getTehai(actionParam.getPosition()).getHand());
        }
        gameCore.kiri(actionParam.getPosition(), actionParam.getIfHai(), false);
        return KyokuState.CONTINUE;
    }

    @Override
    public TehaiActionType getEventType() {
        return TehaiActionType.KIRI;
    }

    @Override
    public void handle(GameEvent gameEvent, IGameCore gameCore, GameConfig gameConfig, IGameEventQueue eventQueue) {
        TehaiActionEvent tehaiActionEvent = (TehaiActionEvent) gameEvent;
        int position = tehaiActionEvent.getPosition();
        // 进行一次牌河动作请求（可行的话）
        eventQueue.addNormal(new RiverActionRequestEvent(tehaiActionEvent));
        // 流局判定
        eventQueue.addNormal(new KyokuEndCheckEvent());
        // 下一名玩家的摸牌事件
        // 若被牌河动作打断，牌河动作会将其移除
        int nextPlayer = PlayerHelper.nextPlayer(gameConfig.getPlayerSize(), position);
        eventQueue.addNormal(new DrawEvent(nextPlayer, DrawFrom.YAMA, tehaiActionEvent));
    }
}
