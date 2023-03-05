package com.github.terralian.fastmaj.yaku.hn;

import com.github.terralian.fastmaj.yaku.meta.BountyYaku;

/**
 * 里宝牌
 *
 * @author terra.lian
 */
@BountyYaku
public class UraDora extends NormalDora {

    public UraDora(int han) {
        super(han);
    }

    @Override
    public String getName() {
        return "里宝牌" + getHan(false);
    }
}
