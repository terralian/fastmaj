package com.github.terralian.fastmaj.game.ryuuky;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;

/**
 * 管理除九种九牌外的所有流局类
 * 
 * @author terra.lian
 */
public interface IRyuukyokuResolverManager {

    /**
     * 判定并执行流局，当流局类判定成功时，会执行对应的流局方法，并返回true;当所有流局都判定失败时，跳过，返回true
     * 
     * @param gameConfig 游戏配置
     * @param gameCore 游戏核心
     */
    IRyuukyokuResolver resolve(GameConfig gameConfig, IGameCore gameCore);
}
