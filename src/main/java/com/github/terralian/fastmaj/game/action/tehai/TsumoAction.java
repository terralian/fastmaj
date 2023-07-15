package com.github.terralian.fastmaj.game.action.tehai;

import com.github.terralian.fastmaj.agari.AgariInfo;
import com.github.terralian.fastmaj.agari.IAgariCalculator;
import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.KyokuState;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.game.context.PlayerGameContextFactory;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;

import java.util.ArrayList;
import java.util.List;

/**
 * 自摸动作
 *
 * @author terra.lian
 */
public class TsumoAction extends AgariAction implements ITehaiAction {

    public TsumoAction(IAgariCalculator agariCalculator) {
        super(agariCalculator);
    }

    @Override
    public KyokuState doAction(TehaiActionValue actionParam, IGameCore gameCore, GameConfig gameOptions) {
        // 当前玩家坐席
        int position = actionParam.getActionPlayer();
        // 玩家的手牌
        ITehai tehai = gameCore.getTehai();
        // 根据当前玩家是否立直获取手牌
        List<IHai> doraHais = gameCore.getDoras();
        List<IHai> uraDoraHais = gameCore.getHaiRiver().isReach() ? gameCore.getUraDoras() : new ArrayList<>();
        // 计算和了信息
        PlayerGameContext context = PlayerGameContextFactory.buildByGameCore(position, gameOptions, gameCore);
        AgariInfo agariInfo = agariCalculator.calculate(tehai, tehai.getDrawHai(), position, doraHais, uraDoraHais, context);
        // 自摸动作
        gameCore.agari(position, position, agariInfo.getYakus(), agariInfo.getBan(), agariInfo.getFu(), agariInfo.getScore(),
                agariInfo.getIncreaseAndDecrease());
        // 当自摸玩家为庄家时，连庄
        boolean isRenchan = position == gameCore.getOya();
        gameCore.setRenchan(isRenchan, !isRenchan);
        // 自摸结束对局
        gameCore.endKyoku();

        return KyokuState.END;
    }

    @Override
    public TehaiActionType getTehaiActionType() {
        return TehaiActionType.TSUMO;
    }
}
