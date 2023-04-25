package com.github.terralian.fastmaj.game.action.tehai;

import com.github.terralian.fastmaj.game.event.tehai.TehaiActionEvent;
import com.github.terralian.fastmaj.hai.IHai;
import lombok.Getter;

/**
 * 
 * @author terra.lian
 */
@Getter
public class TehaiActionValue {

    /**
     * 操作的玩家
     */
    private int actionPlayer;

    /**
     * 操作的牌
     */
    private IHai actionHai;

    /**
     * 动作类型
     */
    private TehaiActionType actionType;

    /**
     * 根据玩家请求参数{@link TehaiActionEvent}创建{@link TehaiActionValue}
     *
     * @param tehaiEvent 请求参数
     * @param actionPlayer 动作对象
     */
    public TehaiActionValue(TehaiActionEvent tehaiEvent, int actionPlayer) {
        this.actionHai = tehaiEvent.getIfHai();
        this.actionType = tehaiEvent.getActionType();
        this.actionPlayer = actionPlayer;
    }

    /**
     * 是否是弃牌动作（包含摸牌和立直）
     */
    public boolean isKiriAction() {
        return actionType == TehaiActionType.KIRI || actionType == TehaiActionType.REACH;
    }

    /**
     * 是否是杠牌动作（暗杠，加杠）
     */
    public boolean isKanAction() {
        return actionType == TehaiActionType.ANNKAN || actionType == TehaiActionType.KAKAN;
    }
}
