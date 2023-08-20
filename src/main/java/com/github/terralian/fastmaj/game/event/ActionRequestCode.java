package com.github.terralian.fastmaj.game.event;

/**
 * 动作请求码
 *
 * @author Terra.Lian
 */
public interface ActionRequestCode {

    /**
     * 请求玩家手牌动作
     */
    int REQUEST_TEHAI_ACTION = 400;

    /**
     * 请求玩家牌河动作
     */
    int REQUEST_RIVER_ACTION = 401;
}
