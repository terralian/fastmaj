package com.github.terralian.fastmaj.yaku.h1;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.YakuNamePool;

/**
 * 河底捞鱼
 * <p>
 * 河底为在牌山剩余为0（也是该局最后一打）
 * 
 * @author 作者: terra.lian
 * 
 */
public class Houtei implements IYaku {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        return holder.getYamaCountdown() == 0 && holder.isRon();
    }

    @Override
    public String getName() {
        return YakuNamePool.Houtei;
    }

    @Override
    public int getHan(boolean isNaki) {
        return 1;
    }
}
