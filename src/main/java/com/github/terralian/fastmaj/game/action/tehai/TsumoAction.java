package com.github.terralian.fastmaj.game.action.tehai;

import java.util.ArrayList;
import java.util.List;

import com.github.terralian.fastmaj.agari.AgariInfo;
import com.github.terralian.fastmaj.agari.IAgariCalculator;
import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.IGameEventQueue;
import com.github.terralian.fastmaj.game.KyokuState;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.game.context.PlayerGameContextFactory;
import com.github.terralian.fastmaj.game.event.GameEvent;
import com.github.terralian.fastmaj.game.event.GameEventCode;
import com.github.terralian.fastmaj.game.event.handler.IGameEventHandler;
import com.github.terralian.fastmaj.game.event.system.CommonSystemEventPool;
import com.github.terralian.fastmaj.game.event.system.SystemEventCode;
import com.github.terralian.fastmaj.game.event.tehai.TehaiActionEvent;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;

/**
 * 自摸动作
 *
 * @author terra.lian
 */
public class TsumoAction extends AgariAction implements ITehaiAction, IGameEventHandler {

    public TsumoAction(IAgariCalculator agariCalculator) {
        super(agariCalculator);
    }

    @Override
    public KyokuState doAction(TehaiActionEvent actionParam, IGameCore gameCore, GameConfig gameOptions) {
        // 当前玩家坐席
        int position = actionParam.getPosition();
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

    @Override
    public int handleEventCode() {
        return GameEventCode.TSUMO;
    }

    @Override
    public void handle(GameEvent gameEvent, IGameCore gameCore, GameConfig gameConfig, IGameEventQueue eventQueue) {
        // 产生对局结束优先事件
        eventQueue.addPriority(CommonSystemEventPool.get(SystemEventCode.KYOKU_END));
    }
}
