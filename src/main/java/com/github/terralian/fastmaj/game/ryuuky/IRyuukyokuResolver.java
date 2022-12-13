package com.github.terralian.fastmaj.game.ryuuky;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;

/**
 * 流局判定及处理
 * 
 * @author terra.lian
 */
public interface IRyuukyokuResolver extends IRyuukyoku {

    /**
     * 判定流局，若为判定成功，返回TRUE。
     * 
     * @param gameConfig 游戏配置
     * @param gameCore 游戏核心
     */
    boolean validate(GameConfig gameConfig, IGameCore gameCore);

    /**
     * 执行流局
     * 
     * @param gameConfig 游戏配置
     * @param gameCore 游戏核心
     */
    void execute(GameConfig gameConfig, IGameCore gameCore);
}
