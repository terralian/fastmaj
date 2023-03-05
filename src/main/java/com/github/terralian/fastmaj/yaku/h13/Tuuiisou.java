package com.github.terralian.fastmaj.yaku.h13;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYakuman;
import com.github.terralian.fastmaj.yaku.YakuNamePool;
import com.github.terralian.fastmaj.yaku.meta.JihaiYaku;

/**
 * 字一色
 *
 * @author terra.lian
 */
@JihaiYaku(minJihaiSize = 14)
public class Tuuiisou implements IYakuman {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        for (IHai hai : tehai.getAll()) {
            if (!hai.isJiHai()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getName() {
        return YakuNamePool.Tuuiisou;
    }
}

