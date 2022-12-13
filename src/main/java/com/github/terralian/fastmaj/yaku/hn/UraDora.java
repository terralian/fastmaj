package com.github.terralian.fastmaj.yaku.hn;

/**
 * 里宝牌
 * 
 * @author terra.lian
 */
public class UraDora extends NormalDora {

    public UraDora(int han) {
        super(han);
    }

    @Override
    public String getName() {
        return "里宝牌" + getHan(false);
    }
}
