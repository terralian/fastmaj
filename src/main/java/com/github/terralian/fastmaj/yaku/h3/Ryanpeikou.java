package com.github.terralian.fastmaj.yaku.h3;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.context.IPlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.YakuNamePool;
import com.github.terralian.fastmaj.yaku.meta.MenchanYaku;

/**
 * 二杯口
 *
 * @author terra.lian
 */
@MenchanYaku
public class Ryanpeikou implements IYaku {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, IPlayerGameContext holder) {
        // 门清且有两个三色同顺（通过分割器实现）
        return !tehai.isNaki() && divide != null && divide.isRyanpeikou();
    }

    @Override
    public String getName() {
        return YakuNamePool.Ryanpeikou;
    }

    @Override
    public int getHan(boolean isNaki) {
        return 3;
    }

}
