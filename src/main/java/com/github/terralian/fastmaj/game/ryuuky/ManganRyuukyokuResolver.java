package com.github.terralian.fastmaj.game.ryuuky;

import java.util.List;

import com.github.terralian.fastmaj.agari.IPointCalculator;
import com.github.terralian.fastmaj.agari.PointCalculator;
import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.river.IHaiRiver;

/**
 * 流局满贯
 * 
 * @author terra.lian
 */
public class ManganRyuukyokuResolver implements IRyuukyokuResolver {

    /**
     * 分数计算器
     */
    private IPointCalculator pointCalculator;

    /**
     * 初始化构建流满计算器
     */
    public ManganRyuukyokuResolver() {
        pointCalculator = new PointCalculator();
    }

    @Override
    public boolean validate(GameConfig gameConfig, IGameCore gameCore) {
        // 牌山枚数剩余0时进行校验
        if (gameCore.getYamaCountdown() > 0) {
            return false;
        }
        // 没被鸣牌，且全都是幺九
        List<IHaiRiver> haiRivers = gameCore.getHaiRivers();
        return haiRivers.stream().anyMatch(k -> isRyuukyokuMangan(k));
    }

    /**
     * 存在一个牌河，没有被鸣牌，且所有的弃牌都是幺九牌
     * 
     * @param haiRiver 牌河
     */
    private boolean isRyuukyokuMangan(IHaiRiver haiRiver) {
        if (haiRiver.hasNaki()) {
            return false;
        }
        for (int i = 0; i < haiRiver.size(); i++) {
            if (!haiRiver.get(i).getValue().isYaotyuHai()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void execute(GameConfig gameConfig, IGameCore gameCore) {
        int[] playerPoints = gameCore.getPlayerPoints();
        // 对每家都判定并计算得分，若多家流满，则分别计算
        // 不同规则下，流满的分数计算会不一样。如雀魂计三麻自摸损，不计场供；雀龙门正好相反。
        // 这里使用天凤/雀魂规则
        // 假设三麻三家流满，庄8000，闲家6000
        // 则庄分数 = (+8000) (-4000) (-4000) = 0
        // 闲家分数 = (+6000) (-4000) (-2000) = 0
        for (int i = 0; i < gameCore.getPlayerSize(); i++) {
            IHaiRiver haiRiver = gameCore.getHaiRiver(i);
            if (!isRyuukyokuMangan(haiRiver)) {
                continue;
            }
            // 将满贯的分数在各家分配转移
            pointCalculator.ryuukyokuTransfer(i, gameCore.getOya(), 2000, playerPoints);
        }
        gameCore.ryuukyoku(this, playerPoints);
        // 庄家听牌则连庄
        boolean isOyaRenchan = gameCore.getPlayerHide(gameCore.getOya()).getSyanten() == 0;
        gameCore.setRenchan(isOyaRenchan, false);
    }

    @Override
    public String getRyuukyokuName() {
        return "流局满贯";
    }
}
