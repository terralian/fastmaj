package com.github.terralian.fastmaj.game.ryuuky;

/**
 * 表示流局
 * 
 * @author terra.lian
 * @since 2022-10-28
 */
public interface IRyuukyoku {

    /**
     * 获取流局名称
     */
    String getRyuukyokuName();

    /**
     * 是否是中途流局
     */
    default boolean isHalfway() {
        return false;
    }
}
