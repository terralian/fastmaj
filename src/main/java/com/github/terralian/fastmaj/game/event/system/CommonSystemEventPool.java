package com.github.terralian.fastmaj.game.event.system;

import java.util.HashMap;
import java.util.Map;

import com.github.terralian.fastmaj.game.event.GameEventCode;

/**
 * 通用系统事件池
 *
 * @author Terra.Lian
 */
public class CommonSystemEventPool {
    private static final Map<Integer, SystemEvent> eventMap = new HashMap<>();

    static {
        eventMap.put(GameEventCode.GAME_START, new CommonSystemEvent(GameEventCode.GAME_START));
        eventMap.put(GameEventCode.GAME_END, new CommonSystemEvent(GameEventCode.GAME_END));
        eventMap.put(GameEventCode.GAME_END_CHECK, new CommonSystemEvent(GameEventCode.GAME_END_CHECK));
        eventMap.put(GameEventCode.KYOKU_START, new CommonSystemEvent(GameEventCode.KYOKU_START));
        eventMap.put(GameEventCode.KYOKU_END_CHECK, new CommonSystemEvent(GameEventCode.KYOKU_END_CHECK));
        eventMap.put(GameEventCode.KYOKU_END, new CommonSystemEvent(GameEventCode.KYOKU_END));
        eventMap.put(GameEventCode.RYUUKYOKU, new CommonSystemEvent(GameEventCode.RYUUKYOKU));
        eventMap.put(GameEventCode.RON3_RYUUKYOKU_CHECK, new CommonSystemEvent(GameEventCode.RON3_RYUUKYOKU_CHECK));
        eventMap.put(GameEventCode.BREAK_SAME_JUN, new CommonSystemEvent(GameEventCode.BREAK_SAME_JUN));
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
