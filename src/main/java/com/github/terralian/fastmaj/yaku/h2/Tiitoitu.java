package com.github.terralian.fastmaj.yaku.h2;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.YakuNamePool;

/**
 * 七对子
 * 
 * @author terra.lian 
 */
public class Tiitoitu implements IYaku {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        // 全是对子，通过分割器实现
        return divide != null && divide.isTiitoitu();
    }

    @Override
    public String getName() {
        return YakuNamePool.Tiitoitu;
    }

    @Override
    public int getHan(boolean isNaki) {
        return 2;
    }

}
