package com.github.terralian.fastmaj.game.ryuuky;


import java.util.ArrayList;
import java.util.List;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.util.EmptyUtil;

/**
 * 默认流局管理器实现
 * 
 * @author terra.lian
 */
public class RyuukyokuResolverManager implements IRyuukyokuResolverManager {

    private List<IRyuukyokuResolver> ryuukyokuResolvers;
    
    /**
     * 根据流局集合初始化流局管理器
     * 
     * @param ryuukyokuResolvers 流局判定集合
     */
    public RyuukyokuResolverManager(List<IRyuukyokuResolver> ryuukyokuResolvers) {
        if (EmptyUtil.isEmpty(ryuukyokuResolvers)) {
            throw new IllegalArgumentException("至少需要一个正常流局类用于结束当前对局");
        }
        this.ryuukyokuResolvers = new ArrayList<>(ryuukyokuResolvers);
    }

    /**
     * 初始化构建一个默认的流局管理器
     */
    public static RyuukyokuResolverManager defaultManager() {
        List<IRyuukyokuResolver> resolvers = new ArrayList<>();
        resolvers.add(new FourKanRyuukyokuResolver());
        resolvers.add(new FourKazeRyuukyokuResolver());
        resolvers.add(new FourReachRyuukyokuResolver());
        resolvers.add(new ManganRyuukyokuResolver());
        resolvers.add(new NormalRyuukyokuResolver());

        return new RyuukyokuResolverManager(resolvers);
    }

    @Override
    public IRyuukyokuResolver resolve(GameConfig gameConfig, IGameCore gameCore) {
        for (IRyuukyokuResolver ryuukyokuResolver : ryuukyokuResolvers) {
            if (ryuukyokuResolver.validate(gameConfig, gameCore)) {
                return ryuukyokuResolver;
            }
        }
        return null;
    }
}
