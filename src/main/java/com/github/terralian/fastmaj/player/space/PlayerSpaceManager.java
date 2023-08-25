package com.github.terralian.fastmaj.player.space;

import java.util.ArrayList;
import java.util.List;

/**
 * 玩家空间管理器的默认实现
 *
 * @author Terra.Lian
 */
public class PlayerSpaceManager implements IPlayerSpaceManager {

    private final List<PlayerDefaultSpace> playerDefaultSpaces;
    private final List<PlayerDefaultSpace> cloneDefaultSpaceContainer;
    private final List<List<PlayerPublicSpace>> clonePublicSpaceContainer;

    /**
     * 根据玩家人数初始化构建{@link PlayerSpaceManager}，预先初始化出所有容器
     *
     * @param playerSize 玩家人数
     */
    public PlayerSpaceManager(int playerSize) {
        playerDefaultSpaces = new ArrayList<>(playerSize);
        cloneDefaultSpaceContainer = new ArrayList<>(playerSize);
        clonePublicSpaceContainer = new ArrayList<>(playerSize);
        for (int i = 0; i < playerSize; i++) {
            playerDefaultSpaces.add(new PlayerDefaultSpace());
            cloneDefaultSpaceContainer.add(new PlayerDefaultSpace());

            List<PlayerPublicSpace> publicSpaces = new ArrayList<>(playerSize);
            for (int j = 0; j < playerSize; j++) {
                publicSpaces.add(new PlayerPublicSpace());
            }
            clonePublicSpaceContainer.add(publicSpaces);
        }
    }

    @Override
    public PlayerDefaultSpace getDefaultSpace(int position) {
        return playerDefaultSpaces.get(position);
    }

    @Override
    public PlayerDefaultSpace cloneDefaultSpace(int position) {
        PlayerDefaultSpace res = cloneDefaultSpaceContainer.get(position);
        playerDefaultSpaces.get(position).copyTo(res);
        return res;
    }

    @Override
    public List<PlayerPublicSpace> clonePublicSpace(int position) {
        List<PlayerPublicSpace> res = clonePublicSpaceContainer.get(position);
        for (int i = 0; i < res.size(); i++) {
            playerDefaultSpaces.get(i).copyTo(res.get(i));
        }
        return res;
    }

    @Override
    public void resetGameState() {
        for (PlayerDefaultSpace space : playerDefaultSpaces) {
            space.resetKyokuState();
        }
    }

    @Override
    public void resetKyokuState() {
        for (PlayerDefaultSpace space : playerDefaultSpaces) {
            space.resetGameState();
        }
    }
}
