package com.github.terralian.fastmaj.game;

import java.util.ArrayDeque;

import com.github.terralian.fastmaj.game.event.GameEvent;

/**
 * 游戏事件队列的默认实现，采用{@link ArrayDeque}数组双端队列作为容器
 *
 * @author Terra.Lian
 */
public class GameEventQueue implements IGameEventQueue {

    /**
     * 通常队列
     */
    private final ArrayDeque<GameEvent> normalQueue;
    /**
     * 优先队列
     */
    private final ArrayDeque<GameEvent> priorityQueue;
    /**
     * 优先队列
     */
    private final ArrayDeque<GameEvent> delayToHeadQueue;
    /**
     * 延迟填充
     */
    private static final DelayFill delayFill = new DelayFill();


    public GameEventQueue() {
        normalQueue = new ArrayDeque<>(10);
        priorityQueue = new ArrayDeque<>(4);
        delayToHeadQueue = new ArrayDeque<>(5);
    }

    @Override
    public int size() {
        return priorityQueue.size() + normalQueue.size();
    }

    @Override
    public GameEvent next() {
        if (this.size() == 0) {
            throw new IllegalStateException("队列中没有下一个事件");
        }
        // 获取下一个事件
        GameEvent nextEvent = priorityQueue.isEmpty() ? normalQueue.poll() : priorityQueue.poll();
        // 完成后若存在延迟事件，则延迟事件出一，当事件非延迟填充的话，加入到队头
        if (delayToHeadQueue.size() > 0) {
            GameEvent delayEvent = delayToHeadQueue.poll();
            if (delayEvent != GameEventQueue.delayFill) {
                normalQueue.addFirst(delayEvent);
            }
        }
        return nextEvent;
    }

    @Override
    public void addNormal(GameEvent gameEvent) {
        normalQueue.add(gameEvent);
    }

    @Override
    public void addDelay(GameEvent gameEvent, int delay) {
        for (int i = 1; i < delay; i++) {
            delayToHeadQueue.add(delayFill);
        }
        delayToHeadQueue.add(gameEvent);
    }

    @Override
    public void addPriority(GameEvent gameEvent) {
        priorityQueue.add(gameEvent);
    }

    @Override
    public void removeNormal(int eventCode) {
        normalQueue.removeIf(k -> k.getEventCode() == eventCode);
    }

    /**
     * @param eventCode 事件编码
     */
    @Override
    public void removePriority(int eventCode) {
        priorityQueue.removeIf(k -> k.getEventCode() == eventCode);
    }

    @Override
    public void clear() {
        normalQueue.clear();
        priorityQueue.clear();
    }

    @Override
    public int prioritySizeof(int eventCode) {
        return (int) priorityQueue.stream().filter(k -> k.getEventCode() == eventCode).count();
    }

    /**
     * 该事件用于延迟队列作为延迟填充
     */
    private static class DelayFill implements GameEvent {
        @Override
        public int getEventCode() {
            return -1;
        }
    }
}
