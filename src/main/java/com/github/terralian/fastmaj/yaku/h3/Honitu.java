package com.github.terralian.fastmaj.yaku.h3;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.hai.HaiTypeEnum;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.YakuNamePool;
import com.github.terralian.fastmaj.yaku.meta.JihaiYaku;

/**
 * 混一色
 *
 * @author terra.lian
 */
@JihaiYaku
public class Honitu implements IYaku {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        // 所有数牌花色相同，且有字牌
        boolean jihai = false;
        HaiTypeEnum haiType = null;
        for (IHai hai : tehai.getAll()) {
            if (hai.isJiHai()) {
                jihai = true;
            } else if (haiType == null) {
                haiType = hai.geHaiType();
            } else if (haiType != hai.geHaiType()) {
                return false;
            }
        }
        return jihai;
    }

    @Override
    public String getName() {
        return YakuNamePool.Honitu;
    }

    @Override
    public int getHan(boolean isNaki) {
        return isNaki ? 2 : 3;
    }

}
