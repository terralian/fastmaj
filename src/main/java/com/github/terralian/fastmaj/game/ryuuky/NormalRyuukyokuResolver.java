package com.github.terralian.fastmaj.game.ryuuky;

import java.util.HashSet;
import java.util.Set;

import com.github.terralian.fastmaj.agari.IPointCalculator;
import com.github.terralian.fastmaj.agari.PointCalculator;
import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.PlayerHideStatus;

/**
 * 荒牌流局
 * 
 * @author terra.lian
 */
public class NormalRyuukyokuResolver implements IRyuukyokuResolver {

    /**
     * 分数计算器
     */
    private IPointCalculator pointCalculator;

    public NormalRyuukyokuResolver() {
        pointCalculator = new PointCalculator();
    }

    @Override
    public boolean validate(GameConfig gameConfig, IGameCore gameCore) {
        return gameCore.getYamaCountdown() <= 0;
    }

    @Override
    public void execute(GameConfig gameConfig, IGameCore gameCore) {
        // 点数增减
        int[] playerPoints = gameCore.getPlayerPoints();
        Set<Integer> tenpais = new HashSet<>();
        for (int i = 0; i < gameCore.getPlayerSize(); i++) {
            PlayerHideStatus playerHides = gameCore.getPlayerHide(i);
            if (playerHides.getSyanten() <= 0) {
                tenpais.add(i);
            }
        }
        pointCalculator.ryuukyokuTransfer(tenpais, 1000, playerPoints);
        gameCore.ryuukyoku(this, playerPoints);
        // 看是否连庄
        gameCore.setRenchan(gameCore.getPlayerHide(gameCore.getOya()).getSyanten() <= 0, false);
    }

    @Override
    public String getRyuukyokuName() {
        return "荒牌流局";
    }
}
