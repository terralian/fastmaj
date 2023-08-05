package com.github.terralian.fastmaj.yaku.h13;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.river.IHaiRiver;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYakuman;
import com.github.terralian.fastmaj.yaku.YakuNamePool;
import com.github.terralian.fastmaj.yaku.meta.RequestContextYaku;

/**
 * 地和
 *
 * @author terra.lian
 */
@RequestContextYaku
public class TiiHo implements IYakuman {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        if (holder == null || holder.getHaiRiver() == null) {
            return false;
        }
        IHaiRiver haiRiver = holder.getHaiRiver();
        return !holder.isOya() // 非庄家
                && haiRiver.isEmpty() // 没有打过牌
                && haiRiver.isSameFirstJun() // 第一巡同巡
                && tehai.getLock().isEmpty() // 未鸣牌且未暗杠
                && tehai.countKita() == 0 // 未拔北
                && !holder.isRon() // 非荣和
                ;
    }

    @Override
    public String getName() {
        return YakuNamePool.TiiHo;
    }
}
