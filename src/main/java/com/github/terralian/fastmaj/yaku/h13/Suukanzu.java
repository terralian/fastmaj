package com.github.terralian.fastmaj.yaku.h13;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.tehai.ITehaiLock;
import com.github.terralian.fastmaj.yaku.IYakuman;
import com.github.terralian.fastmaj.yaku.YakuNamePool;
import com.github.terralian.fastmaj.yaku.meta.KozuYaku;

/**
 * 四杠子
 *
 * @author 作者: terra.lian
 */
@KozuYaku
public class Suukanzu implements IYakuman {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        ITehaiLock tehaiLock = tehai.getLock();
        return tehaiLock.kanzuSize() == 4;
    }

    @Override
    public String getName() {
        return YakuNamePool.Suukanzu;
    }
}
