package com.github.terralian.fastmaj.yaku.h1;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.action.tehai.TehaiActionType;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.YakuNamePool;
import com.github.terralian.fastmaj.yaku.meta.RonYaku;
import com.github.terralian.fastmaj.yaku.meta.StateYaku;

/**
 * 抢杠
 * <p/>
 * 抢杠为当其他家进行加杠操作时，该枚牌为和了牌时的役种
 * <p/>
 * 该役的计算时点应该是，在对方操作抢杠时，对其他听牌且未为振听的一方，计算一次和了判定，若未和牌，修改一发巡标识为否。
 *
 * @author 作者: terra.lian
 */
@StateYaku
@RonYaku
public class TyanKan implements IYaku {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        if (holder == null || !holder.isRon() || holder.getLastTehaiActionType() == null) {
            return false;
        }
        // 当荣和时，在加杠和暗杠会出现抢杠（暗杠特指国士，虽然计抢杠，但是国士无视非普通役，所以这里不计）
        return holder.getLastTehaiActionType() == TehaiActionType.ANNKAN // 暗杠
                || holder.getLastTehaiActionType() == TehaiActionType.KAKAN;
    }

    @Override
    public String getName() {
        return YakuNamePool.TyanKan;
    }

    @Override
    public int getHan(boolean isNaki) {
        return 1;
    }
}