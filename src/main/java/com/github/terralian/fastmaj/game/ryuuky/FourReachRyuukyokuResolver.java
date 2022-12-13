package com.github.terralian.fastmaj.game.ryuuky;

import java.util.List;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;

/**
 * 四家立直
 * 
 * @author terra.lian
 */
public class FourReachRyuukyokuResolver implements IRyuukyokuResolver, IHalfwayRyuukyoku {

    @Override
    public boolean validate(GameConfig gameConfig, IGameCore gameCore) {
        List<Boolean> isReachs = gameCore.getReachs();
        int i = 0;
        for (Boolean isReach : isReachs)
            if (isReach)
                i++;
        return i == 4;
    }

    @Override
    public void execute(GameConfig gameConfig, IGameCore gameCore) {
        gameCore.ryuukyoku(this, gameCore.getPlayerPoints());
        // 连庄
        gameCore.setRenchan(true, false);
    }

    @Override
    public String getRyuukyokuName() {
        return "四家立直";
    }
}
