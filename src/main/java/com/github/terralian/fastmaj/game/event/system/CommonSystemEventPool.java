package com.github.terralian.fastmaj.game.event.system;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用系统事件池
 *
 * @author Terra.Lian
 */
public class CommonSystemEventPool {
    private static final Map<Integer, SystemEvent> eventMap = new HashMap<>();

    static {
        eventMap.put(SystemEventCode.GAME_START, new CommonSystemEvent(SystemEventCode.GAME_START));
        eventMap.put(SystemEventCode.GAME_END, new CommonSystemEvent(SystemEventCode.GAME_END));
        eventMap.put(SystemEventCode.GAME_END_CHECK, new CommonSystemEvent(SystemEventCode.GAME_END_CHECK));
        eventMap.put(SystemEventCode.KYOKU_START, new CommonSystemEvent(SystemEventCode.KYOKU_START));
        eventMap.put(SystemEventCode.KYOKU_END_CHECK, new CommonSystemEvent(SystemEventCode.KYOKU_END_CHECK));
        eventMap.put(SystemEventCode.KYOKU_END, new CommonSystemEvent(SystemEventCode.KYOKU_END));
        eventMap.put(SystemEventCode.RYUUKYOKU, new CommonSystemEvent(SystemEventCode.RYUUKYOKU));
        eventMap.put(SystemEventCode.RON3_RYUUKYOKU_CHECK, new CommonSystemEvent(SystemEventCode.RON3_RYUUKYOKU_CHECK));
        eventMap.put(SystemEventCode.BREAK_SAME_JUN, new CommonSystemEvent(SystemEventCode.BREAK_SAME_JUN));
    }

    /**
     * 获取定义的系统事件
     *
     * @param eventType 事件类型
     */
    public static SystemEvent get(int eventType) {
        return eventMap.get(eventType);
    }
}
