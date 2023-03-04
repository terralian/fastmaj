package com.github.terralian.fastmaj.yaku.h1;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.YakuNamePool;

/**
 * 门前清自摸
 * 
 * @author 作者: terra.lian
 * 
 */
public class Tsumo implements IYaku {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        // 非荣和且未副露
        return holder != null && !holder.isRon() && !tehai.isNaki();
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
