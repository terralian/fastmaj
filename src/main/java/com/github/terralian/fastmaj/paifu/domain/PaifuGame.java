package com.github.terralian.fastmaj.paifu.domain;

import java.util.Date;
import java.util.List;

import com.github.terralian.fastmaj.game.KazeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 一个牌谱解析的结果，对人友好易懂易用。
 * <p/>
 * 通过该实体，可以获取到一个牌谱中每一个时刻的信息，这些详细的信息可以用于支撑后续的牌谱分析
 * <p/>
 * 比如南四局，目标玩家在分数相近时，在没有人立直时，该玩家选择立直的牌谱。那么，遍历解析该玩家的所有解析牌谱后，
 * 获取到{@link PaifuGame}的集合，再通过内部信息进行过滤。一个示例代码：
 *
 * @author terra.lian
 * @since 2023-03-21
 */
@Data
@Accessors(chain = true)
public class PaifuGame {

    // --------------------------------------------------
    // 牌谱编码部分
    // --------------------------------------------------

    /**
     * 原始的牌谱信息
     */
    private String origin;

    /**
     * 牌谱的种子，一般平台的麻将游戏都会使用种子技术生成牌山
     */
    private String seed;

    /**
     * 牌谱的文件名（包含后缀）
     */
    private String fileName;

    /**
     * 标识唯一牌谱的编码
     */
    private String uniqueNo;

    /**
     * 对局发生的时间
     */
    private Date happenTime;

    // --------------------------------------------------
    // 牌谱平台部分
    // --------------------------------------------------

    /**
     * 对局的房间（段位场/个人间等）
     */
    private String room;

    /**
     * 平台名称（天凤/雀魂/自定义）
     */
    private String platform;

    /**
     * 规则版本，平台在历史上可能调整过规则，用该字段进行区分
     */
    private String ruleVersion;

    // --------------------------------------------------
    // 牌谱规则部分，目前仅支持天凤/雀魂的段位场规则
    // --------------------------------------------------

    /**
     * 玩家数（4麻/3麻）
     */
    private int playerSize;

    /**
     * 结束场风，东风场=东风/东南场=南风
     */
    private KazeEnum endBakaze;

    /**
     * 是否使用红宝牌
     */
    private boolean useRed;

    // --------------------------------------------------
    // 对局部分
    // --------------------------------------------------

    /**
     * 玩家昵称，通过该字段也可获取玩家的坐席
     */
    private String[] playerNames;

    /**
     * 该麻将游戏中的所有对局
     */
    private List<PaifuKyoku> kyokus;

    /**
     * 玩家的最终分数
     */
    private int[] endPoints;

    // --------------------------------------------------
    // 常用方法部分
    // --------------------------------------------------

    /**
     * 获取末局
     */
    public PaifuKyoku getEndKyoku() {
        return kyokus.get(kyokus.size() - 1);
    }

    /**
     * 是否提前结束比赛
     * <p/>
     * 东风场，东四局前结束; 东南场，南四局前结束
     */
    public boolean isSuspend() {
        PaifuKyoku endKyoku = getEndKyoku();
        return endKyoku.getBakaze().getOrder() <= endBakaze.getOrder()
                && KazeEnum.getByOrder(endKyoku.getOya()).getOrder() < endBakaze.getOrder();
    }
}
