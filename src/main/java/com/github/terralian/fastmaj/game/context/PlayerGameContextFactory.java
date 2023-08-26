package com.github.terralian.fastmaj.game.context;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.KazeEnum;
import com.github.terralian.fastmaj.game.action.tehai.TehaiActionType;
import com.github.terralian.fastmaj.hai.HaiPool;
import com.github.terralian.fastmaj.river.HaiRiver;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.tehai.TehaiLock;
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
        TehaiActionType lastActionType = gameCore.getLastTehaiAction() == null //
                ? null //
                : gameCore.getLastTehaiAction().getEventType();
        context.setGameConfig(config) //
                .setPlayerSize(gameCore.getPlayerSize()) //
                .setRound(gameCore.getRound()) //
                .setActionCount(gameCore.getActionCount()) //
                .setBakaze(gameCore.getBakaze()) //
                .setHonba(gameCore.getHonba()) //
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
                .setLastTehaiActionType(lastActionType) //
                .setLastRiverAction(gameCore.getLastRiverAction()) //
                .setLastDrawFrom(gameCore.getLastDrawFrom()) //
        ;
        // 和了信息没有记录到上下文

        return context;
    }

    /**
     * 为某个玩家创建一个平场的游戏上下文
     *
     * @param position 玩家
     */
    public static PlayerGameContext buildByNormal(int position) {
        PlayerGameContext context = new PlayerGameContext();
        context.setGameConfig(GameConfig.defaultRule()) //
                .setPlayerSize(4) //
                .setRound(0) //
                .setBakaze(KazeEnum.DON) //
                .setHonba(0) //
                .setKyotaku(0) //
                .setOya(0) //
                .setPlayerPoints(new int[]{25000, 25000, 25000, 25000}) //
                .setYamaCountdown(70) //
                .setDoraDisplays(CollectionUtil.newArrayList(HaiPool.m(1))) //
                .setHaiRivers(CollectionUtil.newArrayList(new HaiRiver(0), new HaiRiver(1), new HaiRiver(2), new HaiRiver(3))) //
                .setTehaiLocks(CollectionUtil.newArrayList(new TehaiLock(), new TehaiLock(), new TehaiLock(), new TehaiLock()))
                .setPosition(position) //
                .setJikaze(KazeEnum.jiKaze(position, 0)) //
                .setFuriten(false) //
                .setSyaten(13) //
                .setTehai(null) //
                .setLastTehaiActionType(null) //
        ;
        return context;
    }
}
