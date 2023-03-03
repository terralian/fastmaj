package com.github.terralian.fastmaj.yaku;

/**
 * 役种中的役满
 * <p/>
 * 一般规则有规定部分役满是双倍役满，役满的番数固定为13番
 * 
 * @author terra.lian 
 */
public interface IYakuman extends IYaku {

    /**
     * 是否双倍役满
     */
    default boolean isDoubleYakuman() {
        return false;
    }

    /**
     * 役满的番数固定为13番，特例累计役满的番数则需要根据实际值确定
     */
    @Override
    default int getHan(boolean isNaki) {
        return 13;
    }

    /**
     * 是否役满，返回true
     * 
     * @return true
     */
    @Override
    default boolean isYakuman() {
        return true;
    }
}
