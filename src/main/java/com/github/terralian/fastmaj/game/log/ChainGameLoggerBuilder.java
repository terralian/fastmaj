package com.github.terralian.fastmaj.game.log;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link ChainGameLogger}构建器
 * 
 * @author terra.lian
 */
public class ChainGameLoggerBuilder {

    /**
     * 链式日志处理器
     */
    private List<IGameLogger> gameLoggers;

    /**
     * 初始化一个构建器
     */
    public static ChainGameLoggerBuilder newBuilder() {
        ChainGameLoggerBuilder builder = new ChainGameLoggerBuilder();
        return builder;
    }

    /**
     * 初始化构建器
     */
    public ChainGameLoggerBuilder() {
        gameLoggers = new ArrayList<>();
    }

    /**
     * 增加日志处理器
     * 
     * @param logger 日志处理器
     */
    public ChainGameLoggerBuilder addLogger(IGameLogger logger) {
        gameLoggers.add(logger);
        return this;
    }

    /**
     * 根据下标移除日志处理器
     * 
     * @param index 下标
     */
    public ChainGameLoggerBuilder removeLogger(int index) {
        gameLoggers.remove(index);
        return this;
    }

    /**
     * 完成构建工作，获得构建的链式日志处理器.当没有日志处理器时，会给与一个空的默认处理器
     */
    public IGameLogger build() {
        if (gameLoggers.isEmpty()) {
            gameLoggers.add(EmptyGameLogger.empty());
        }
        return new ChainGameLogger(gameLoggers);
    }
}
