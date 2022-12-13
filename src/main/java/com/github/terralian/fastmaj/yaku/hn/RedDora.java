package com.github.terralian.fastmaj.yaku.hn;

/**
 * 红宝牌
 * 
 * @author terra.lian
 */
public class RedDora extends NormalDora {

    public RedDora(int han) {
        super(han);
    }

    @Override
    public String getName() {
        return "红宝牌" + getHan(false);
    }
}
