package com.github.terralian.fastmaj.game.event;

import com.github.terralian.fastmaj.game.action.river.RiverActionType;
import com.github.terralian.fastmaj.game.event.river.RiverActionEvent;
import com.github.terralian.fastmaj.game.event.river.SkipEvent;
import com.github.terralian.fastmaj.game.event.tehai.TehaiActionEvent;
import com.github.terralian.fastmaj.game.event.tehai.TehaiEventCode;

/**
 * CreateByTerra
 *
 * @author terra.lian
 * @since 2023-06-04
 */
public abstract class ActionEvents {
    /**
     * 跳过事件
     */
    public static final SkipEvent SKIP = new SkipEvent();

    /**
     * 是否是结束事件
     *
     * @param actionEvent 结束的事件
     */
    public static boolean isEndEvent(ActionEvent actionEvent) {
        return actionEvent.getCode() == GameEventCode.RON || actionEvent.getCode() == GameEventCode.TSUMO;
    }

    /**
     * 是否需要再摸一枚牌
     *
     * @param actionEvent 事件
     */
    public static boolean needAddDraw(ActionEvent actionEvent) {
        if (actionEvent == null)
            return false;
        return actionEvent.getCode() == GameEventCode.MINKAN  //
                || actionEvent.getCode() == GameEventCode.ANNKAN //
                || actionEvent.getCode() == GameEventCode.KITA //
                || actionEvent.getCode() == GameEventCode.KAKAN;
    }

    /**
     * 是否需要再摸一枚牌
     *
     * @param actionEvent 事件
     */
    public static boolean needAddDraw(RiverActionEvent actionEvent) {
        if (actionEvent == null)
            return false;
        return actionEvent.getRiverType() == RiverActionType.MINKAN;
    }

    /**
     * 是否需要再摸一枚牌
     *
     * @param actionEvent 事件
     */
    public static boolean needAddDraw(TehaiActionEvent actionEvent) {
        return actionEvent.getCode() == TehaiEventCode.KAKAN //
                || actionEvent.getCode() == TehaiEventCode.ANNKAN //
                || actionEvent.getCode() == TehaiEventCode.KITA;
    }

    /**
     * 该事件执行完成后，是否需要切换玩家
     *
     * @param actionEvent 事件
     */
    public static boolean needSwitchPlayer(TehaiActionEvent actionEvent) {
        return actionEvent.getCode() == GameEventCode.KIRI //
                || actionEvent.getCode() == GameEventCode.REACH;
    }
}
