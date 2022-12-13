package com.github.terralian.fastmaj.game.validator.tehai;

import java.util.Set;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.action.tehai.TehaiActionType;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.tehai.IYuukouhaiCalculator;
import com.github.terralian.fastmaj.tehai.YuukouhaiCalculator;

/**
 * 暗杠动作可执行判断
 * 
 * @author terra.lian
 */
public class AnnkanValidator implements ITehaiActionValidator {

    /**
     * 有效牌计算器
     */
    private IYuukouhaiCalculator yuukouhaiCalculator;

    /**
     * 初始化构建暗杠判定器
     */
    public AnnkanValidator() {
        yuukouhaiCalculator = new YuukouhaiCalculator();
    }

    @Override
    public boolean resolveAction(int position, GameConfig gameConfig, IGameCore gameCore) {
        // 需要牌山还有可摸的牌
        // 宝牌数需要小于5，即没有人操作开四杠
        if (gameCore.getYamaCountdown() <= 0 || gameCore.getDoraDisplays().size() >= 5) {
            return false;
        }
        ITehai tehai = gameCore.getTehai(position);
        // 若未立直，则只需判定能否暗杠
        if (!gameCore.getHaiRiver(position).isReach()) {
            return tehai.canAnkan();
        }
        // 若已立直，则需要判定摸到的牌是否可以开暗杠，且开暗杠后，听的牌是否会变更
        if (!tehai.canAnkan(tehai.getDrawHai())) {
            return false;
        }
        return isYuukouhaiNotChangeAfterAnnkan(tehai);
    }

    @Override
    public TehaiActionType getTehaiActionType() {
        return TehaiActionType.ANNKAN;
    }

    /**
     * 判定开杠后，有效牌是否不会变化
     * 
     * @param tehai 手牌
     */
    private boolean isYuukouhaiNotChangeAfterAnnkan(ITehai tehai) {
        ITehai tehaiClone = tehai.deepClone();
        IHai drawHai = tehaiClone.kiriLast();
        Set<IHai> beforeYuukouHais = yuukouhaiCalculator.calcMin(tehaiClone);
        tehaiClone.draw(drawHai);
        tehaiClone.annkan(drawHai);
        Set<IHai> afterYuukouHais = yuukouhaiCalculator.calcMin(tehaiClone);
        return beforeYuukouHais.size() == afterYuukouHais.size() && beforeYuukouHais.containsAll(afterYuukouHais);
    }
}
