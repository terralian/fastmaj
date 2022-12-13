package com.github.terralian.fastmaj.game.validator.tehai;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.action.tehai.TehaiActionType;

/**
 * 弃牌动作判定器，什么时候都可以执行
 * 
 * @author terra.lian
 * @since 2022-10-26
 */
public class KiriValidator implements ITehaiActionValidator {

    @Override
    public boolean resolveAction(int position, GameConfig gameConfig, IGameCore gameCore) {
        return true;
    }

    @Override
    public TehaiActionType getTehaiActionType() {
        return TehaiActionType.KIRI;
    }
}
