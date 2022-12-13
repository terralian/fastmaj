package com.github.terralian.fastmaj.yaku.h1;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.YakuNamePool;

/**
 * 断幺九
 * 
 * @author 作者: terra.lian
 * 
 */
public class Tanyaotyu implements IYaku {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        for (IHai hai : tehai.getAll()) {
            if (hai.isYaotyuHai()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getName() {
        return YakuNamePool.Tanyaotyu;
    }

    @Override
    public int getHan(boolean isNaki) {
        return 1;
    }
}

