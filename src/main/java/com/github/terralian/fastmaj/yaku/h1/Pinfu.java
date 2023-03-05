package com.github.terralian.fastmaj.yaku.h1;

import java.util.List;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.encode.EncodeMark;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.hai.HaiPool;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.YakuNamePool;
import com.github.terralian.fastmaj.yaku.meta.MenchanYaku;
import com.github.terralian.fastmaj.yaku.meta.ShunzuYaku;


/**
 * 平和
 * <p/>
 * 平和需要知道自风场风，也可可脱离场况，当无场况上下文时不验证场风自风
 *
 * @author 作者: terra.lian
 */
@MenchanYaku
@ShunzuYaku(minShunzuSize = 4)
public class Pinfu implements IYaku {

    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        IHai agariHai = tehai.getDrawHai();
        // 平和不能副露暗刻
        if (tehai.isNaki() || tehai.isAnnkan() || divide == null || agariHai == null) {
            return false;
        }
        // 和了的牌不能是字牌
        if (agariHai.isJiHai()) {
            return false;
        }

        // 手上没有刻子，顺子为4（除却七对子）
        List<IHai> shunzuFirst = divide.getAllShunzuFirst();
        if (!divide.getAllKanKozuFirst().isEmpty() || shunzuFirst.size() != 4) {
            return false;
        }

        // 雀头非场风，自风，三元牌
        IHai jantou = divide.getJantou();
        if (jantou.isJiHai()) {
            if (holder == null //
                    || (holder.getBakaze() != null && jantou.getValue() == Encode34.toEncode34(holder.getBakaze())) //
                    || (holder.jikaze() != null && jantou.getValue() == Encode34.toEncode34(holder.jikaze())) //
                    || jantou.isSanGenHai()) {
                return false;
            }
        }

        // 4面，和的牌是两面听牌
        int agariLiteral = EncodeMark.clearRed(agariHai.getLiteral());

        // 当和了牌为3时，若为右侧，左侧搭子仅有12，非两面
        // 当和了牌为12时，则不可能为两面的右侧
        IHai minHai = agariLiteral < 4 //
                ? agariHai //
                : HaiPool.getByMark(agariLiteral - 2, agariHai.geHaiType());

        // 当和了牌>=7，若需要为两面只能是右侧待牌，第一枚为 -2
        // 当和了牌<7，最大的左侧牌可以为自身
        IHai maxHai = agariLiteral >= 7 //
                ? HaiPool.getByMark(agariLiteral - 2, agariHai.geHaiType())
                : agariHai;
        return Encode34.contains(shunzuFirst, minHai) || Encode34.contains(shunzuFirst, maxHai);
    }

    @Override
    public String getName() {
        return YakuNamePool.Pinfu;
    }

    @Override
    public int getHan(boolean isNaki) {
        return 1;
    }
}

