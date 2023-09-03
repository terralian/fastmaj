package com.github.terralian.fastmaj.game.action.river;

import java.util.ArrayList;
import java.util.List;

import com.github.terralian.fastmaj.agari.AgariInfo;
import com.github.terralian.fastmaj.agari.IAgariCalculator;
import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.IGameEventQueue;
import com.github.terralian.fastmaj.game.action.tehai.AgariAction;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.game.context.PlayerGameContextFactory;
import com.github.terralian.fastmaj.game.event.GameEvent;
import com.github.terralian.fastmaj.game.event.river.RiverActionEvent;
import com.github.terralian.fastmaj.game.event.system.KyokuEndEvent;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;

/**
 * 荣和动作
 *
 * @author terra.lian
 */
public class RonAction extends AgariAction implements IRiverAction {

    public RonAction(IAgariCalculator agariCalculator) {
        super(agariCalculator);
    }

    /**
     * 执行荣和操作
     *
     * @param value 参数
     * @param gameConfig 游戏规则
     * @param gameCore 游戏核心
     */
    @Override
    public void doAction(RiverActionEvent value, GameConfig gameConfig, IGameCore gameCore) {
        // 坐席
        int position = value.getPosition();
        // 手牌
        ITehai tehai = gameCore.getTehai(position);
        // 对手动作的牌
        IHai rivalActionHai = value.getFromHai();
        // 将荣和的牌加入手牌
        tehai.draw(rivalActionHai);

        // 组装对手的游戏上下文
        PlayerGameContext context = PlayerGameContextFactory.buildByGameCore(position, gameConfig, gameCore);
        context.setEndByRon(true);
        context.setAgariHai(rivalActionHai);
        // 获取宝牌，若对手立直，包括里宝牌
        List<IHai> doraHais = gameCore.getDoras();
        List<IHai> uraDoras = context.getHaiRiver().isReach() ? gameCore.getUraDoras() : new ArrayList<>();
        // 计算和了信息
        AgariInfo agariInfo =
                agariCalculator.calculate(tehai, rivalActionHai, value.getFrom(), doraHais, uraDoras, context);
        // 和了动作
        gameCore.agari(position, value.getFrom(), agariInfo.getYakus(), agariInfo.getBan(), agariInfo.getFu(),
                agariInfo.getScore(), agariInfo.getIncreaseAndDecrease()
        );
        // 庄家是否荣和
        boolean isRenchan = position == gameCore.getOya();
        gameCore.setRenchan(isRenchan, !isRenchan);
    }

    @Override
    public RiverActionType getEventType() {
        return RiverActionType.RON;
    }

    @Override
    public void handle(GameEvent gameEvent, IGameCore gameCore, GameConfig gameConfig, IGameEventQueue eventQueue) {
        RiverActionEvent riverActionEvent = (RiverActionEvent) gameEvent;
        doAction(riverActionEvent, gameConfig, gameCore);
        // 产生对局结束优先事件
        eventQueue.addPriority(new KyokuEndEvent());
    }
}
