package com.github.terralian.fastmaj.yaku;

import java.util.Collection;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;

/**
 * 役接口
 * <p>
 * 该接口为役的顶层类，其实现类包含所有麻将相关的役种
 * <p>
 * 役种实现类的匹配方式各不相同，部分役判断仅需要手牌即可，而部分役的判断则与手牌无关，需要根据场况才能确定（如抢杠）。
 * 
 * @author terra.lian 
 */
public interface IYaku {

    /**
     * 匹配
     * 
     * @param tehai 手牌
     * @param divide 和牌分割，手牌少数情况下有多种分割
     * @param gameContext 玩家对局数据容器
     */
    boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext gameContext);

    /**
     * 获取役的名称（如：门前清自摸）
     * <p>
     * 该名称在一场游戏中是唯一的，可以作为唯一主键使用，该接口的实现类的{@link #equals(String)}方法一般需要根据该名称来判断
     * <p>
     * 实现类的役种名称一般通过{@link YakuNamePool}获取
     *
     * @see YakuNamePool
     */
    String getName();

    /**
     * 役代表的番数，该番数在役的构建时固定
     * <p>
     * 役的番数根据不同规则可能有所不同
     * 
     * @param isNaki 是否鸣牌
     */
    int getHan(boolean isNaki);

    /**
     * 是否是役满，仅役满{@link IYakuman}需要修改该方法
     * 
     * @return 若该役是役满，返回true，否则返回false
     */
    default boolean isYakuman() {
        return false;
    }

    /**
     * 根据役实例判断是否是该役
     * 
     * @param yaku 役实例
     */
    default boolean equals(IYaku yaku) {
        return equals(yaku.getName());
    }

    /**
     * 根据役名称判断是否与该役相等
     * 
     * @param yakuName 役名称
     * @see YakuNamePool
     */
    default boolean equals(String yakuName) {
        return getName().equals(yakuName);
    }

    /**
     * 统计役种共多少番数，仅按配置的符数计算，不区别役满
     * 
     * @param yakus 役满
     * @param isNaki 是否鸣牌
     */
    static int sumHan(Collection<IYaku> yakus, boolean isNaki) {
        return yakus.stream().mapToInt(k -> k.getHan(isNaki)).sum();
    }
}
