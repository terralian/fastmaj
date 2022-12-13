package com.github.terralian.fastmaj.game.ryuuky;

import java.util.List;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.river.IHaiRiver;

/**
 * 四风连打
 * 
 * @author terra.lian
 */
public class FourKazeRyuukyokuResolver implements IRyuukyokuResolver, IHalfwayRyuukyoku {

    @Override
    public boolean validate(GameConfig gameConfig, IGameCore gameCore) {
        // 仅在4人麻将生效
        if (gameCore.getPlayerSize() != 4) {
            return false;
        }
        List<IHaiRiver> haiRivers = gameCore.getHaiRivers();
        IHai hai = null;
        for (IHaiRiver haiRiver : haiRivers) {
            // 需要第一巡同巡（未吃碰杠，暗杠）
            // 只在第一枚牌时判定
            if (!haiRiver.isSameFirstJun() || haiRiver.size() != 1) {
                return false;
            }

            // 校验第一枚是否为风牌
            IHai kiriHai = haiRiver.get(0).getValue();
            if (!kiriHai.isKazeHai()) {
                return false;
            }

            // 校验打的牌是否相同
            if (hai == null) {
                hai = kiriHai;
            } else if (!hai.equals(kiriHai)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void execute(GameConfig gameConfig, IGameCore gameCore) {
        gameCore.ryuukyoku(this, gameCore.getPlayerPoints());
        // 连庄
        gameCore.setRenchan(true, false);
    }

    @Override
    public String getRyuukyokuName() {
        return "四风连打";
    }
}
