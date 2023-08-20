package com.github.terralian.fastmaj.game;

import com.github.terralian.fastmaj.game.event.GameEvent;

/**
 * 游戏事件队列（单向队列），一个游戏内的所有动作可通过事件进行驱动，事件间按顺序进行消费，但是可进行插队添加新的事件。
 * 一个事件和它的下一个事件是存在关联性的，如摸牌后，必然需要进行一次手牌动作，而手牌动作又可以产生多种的后续。
 * 以此，通过事件的不断的消费并在消费者内生成新的事件，达到推动游戏循环前进的目的。
 *
 * @author terra.lian
 */
public interface IGameEventQueue {

    /**
     * 当前队列中尚未消费的事件数
     */
    int size();

    /**
     * 从队列中取出下一个事件，会先从优先队列中取事件，若优先队列为空则再从通常队列中取值
     *
     * @throws IllegalStateException 若没有下一个事件则抛出该异常
     */
    GameEvent next();

    /**
     * 将一个事件加入到通常队列
     *
     * @param gameEvent 事件集合
     */
    void addNormal(GameEvent gameEvent);

    /**
     * 将一个事件在执行delay个{@link #next()}事件之后加入到通常队列的队头，作为那时的下一个事件执行
     * <p/>
     * 该方法会在延迟[0,5]个事件后，加入到通常队列头，当延迟为1时，将在下一个事件执行后加入队列头；
     * 当延迟为2时，将在下下个事件执行后加入队列头。
     *
     * @param gameEvent 事件
     * @param delay 延迟数 [1,5]
     */
    void addDelay(GameEvent gameEvent, int delay);

    /**
     * 将一个事件加入到优先队列
     *
     * @param gameEvent 事件集合
     */
    void addPriority(GameEvent gameEvent);

    /**
     * 移除通常队列中的某种事件
     *
     * @param eventCode 事件编码
     */
    void removeNormal(int eventCode);

    /**
     * 移除优先队列中的某个事件
     *
     * @param eventCode 事件编码
     */
    void removePriority(int eventCode);

    /**
     * 移除所有剩下的事件
     */
    void clear();

    /**
     * 当前优先事件队列中存在多少个指定的事件
     *
     * @param eventCode 事件编码
     */
    int prioritySizeof(int eventCode);
}
