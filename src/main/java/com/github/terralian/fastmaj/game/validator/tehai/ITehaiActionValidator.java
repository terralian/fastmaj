package com.github.terralian.fastmaj.game.validator.tehai;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.action.tehai.ITehaiAction;
import com.github.terralian.fastmaj.game.action.tehai.TehaiActionType;
import com.github.terralian.fastmaj.game.validator.IPlayerActionValidator;

/**
 * 手牌处理动作可执行判断器
 * 
 * @author terra.lian
 * @see ITehaiAction
 */
public interface ITehaiActionValidator extends IPlayerActionValidator {

    /**
     * 校验该玩家哪些手牌处理动作可执行
     * 
     * @param position 玩家坐席
     * @param gameConfig 游戏规则
     * @param gameCore 游戏核心
     */
    boolean resolveAction(int position, GameConfig gameConfig, IGameCore gameCore);

    /**
     * 获取牌河动作类型
     */
    TehaiActionType getTehaiActionType();
}
