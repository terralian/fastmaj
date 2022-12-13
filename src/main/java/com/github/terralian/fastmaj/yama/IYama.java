package com.github.terralian.fastmaj.yama;

import java.util.List;

import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;

/**
 * 表示一个牌山，包含牌山内的所有牌
 * <p>
 * 
 * @author terra.lian 
 */
public interface IYama {

    // --------------------------
    // 初始化
    // --------------------------

    /**
     * 初始配牌，按玩家数分发
     * <p>
     * 模拟牌山搭好后，玩家从牌山拿牌的场景
     * 
     * @param oya 庄家位置（由庄家起手先拿）
     */
    List<ITehai> deals(int oya);

    /**
     * 初始化
     */
    void reset();

    // --------------------------
    // 从牌山摸牌/翻指示牌
    // --------------------------

    /**
     * 从牌山摸下一枚牌
     */
    IHai nextHai();

    /**
     * 从岭上摸下一枚牌
     */
    IHai nextTrumpHai();

    /**
     * 翻开下一枚宝牌指示牌
     */
    IHai nextDoraDisplay();

    // --------------------------
    // 获取信息
    // --------------------------

    /**
     * 获取牌山剩余枚数
     */
    int getCountdown();

    /**
     * 获取宝牌指示牌集合
     */
    List<IHai> getDoraDisplay();

    /**
     * 获取里宝牌指示牌集合
     */
    List<IHai> getUraDoraDisplay();

    /**
     * 获取牌山内的所有牌
     */
    List<IHai> getRaw();

    /**
     * 获取宝牌
     */
    List<IHai> getDoras();

    /**
     * 获取里宝牌
     */
    List<IHai> getUraDoras();

    // --------------------------
    // 牌山的构筑信息
    // --------------------------

    /**
     * 当前牌山偏移
     */
    int getOffset();

    /**
     * 当前王牌区偏移
     */
    int getTrumpOffset();
}
