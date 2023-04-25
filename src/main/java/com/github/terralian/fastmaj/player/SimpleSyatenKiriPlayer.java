package com.github.terralian.fastmaj.player;

import java.util.Set;

import com.github.terralian.fastmaj.FastMajong;
import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.game.action.river.RiverActionType;
import com.github.terralian.fastmaj.game.action.tehai.TehaiActionType;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.game.event.tehai.KiriEvent;
import com.github.terralian.fastmaj.game.event.tehai.ReachEvent;
import com.github.terralian.fastmaj.game.event.tehai.TehaiActionEvent;
import com.github.terralian.fastmaj.game.event.tehai.TsumoEvent;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ISyatenCalculator;
import com.github.terralian.fastmaj.tehai.ITehai;

/**
 * 按照向听数，从左到右选择一个能使向听数减少的牌，若没有则模切。不会吃碰杠，会立直和了
 * 
 * @author terra.lian
 */
public class SimpleSyatenKiriPlayer implements IPlayer {

    private ISyatenCalculator syatenCalculator;

    public SimpleSyatenKiriPlayer() {
        syatenCalculator = FastMajong.doGetSyatenCalculator();
    }

    @Override
    public TehaiActionEvent drawHai(ITehai tehai, Set<TehaiActionType> enableActions, PlayerGameContext context) {
        // 能和了则和了
        if (enableActions.contains(TehaiActionType.TSUMO)) {
            return new TsumoEvent();
        }

        // 按顺序，选一枚能使向听数减小的牌
        IHai candidate = null;
        int syaten = context.getSyaten();
        int[] hands = Encode34.toEncode34(tehai.getHand());
        for (IHai hai : tehai.getHand()) {
            hands[hai.getValue()]--;
            int newSyaten = syatenCalculator.calcMin(hands);
            if (newSyaten <= syaten) {
                candidate = hai;
                break;
            }
            hands[hai.getValue()]++;
        }
        // 没有能够减小向听数的牌，模切
        if (candidate == null) {
            return new KiriEvent().setKiriHai(tehai.getDrawHai());
        }
        // 能立直则立直
        if (enableActions.contains(TehaiActionType.REACH)) {
            return new ReachEvent().setReachHai(candidate);
        }
        // 手切向听数减小的牌
        return new KiriEvent().setKiriHai(candidate);
    }

    @Override
    public RiverActionCall nakiOrRon(int fromPosition, TehaiActionType fromDealAction, Set<RiverActionType> enableActions,
            PlayerGameContext context) {
        if (enableActions.contains(RiverActionType.RON)) {
            return RiverActionCall.RON;
        }
        return null;
    }
}
