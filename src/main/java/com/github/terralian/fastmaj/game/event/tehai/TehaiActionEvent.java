package com.github.terralian.fastmaj.game.event.tehai;

import com.github.terralian.fastmaj.game.action.tehai.TehaiActionType;
import com.github.terralian.fastmaj.game.event.ActionEvent;
import com.github.terralian.fastmaj.hai.IHai;

/**
 * 手牌事件
 *
 * @author terra.lian
 * @since 2023-04-25
 */
public interface TehaiActionEvent extends ActionEvent {

    /**
     * 获取动作类型
     */
    TehaiActionType getEventType();

    /**
     * 获取事件编码
     */
    @Override
    default int getEventCode() {
        return getEventType().getCode();
    }

    /**
     * 返回操作的手牌（可能为空）
     */
    IHai getIfHai();
}
