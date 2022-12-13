package com.github.terralian.fastmaj.yaku.h13;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.tehai.ITehaiLock;
import com.github.terralian.fastmaj.yaku.IYakuman;
import com.github.terralian.fastmaj.yaku.YakuNamePool;

/**
 * 四杠子
 * 
 * @author 作者: terra.lian
 * 
 */
public class Suukantu implements IYakuman {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        ITehaiLock tehaiLock = tehai.getLock();
        return tehaiLock.kantsuSize() == 4;
    }

    @Override
    public String getName() {
        return YakuNamePool.Suukantu;
    }
}
