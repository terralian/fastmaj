package com.github.terralian.fastmaj.game.validator.tehai;

import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.action.tehai.TehaiActionType;
import com.github.terralian.fastmaj.river.IHaiRiver;
import com.github.terralian.fastmaj.tehai.ITehai;

/**
 * 九种九牌动作判定
 * 
 * @author terra.lian
 */
public class Ryuukyoku99Validator implements ITehaiActionValidator {

    @Override
    public boolean resolveAction(int position, GameConfig gameConfig, IGameCore gameCore) {
        IHaiRiver haiRiver = gameCore.getHaiRiver(position);
        // 需要第一巡同巡，即在第一巡时没人进行鸣牌暗杠
        // 且手牌的幺九牌数量 >= 9
        if (haiRiver.isSameFirstJun() && haiRiver.isEmpty()) {
            ITehai tehai = gameCore.getTehai(position);
            int[] value34 = Encode34.toEncode34(tehai.getAll());
            int type = 0;
            for (int i = 0; i < Encode34.YAOTYU_HAIS.length; i++) {
                if (value34[Encode34.YAOTYU_HAIS[i]] > 0) {
                    type++;
                }
            }
            return type >= 9;
        }
        return false;
    }

    @Override
    public TehaiActionType getTehaiActionType() {
        return TehaiActionType.RYUUKYOKU99;
    }
}
