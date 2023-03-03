package com.github.terralian.fastmaj.game.ryuuky;

import java.util.List;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.tehai.ITehai;

/**
 * 四杠散了. 同一人开四杠不算四杠散了，开后其他人不允许再开杠。不同人开四杠计四杠散了
 * 
 * @author terra.lian
 */
public class FourKanRyuukyokuResolver implements IRyuukyokuResolver, IHalfwayRyuukyoku {

    @Override
    public boolean validate(GameConfig gameConfig, IGameCore gameCore) {
        int total = 0;
        int kantsuPlayer = 0;
        List<ITehai> tehais = gameCore.getTehais();
        for (ITehai tehai : tehais) {
            int kantsuSize = tehai.getLock().getKanzuSize();
            if (kantsuSize > 0) {
                total += kantsuSize;
                kantsuPlayer += 1;
            }
        }
        return total >= 4 && kantsuPlayer > 1;
    }

    @Override
    public void execute(GameConfig gameConfig, IGameCore gameCore) {
        gameCore.ryuukyoku(this, gameCore.getPlayerPoints());
        // 连庄
        gameCore.setRenchan(true, false);
    }

    @Override
    public String getRyuukyokuName() {
        return "四杠散了";
    }
}
