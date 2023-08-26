package com.github.terralian.fastmaj.player.space;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import com.github.terralian.fastmaj.player.IPlayer;

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

    /**
     * 根据玩家接口初始化构建{@link PlayerSpaceManager}
     *
     * @param players 玩家接口
     */
    public PlayerSpaceManager(List<? extends IPlayer> players) {
        this(players.size());
        for (int i = 0; i < players.size(); i++) {
            playerDefaultSpaces.get(i).setSelf(players.get(i));
        }
    }

    @Override
    public <T> void setState(List<T> data, BiConsumer<PlayerDefaultSpace, T> consumer) {
        for (int i = 0; i < data.size(); i++) {
            consumer.accept(playerDefaultSpaces.get(i), data.get(i));
        }
    }

    @Override
    public <T> void setState(T[] data, BiConsumer<PlayerDefaultSpace, T> consumer) {
        for (int i = 0; i < data.length; i++) {
            consumer.accept(playerDefaultSpaces.get(i), data[i]);
        }
    }

    @Override
    public void setState(int[] data, BiConsumer<PlayerDefaultSpace, Integer> consumer) {
        for (int i = 0; i < data.length; i++) {
            consumer.accept(playerDefaultSpaces.get(i), data[i]);
        }
    }

    @Override
    public <T> void foreach(Consumer<PlayerDefaultSpace> consumer) {
        for (PlayerDefaultSpace playerDefaultSpace : playerDefaultSpaces) {
            consumer.accept(playerDefaultSpace);
        }
    }

    @Override
    public <T> T getState(int position, Function<PlayerDefaultSpace, T> mapper) {
        return mapper.apply(playerDefaultSpaces.get(position));
    }

    @Override
    public PlayerDefaultSpace getDefaultSpace(int position) {
        return playerDefaultSpaces.get(position);
    }

    @Override
    public List<PlayerDefaultSpace> getDefaultSpaces() {
        return playerDefaultSpaces;
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
            space.resetGameState();
        }
    }

    @Override
    public void resetKyokuState() {
        for (PlayerDefaultSpace space : playerDefaultSpaces) {
            space.resetKyokuState();
        }
    }
}
