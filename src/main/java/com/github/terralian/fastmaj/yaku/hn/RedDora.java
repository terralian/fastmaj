package com.github.terralian.fastmaj.yaku.hn;

import com.github.terralian.fastmaj.yaku.meta.BountyYaku;

/**
 * 红宝牌
 *
 * @author terra.lian
 */
@BountyYaku
public class RedDora extends NormalDora {

    public RedDora(int han) {
        super(han);
    }

    @Override
    public String getName() {
        return "红宝牌" + getHan(false);
    }
}
