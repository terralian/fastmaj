package com.github.terralian.fastmaj.yaku.hn;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.meta.BountyYaku;

/**
 * 宝牌
 *
 * @author terra.lian
 */
@BountyYaku
public class NormalDora implements IYaku {

    private int han;

    public NormalDora(int han) {
        this.han = han;
    }

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext gameContext) {
        // 仅通过自构建，而不能通过匹配
        return false;
    }

    @Override
    public String getName() {
        return "宝牌" + getHan(false);
    }

    @Override
    public int getHan(boolean isNaki) {
        return han;
    }
}
