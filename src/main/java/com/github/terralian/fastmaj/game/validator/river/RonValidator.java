package com.github.terralian.fastmaj.game.validator.river;

import java.util.ArrayList;
import java.util.List;

import com.github.terralian.fastmaj.agari.IRonYakuMatcher;
import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.action.river.RiverActionType;
import com.github.terralian.fastmaj.game.action.tehai.TehaiActionType;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.game.event.river.RonEvent;
import com.github.terralian.fastmaj.game.event.tehai.TehaiActionEvent;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.player.space.PlayerDefaultSpace;
import com.github.terralian.fastmaj.tehai.ISyatenCalculator;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;

/**
 * 计算荣和动作是否可执行
 * <p>
 * TODO 优化多次调用向听计算及手牌分割的问题
 *
 * @author terra.lian
 */
public class RonValidator implements IRiverActionValidator {

    /**
     * 向听计算器
     */
    private final ISyatenCalculator syatenCalculator;
    /**
     * 荣和役计算器
     */
    private final IRonYakuMatcher ronYakuMatcher;

    public RonValidator(ISyatenCalculator syatenCalculator, IRonYakuMatcher ronYakuMatcher) {
        this.syatenCalculator = syatenCalculator;
        this.ronYakuMatcher = ronYakuMatcher;
    }

    @Override
    public boolean resolveAction(int position, TehaiActionEvent rivalTehaiAction, GameConfig gameConfig,
            IGameCore gameCore,
            PlayerGameContext context) {
        // 未听牌或者振听
        if (gameCore.getSyaten(position) > 0 || gameCore.isFuriten(position)) {
            return false;
        }
        // 有效牌计算
        // 把牌加入到手牌中，需要使得向听数 = 和了
        ITehai tehai = gameCore.getTehai(position);
        List<IHai> hais = new ArrayList<>(tehai.getHand());
        hais.add(rivalTehaiAction.getIfHai());
        if (syatenCalculator.calcMin(hais) >= 0) {
            return false;
        }
        // 抢暗杠
        // 对手动作是暗杠，且自己未固定牌，且国士听牌
        if (rivalTehaiAction.getEventType() == TehaiActionType.ANNKAN && tehai.getLock().isEmpty()) {
            int syaten = syatenCalculator.calcKokusi(tehai.getHand());
            return syaten == 0;
        }
        // 抢加杠
        // 只要听这张，且非振听
        else if (rivalTehaiAction.getEventType() == TehaiActionType.KAKAN) {
            return true;
        }
        // 尝试为其设置荣标识，来匹配役种
        boolean isEndByRon = context.isEndByRon();
        context.setEndByRon(true);

        RonEvent ronEvent = new RonEvent()
                .setFrom(rivalTehaiAction.getPosition())
                .setPosition(position)
                .setFromHai(rivalTehaiAction.getIfHai());
        PlayerDefaultSpace defaultSpace = context.getSpace();
        defaultSpace.addIfAction(ronEvent);

        // 弃牌，得判断手牌是否有役
        List<IYaku> yakus = ronYakuMatcher.match(tehai, rivalTehaiAction.getIfHai(), context);
        // 还原标识
        context.setEndByRon(isEndByRon);
        defaultSpace.removeIfAction();

        return !yakus.isEmpty();
    }

    @Override
    public RiverActionType getRiverActionType() {
        return RiverActionType.RON;
    }
}
