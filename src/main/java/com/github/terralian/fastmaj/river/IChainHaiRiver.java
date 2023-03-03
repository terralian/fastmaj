package com.github.terralian.fastmaj.river;

import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.NakiEnum;

/**
 * 链式牌河，用于更遍历的操作设置方法。
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author terra.lian
 * @since 2022-11-03
 */
public interface IChainHaiRiver {

    /**
     * 丢弃某张牌
     * 
     * @param hai 牌
     */
    IChainHaiRiver kiri(IHai hai);

    /**
     * 立直宣言时丢弃的牌
     * <p/>
     * 牌会以横向表示
     * 
     * @param hai 牌
     */
    IChainHaiRiver reach(IHai hai);

    /**
     * 立直宣言时丢弃的牌
     * <p/>
     * 牌会以横向表示
     * 
     * @param hai 牌
     * @param isDoubleReach 是否是双立直
     */
    IChainHaiRiver reach(IHai hai, boolean isDoubleReach);

    /**
     * 鸣牌河最末尾的那张牌
     * <p/>
     * 内部会对该牌进行鸣牌标识，但是不会将牌从牌河中移除。展示时可以通过{@link IKiriHai}的属性进行判断
     * 
     * @param player 鸣牌玩家的坐席
     * @param nakiType 鸣牌类型
     */
    IChainHaiRiver naki(int player, NakiEnum nakiType);

    /**
     * 为牌河设置是否是同巡
     * 
     * @param sameJun 同巡标识，TRUE表示同巡
     */
    IChainHaiRiver setSameJun(boolean sameJun);

    /**
     * 设置第一巡是否同巡
     * 
     * @param sameFirstJun 第一巡同巡
     */
    IChainHaiRiver setSameFirstJun(boolean sameFirstJun);

    /**
     * 清空牌河，移除牌河内的元素
     */
    IChainHaiRiver clear();
}
