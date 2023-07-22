package com.github.terralian.fastmaj.game.action.tehai;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.KyokuState;
import com.github.terralian.fastmaj.game.action.IPlayerAction;
import com.github.terralian.fastmaj.game.event.tehai.TehaiActionEvent;

/**
 * 牌流出动作，表示将手牌中某一枚打出或固定的行为。这些行为包括
 * <ul>
 * <li>弃牌动作。{@link KiriAction}， {@link ReachAction}
 * <li>固定动作。{@link AnnkanAction}，{@link KitaAction}，{@link KakanAction}
 * <li>自摸动作。{@link TsumoAction}
 * <li>流局动作。{@link Ryuukyoku99Action}
 * </ul>
 * 玩家在自身回合时在一般规则中为以上几种，这也是为何将自摸行为归纳到牌流入动作。
 *
 * @author terra.lian
 */
public interface ITehaiAction extends IPlayerAction {

    /**
     * 执行牌流入动作，根据动作的不同，执行不同的功能
     *
     * @param actionParam 参数
     * @param gameCore 游戏核心
     * @param gameOptions 游戏选项
     * @return 当前局是否结束
     */
    KyokuState doAction(TehaiActionEvent actionParam, IGameCore gameCore, GameConfig gameOptions);

    /**
     * 获取牌河动作类型
     */
    TehaiActionType getTehaiActionType();
}
