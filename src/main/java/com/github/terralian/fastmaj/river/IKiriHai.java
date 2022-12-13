package com.github.terralian.fastmaj.river;

import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.NakiEnum;

/**
 * 表示一张牌河的的牌
 * <p>
 * 该类用于标记丢弃牌的属性，包含弃牌的位置，弃牌的状态（是否被吃碰杠），是否立直表示牌等
 * 
 * @author terra.lian 
 */
public interface IKiriHai {

    /**
     * 获取牌的实际信息
     */
    IHai getValue();

    /**
     * 弃牌在牌河的位置
     * 
     * @return [0, ~]
     */
    int kiriIndex();

    /**
     * 设置鸣牌类型
     * 
     * @param playerIndex 玩家枚举
     * @param nakiType 鸣牌类型
     */
    void setNaki(int playerIndex, NakiEnum nakiType);

    /**
     * 是否被鸣牌
     * 
     * @see #getNakiType()
     * @see #getNakiPlayer()
     */
    boolean isNaki();

    /**
     * 返回被鸣牌的行为，若未被鸣牌，返回null
     * 
     * @return null | NakiEnum
     * @see #isNaki()
     */
    NakiEnum getNakiType();

    /**
     * 返回鸣该牌的玩家坐席，若未被鸣牌，返回null
     * 
     * @return null | Integer([0, 3])
     * @see #isNaki()
     */
    Integer getNakiPlayer();

    /**
     * 清空鸣牌相关字段
     */
    void resetNaki();

    /**
     * 设置是否立直标识
     * 
     * @param isReach 是否立直
     */
    void setReachDisplay(boolean isReach);

    /**
     * 是否立直标识牌
     * <p>
     * 立直牌会被横向放置表示
     */
    boolean isReachDisplay();
}
