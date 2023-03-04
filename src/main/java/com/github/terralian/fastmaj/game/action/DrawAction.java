package com.github.terralian.fastmaj.game.action;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.action.river.RiverActionType;
import com.github.terralian.fastmaj.game.action.river.RiverActionValue;
import com.github.terralian.fastmaj.game.action.tehai.TehaiActionType;
import com.github.terralian.fastmaj.game.action.tehai.TehaiActionValue;
import com.github.terralian.fastmaj.yama.DrawFrom;

/**
 * 摸牌动作，从牌山或者王牌区摸一枚牌。
 * <p/>
 * 该动作由程序自动执行
 * 
 * @author terra.lian
 */
public class DrawAction implements IGameAction {

    /**
     * 执行摸牌动作，向当前等待的玩家摸进一枚手牌
     * <p/>
     * 该方法使用玩家的最近动作来判断从何处摸牌：<br>
     * 若上回合动作存在，表示进行过杠牌或者拔北，此时从王牌区摸牌，否则从牌山摸牌.
     * 
     * @param gameConfig 游戏规则
     * @param gameCore 游戏核心
     */
    public void doAction(GameConfig gameConfig, IGameCore gameCore) {
        RiverActionValue lastRiverAction = gameCore.getLastRiverAction();
        TehaiActionValue lastTehaiAction = gameCore.getLastTehaiAction();
        DrawFrom drawFrom = DrawFrom.YAMA;
        if(lastRiverAction != null && lastRiverAction.getActionType() == RiverActionType.MINKAN) {
            drawFrom = DrawFrom.RINSYAN;
        } else if (lastTehaiAction != null && (lastTehaiAction.getActionType() == TehaiActionType.ANNKAN
                || lastTehaiAction.getActionType() == TehaiActionType.KAKAN)) {
            drawFrom = DrawFrom.RINSYAN;
        }
        gameCore.draw(drawFrom);
    }
}
