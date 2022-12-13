package com.github.terralian.fastmaj.river;

import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.NakiEnum;

/**
 * 表示一个玩家的牌河
 * <p>
 * 该类定义为一个存储与管理玩家弃的牌的容器，提供对其他玩家鸣牌及查询弃牌的方法。
 * <p>
 * 同巡标识设置在牌河是由于是否同巡为玩家可知状态，当玩家不能看其他玩家手牌时，则只能通过牌河进行确认。
 * 
 * @author terra.lian
 */
public interface IHaiRiver {

    /**
     * 鸣牌河最末尾的那张牌
     * <p>
     * 内部会对该牌进行鸣牌标识，但是不会将牌从牌河中移除。展示时可以通过{@link IKiriHai}的属性进行判断
     * 
     * @param player 鸣牌玩家的坐席
     * @param nakiType 鸣牌类型
     */
    IHai naki(int player, NakiEnum nakiType);

    /**
     * 立直宣言时丢弃的牌
     * <p>
     * 牌会以横向表示，非双立直
     * 
     * @param hai 牌
     */
    default void reach(IHai hai) {
        reach(hai, false);
    }

    /**
     * 立直宣言时丢弃的牌
     * <p>
     * 牌会以横向表示
     * 
     * @param hai 牌
     * @param isDoubleReach 是否是双立直
     */
    void reach(IHai hai, boolean isDoubleReach);

    /**
     * 为牌河设置是否是同巡
     * 
     * @param sameJun 同巡标识，TRUE表示同巡
     */
    void setSameJun(boolean sameJun);

    /**
     * 设置第一巡是否同巡
     * 
     * @param sameFirstJun 第一巡同巡
     */
    void setSameFirstJun(boolean sameFirstJun);

    /**
     * 清空牌河，移除牌河内的元素
     */
    void clear();

    // -------------------------------------------------
    // 牌河的状态获取
    // -------------------------------------------------

    /**
     * 返回当前牌河的数量，被鸣的牌也会包含在统计内
     */
    int size();

    /**
     * 返回牌河是否为空，当牌河为空时返回<tt>true</tt>
     * 
     * @return 空返回<tt>true</tt>，否则返回<tt>false</tt>
     */
    boolean isEmpty();

    /**
     * 丢弃某张牌
     * 
     * @param hai 牌
     */
    void kiri(IHai hai);

    /**
     * 获取某张牌
     * 
     * @param index 牌河索引
     */
    IKiriHai get(int index);

    /**
     * 获取最后一枚弃牌
     * 
     * @return 若有弃牌则返回最后一枚
     */
    IKiriHai getLast();

    /**
     * 牌河是否有被鸣牌
     */
    boolean hasNaki();

    /**
     * 是否弃过某张牌
     * 
     * @param hai 手牌
     */
    boolean hasKiri(IHai hai);

    /**
     * 是否立直
     */
    boolean isReach();

    /**
     * 是否是两立直
     */
    boolean isDoubleReach();

    /**
     * 当前是否同巡
     */
    boolean isSameJun();

    /**
     * 第一巡是否同巡
     */
    boolean isSameFirstJun();

    // -------------------------------------------------
    // 其他
    // -------------------------------------------------

    /**
     * 将牌河转为链式操作，可以更好的处理牌河
     */
    default IChainHaiRiver chain() {
        return new ChainHaiRiver(this);
    }
}
