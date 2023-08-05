package com.github.terralian.fastmaj.game.ryuuky;

import java.util.List;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.action.river.RiverActionType;
import com.github.terralian.fastmaj.game.event.river.RiverActionEvent;
import com.github.terralian.fastmaj.util.EmptyUtil;

/**
 * 三家和了，天凤使用的特殊规则，当一个对局中有3人同时荣和，做流局处理（非中途流局）需要看庄家是否听牌。
 *
 * @author terra.lian
 * @since 2022-12-05
 */
public class Ron3RyuukyokuResolver implements IRyuukyoku {

    /**
     * 判定是否三家和了
     *
     * @param actions 玩家动作（同优先级）
     * @param gameConfig 游戏配置
     * @param gameCore 游戏核心
     */
    public boolean validate(List<RiverActionEvent> actions, GameConfig gameConfig, IGameCore gameCore) {
        // 不使用三家和了的情况
        if (!gameConfig.isUseRon3Ryuukyoku()) {
            return false;
        }
        // 玩家无动作，或者没有3人
        if (EmptyUtil.isEmpty(actions) || actions.size() < 3) {
            return false;
        }
        // 玩家动作非荣和
        RiverActionType riverActionType = actions.get(0).getRiverType();
        if (riverActionType != RiverActionType.RON) {
            return false;
        }
        return true;
    }

    /**
     * 处理三家和了，执行流局操作。内部会按规则判别是否使用三家和了。动作优先级需要由外部设置，传入的动作需要为过滤后的同优先级动作
     *
     * @param gameConfig 游戏配置
     * @param gameCore 游戏核心
     */
    public void execute(GameConfig gameConfig, IGameCore gameCore) {
        // 执行流局操作
        gameCore.ryuukyoku(this, gameCore.getPlayerPoints());
        // 庄家听牌则连庄
        boolean isOyaRenchan = gameCore.getPlayerHide(gameCore.getOya()).getSyaten() == 0;
        gameCore.setRenchan(isOyaRenchan, false);
    }

    @Override
    public String getRyuukyokuName() {
        return "三家和了";
    }
}
