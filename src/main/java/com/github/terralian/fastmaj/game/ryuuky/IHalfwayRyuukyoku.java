package com.github.terralian.fastmaj.game.ryuuky;

/**
 * 是否是中途流局
 * 
 * @author terra.lian
 */
public interface IHalfwayRyuukyoku extends IRyuukyoku {

    /**
     * 是中途流局
     */
    @Override
    default boolean isHalfway() {
        return true;
    }
}
