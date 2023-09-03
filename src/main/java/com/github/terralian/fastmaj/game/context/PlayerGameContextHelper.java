package com.github.terralian.fastmaj.game.context;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.KazeEnum;
import com.github.terralian.fastmaj.hai.HaiPool;
import com.github.terralian.fastmaj.river.HaiRiver;
import com.github.terralian.fastmaj.tehai.TehaiLock;
import com.github.terralian.fastmaj.util.CollectionUtil;

/**
 * {@link IPlayerGameContext}帮助类
 *
 * @author Terra.Lian
 */
public abstract class PlayerGameContextHelper {

    /**
     * 为某个玩家创建一个平场的游戏上下文
     *
     * @param position 玩家
     */
    @Deprecated
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
        ;
        return context;
    }
}
