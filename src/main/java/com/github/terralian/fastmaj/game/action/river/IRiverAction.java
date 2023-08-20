package com.github.terralian.fastmaj.game.action.river;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.action.IPlayerAction;
import com.github.terralian.fastmaj.game.action.tehai.KakanAction;
import com.github.terralian.fastmaj.game.event.IDefaultGameEventHandler;
import com.github.terralian.fastmaj.game.event.river.RiverActionEvent;
import com.github.terralian.fastmaj.tehai.ITehai;

/**
 * 牌流入到手牌{@link ITehai}中的行为。这些行为包括
 * <ul>
 * <li>鸣牌动作，吃碰杠。{@link ChiiAction}，{@link PonAction}，{@link KakanAction}
 * <li>荣和动作。{@link RonAction}
 * </ul>
 * 玩家对对手处理动作在一般规则中为以上几种，这也是为何将荣和行为归纳到牌流入动作。
 * 对于特殊规则，如雀魂的部分规则中还有对对手盖牌进行开牌的动作，可继承该接口实现。
 *
 * @author terra.lian
 */
public interface IRiverAction extends IPlayerAction, IDefaultGameEventHandler {

    /**
     * 执行牌流入动作，根据动作的不同，执行不同的功能。
     *
     * @param value
     * @param gameConfig 游戏规则
     * @param gameCore 游戏核心
     */
    void doAction(RiverActionEvent value, GameConfig gameConfig, IGameCore gameCore);

    /**
     * 获取牌河动作类型
     */
    @Override
    RiverActionType getEventType();

    /**
     * 获取牌河动作编码
     */
    @Override
    default int getEventCode() {
        return getEventType().getCode();
    }
}
