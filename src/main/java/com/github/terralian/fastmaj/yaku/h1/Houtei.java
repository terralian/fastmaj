package com.github.terralian.fastmaj.yaku.h1;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.YakuNamePool;
import com.github.terralian.fastmaj.yaku.meta.RonYaku;
import com.github.terralian.fastmaj.yaku.meta.RequestContextYaku;
import com.github.terralian.fastmaj.yama.DrawFrom;

/**
 * 河底捞鱼
 * <p/>
 * 河底需要对手打出一枚牌，而非抢杠。
 *
 * @author 作者: terra.lian
 */
@RonYaku
@RequestContextYaku
public class Houtei implements IYaku {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        // 海底，河底，抢杠
        DrawFrom drawFrom = holder.getLastDrawFrom();
        return holder.getYamaCountdown() == 0 && holder.isRon() && drawFrom == DrawFrom.YAMA;
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
