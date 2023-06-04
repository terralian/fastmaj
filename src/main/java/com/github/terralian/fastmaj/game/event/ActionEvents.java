package com.github.terralian.fastmaj.game.event;

import com.github.terralian.fastmaj.game.event.river.RiverActionEvent;
import com.github.terralian.fastmaj.game.event.river.SkipEvent;
import com.github.terralian.fastmaj.game.event.tehai.TehaiActionEvent;

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
        return actionEvent.getType() == GameEventEnum.RON || actionEvent.getType() == GameEventEnum.TSUMO;
    }

    /**
     * 是否需要再摸一枚牌
     *
     * @param actionEvent 事件
     */
    public static boolean needAddDraw(ActionEvent actionEvent) {
        if (actionEvent == null) return false;
        return actionEvent.getType() == GameEventEnum.MINKAN  //
                || actionEvent.getType() == GameEventEnum.ANNKAN //
                || actionEvent.getType() == GameEventEnum.KITA //
                || actionEvent.getType() == GameEventEnum.KAKAN;
    }

    /**
     * 是否需要再摸一枚牌
     *
     * @param actionEvent 事件
     */
    public static boolean needAddDraw(RiverActionEvent actionEvent) {
        if (actionEvent == null) return false;
        return actionEvent.getType() == GameEventEnum.MINKAN  //
                || actionEvent.getType() == GameEventEnum.ANNKAN;
    }

    /**
     * 是否需要再摸一枚牌
     *
     * @param actionEvent 事件
     */
    public static boolean needAddDraw(TehaiActionEvent actionEvent) {
        if (actionEvent == null) return false;
        return actionEvent.getType() == GameEventEnum.KAKAN //
                || actionEvent.getType() == GameEventEnum.KITA;
    }
}
