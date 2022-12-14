package com.github.terralian.fastmaj.game.validator.river;

import java.util.List;
import java.util.Set;

import com.github.terralian.fastmaj.FastMajong;
import com.github.terralian.fastmaj.agari.IRonYakuMatcher;
import com.github.terralian.fastmaj.agari.RonYakuMatcher;
import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.PlayerHideStatus;
import com.github.terralian.fastmaj.game.action.river.RiverActionType;
import com.github.terralian.fastmaj.game.action.tehai.TehaiActionType;
import com.github.terralian.fastmaj.game.action.tehai.TehaiActionValue;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ISyantenCalculator;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.tehai.IYuukouhaiCalculator;
import com.github.terralian.fastmaj.yaku.IYaku;

/**
 * 计算荣和动作是否可执行
 * 
 * @author terra.lian
 */
public class RonValidator implements IRiverActionValidator {

    /**
     * 有效牌计算器
     */
    private IYuukouhaiCalculator yuukouhaiCalculator;
    /**
     * 向听计算器
     */
    private ISyantenCalculator syantenCalculator;
    /**
     * 荣和役计算器
     */
    private IRonYakuMatcher ronYakuMatcher;

    public RonValidator() {
        this.yuukouhaiCalculator = FastMajong.doGetYuukouhaiCalculator();
        this.syantenCalculator = FastMajong.doGetSyantenCalculator();
        this.ronYakuMatcher = new RonYakuMatcher();
    }

    @Override
    public boolean resolveAction(int position, TehaiActionValue rivalTehaiAction, GameConfig gameConfig, IGameCore gameCore,
            PlayerGameContext context) {
        // 未听牌或者振听
        PlayerHideStatus hideStatus = gameCore.getPlayerHide(position);
        if (hideStatus.getSyanten() > 0 || hideStatus.isFuriten()) {
            return false;
        }
        // 听的牌是这几枚
        ITehai tehai = gameCore.getTehai(position);
        Set<IHai> agariHais = yuukouhaiCalculator.calcMin(tehai);
        if (!Encode34.contains(agariHais, rivalTehaiAction.getActionHai())) {
            return false;
        }
        // 抢暗杠
        // 对手动作是暗杠，且自己未固定牌，且国士听牌
        if (rivalTehaiAction.getActionType() == TehaiActionType.ANNKAN && tehai.getLock().isEmpty()) {
            int syanten = syantenCalculator.calcKokusi(tehai.getHand());
            return syanten == 0;
        }
        // 抢加杠
        // 只要听这张，且非振听
        else if (rivalTehaiAction.getActionType() == TehaiActionType.KAKAN) {
            return true;
        }
        // 弃牌，得判断手牌是否有役
        List<IYaku> yakus = ronYakuMatcher.match(tehai, rivalTehaiAction.getActionHai(), context);
        return !yakus.isEmpty();
    }

    @Override
    public RiverActionType getRiverActionType() {
        return RiverActionType.RON;
    }
}
