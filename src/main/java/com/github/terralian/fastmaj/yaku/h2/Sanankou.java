package com.github.terralian.fastmaj.yaku.h2;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.YakuNamePool;
import com.github.terralian.fastmaj.yaku.meta.KozuYaku;

/**
 * 三暗刻
 *
 * @author terra.lian
 */
@KozuYaku
public class Sanankou implements IYaku {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        // 手牌的暗刻数 + 暗杠数 = 3
        return divide.getAnnkoFirst().size() + tehai.getLock().getAnnkanFirst().size() == 3;
    }

    @Override
    public String getName() {
        return YakuNamePool.Sanankou;
    }

    @Override
    public int getHan(boolean isNaki) {
        return 2;
    }

}
