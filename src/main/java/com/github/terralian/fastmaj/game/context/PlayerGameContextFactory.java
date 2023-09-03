package com.github.terralian.fastmaj.game.context;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.KazeEnum;
import com.github.terralian.fastmaj.player.space.IPlayerSpaceManager;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.util.CollectionUtil;

/**
 * {@link PlayerGameContext} 工厂类
 *
 * @author terra.lian
 */
public class PlayerGameContextFactory {

    /**
     * 根据游戏核心构建玩家上下文
     *
     * @param position 玩家的坐席
     * @param config 游戏配置
     * @param gameCore 游戏核心
     */
    public static PlayerGameContext buildByGameCore(int position, GameConfig config, IGameCore gameCore) {
        PlayerGameContext context = new PlayerGameContext();
        IPlayerSpaceManager playerSpaceManager = gameCore.getPlayerSpaceManager();
        context.setGameConfig(config) //
                .setPlayerSize(gameCore.getPlayerSize()) //
                .setRound(gameCore.getRound()) //
                .setActionCount(gameCore.getActionCount()) //
                .setBakaze(gameCore.getBakaze()) //
                .setHonba(gameCore.getDisplayHonba()) //
                .setRealHonba(gameCore.getRealHonba()) //
                .setKyotaku(gameCore.getKyotaku()) //
                .setOya(gameCore.getOya()) //
                .setPlayerPoints(gameCore.getPlayerPoints()) //
                .setYamaCountdown(gameCore.getYamaCountdown()) //
                .setDoraDisplays(gameCore.getDoraDisplays()) //
                .setHaiRivers(gameCore.getHaiRivers()) //
                .setTehaiLocks(CollectionUtil.mapToList(gameCore.getTehais(), ITehai::getLock))
                .setPosition(position) //
                .setJikaze(KazeEnum.jiKaze(position, gameCore.getOya())) //
                .setFuriten(gameCore.isFuriten(position)) //
                .setSyaten(gameCore.getSyaten(position)) //
                .setTehai(gameCore.getTehai(position)) //
                .setSpace(playerSpaceManager.cloneDefaultSpace(position)) //
                .setPublicSpaces(playerSpaceManager.clonePublicSpace(position)) //
        ;
        // 和了信息没有记录到上下文

        return context;
    }
}
