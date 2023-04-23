package com.github.terralian.fastmaj.yaku.h1;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.YakuNamePool;
import com.github.terralian.fastmaj.yaku.meta.RequestContextYaku;
import com.github.terralian.fastmaj.yama.DrawFrom;

/**
 * 海底摸月
 * <p/>
 * 海底摸月为山牌的最后一枚牌自摸（非岭上牌）
 *
 * @author 作者: terra.lian
 */
@RequestContextYaku
public class Haitei implements IYaku {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        if (holder == null) {
            return false;
        }
        // 岭上，海底，河底
        DrawFrom lastDrawFrom = holder.getLastDrawFrom();
        return lastDrawFrom == DrawFrom.YAMA && holder.getYamaCountdown() == 0 && !holder.isRon();
    }

    @Override
    public String getName() {
        return YakuNamePool.Haitei;
    }

    @Override
    public int getHan(boolean isNaki) {
        return 1;
    }
}
