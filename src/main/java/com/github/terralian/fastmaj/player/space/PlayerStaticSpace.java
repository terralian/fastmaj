package com.github.terralian.fastmaj.player.space;

import com.github.terralian.fastmaj.player.IPlayerPlatformInfo;
import lombok.Data;

/**
 * 玩家的静态信息空间，这些信息在游戏开始前就已固定，整个游戏生命内不会进行更改
 *
 * @author Terra.Lian
 */
@Data
public class PlayerStaticSpace implements IPlayerSpace {
    /**
     * 玩家坐席
     */
    private int position = -1;
    /**
     * 玩家名称
     */
    private String playerName;
    /**
     * 玩家的平台信息
     */
    private IPlayerPlatformInfo platformInfo;

    @Override
    public void copyTo(IPlayerSpace playerSpace) {
        if (playerSpace instanceof PlayerStaticSpace) {
            PlayerStaticSpace staticSpace = (PlayerStaticSpace) playerSpace;
            staticSpace.setPosition(position);
            staticSpace.setPlayerName(playerName);
            staticSpace.setPlatformInfo(platformInfo);
        }
    }
}
