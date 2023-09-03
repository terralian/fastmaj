package com.github.terralian.fastmaj.yaku.h1;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.context.IPlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.YakuNamePool;
import com.github.terralian.fastmaj.yaku.meta.MenchanYaku;

/**
 * 门前清自摸
 *
 * @author 作者: terra.lian
 */
@MenchanYaku
public class Tsumo implements IYaku {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, IPlayerGameContext holder) {
        // 非荣和且未副露
        return holder != null && !tehai.isNaki() && !holder.isEndByRon();
    }

    @Override
    public String getName() {
        return YakuNamePool.Tsumo;
    }

    @Override
    public int getHan(boolean isNaki) {
        return 1;
    }
}
