package com.github.terralian.fastmaj.game.action.tehai;

import com.github.terralian.fastmaj.FastMajong;
import com.github.terralian.fastmaj.agari.IAgariCalculator;
import com.github.terralian.fastmaj.game.action.IPlayerAction;

/**
 * 表示一个和了动作，包含荣和及自摸。该类可用于判定一个动作是否为和了动作
 * 
 * @author terra.lian
 */
public abstract class AgariAction implements IPlayerAction {

    /**
     * 役匹配器，有值时使用指定的役匹配器。若为指定，则使用默认的
     * <p/>
     * 通过更改役匹配器，可以决定规则使用什么役种，比如是否启用人和，古役。
     */
    protected IAgariCalculator agariCalculator;

    public AgariAction() {
        agariCalculator = FastMajong.doGetAgariCalculator();
    }
}
