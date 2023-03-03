package com.github.terralian.fastmaj.paifu.tenhou;

import java.util.List;

/**
 * 天凤牌谱分析接口，进行XML的牌谱文件数据
 * 
 * @author terra.lian
 */
public interface ITenhouPaifuAnalyzer {

    /**
     * 洗牌的的头信息，在牌谱开头记录着牌山的种子，通过种子即可还原牌山
     * 
     * @param seed 种子
     */
    default void shuffleMeta(String seed) {}

    /**
     * 牌谱开始时调用，匹配节点
     * 
     * @param isSanma 是否是3麻，true为是，false为4麻
     * @param taku 天凤桌，0一般，1上级，2特上，3凤凰
     * @param isTonnan 是否东南战，true东南战，false东风战
     * @param isSoku 是否快速场
     * @param isUseAka 是否含赤规则
     * @param isAriAri 是否含食断规则
     * @param playerNames 各个玩家的天凤昵称
     * @param playerRates 各个玩家的R值
     * @param playerDans 各个玩家的段位
     */
    default void startGame(boolean isSanma, int taku, boolean isTonnan, boolean isSoku, boolean isUseAka, boolean isAriAri,
            String[] playerNames, int[] playerRates, String[] playerDans) {}

    /**
     * 牌谱结束时调用，匹配节点<code>owari</code>
     * 
     * @param playerPoints 玩家最终点数
     * @param playerScores 玩家最终分数
     */
    default void endGame(int[] playerPoints, int[] playerScores) {}

    /**
     * 一局开始时调用
     * 
     * @param playerPoints 当前玩家分数
     * @param playerHaipais 当前玩家配牌
     * @param oya 亲家[0,3]
     * @param bakaze 场风[1-4]，东1-北4
     * @param kyoku 当前局数
     * @param honba 本场
     * @param kyotaku 场供
     * @param firstDoraDisplay 最初的dora表示牌
     * @param yama 牌山
     */
    default void startKyoku(int[] playerPoints, List<List<Integer>> playerHaipais, int oya, int bakaze, int kyoku, int honba, int kyotaku,
            int firstDoraDisplay, int[] yama) {}

    /**
     * 一局结束时调用
     * 
     * @param playerPoints 玩家分数
     */
    default void endKyoku(int[] playerPoints) {}

    /**
     * 摸牌动作，从牌山将牌加入手中
     * 
     * @param position 玩家[0,3]
     * @param tsumoHai 自摸的牌
     */
    default void draw(int position, int tsumoHai) {}

    /**
     * 模切时调用
     * 
     * @param position 玩家[0,3]
     * @param kiriHai 模切的牌
     */
    default void kiri(int position, int kiriHai) {}

    /**
     * 吃的时候调用
     * 
     * @param position 鸣牌的玩家[0,3]
     * @param from 被鸣牌的玩家[0,3]
     * @param selfHai 自己的搭子
     * @param nakiHai 鸣的牌
     */
    default void chi(int position, int from, int[] selfHai, int nakiHai) {}

    /**
     * 碰的时候调用
     * 
     * @param position 鸣牌的玩家
     * @param from 被鸣牌的玩家
     * @param selfHai 自己的搭子
     * @param nakiHai 鸣的牌
     */
    default void pon(int position, int from, int[] selfHai, int nakiHai) {}

    /**
     * 暗杠的时候调用
     * 
     * @param position 玩家
     * @param selfHai 暗杠的牌
     */
    default void ankan(int position, int[] selfHai) {}

    /**
     * 明杠的时候调用
     * 
     * @param position 鸣牌的玩家
     * @param from 被鸣牌的玩家
     * @param selfHai 自己的搭子
     * @param nakiHai 鸣的牌
     */
    default void minkan(int position, int from, int[] selfHai, int nakiHai) {}

    /**
     * 加杠的时候调用
     * 
     * @param position 鸣牌的玩家
     * @param from 被鸣牌的玩家
     * @param selfHai 自己的搭子
     * @param nakiHai 鸣的牌
     * @param addHai 加杠的牌
     */
    default void kakan(int position, int from, int[] selfHai, int nakiHai, int addHai) {}

    /**
     * 拔北的时候调用
     * 
     * @param position 拔北的玩家
     */
    default void kita(int position) {}

    /**
     * 立直，步骤1，立直宣言
     * 
     * @param position 立直的玩家
     */
    default void reach1(int position) {}

    /**
     * 立直，步骤2，步骤1到2间，先打出一枚牌，再扣除点数，打的牌调用模切方法{@link #kiri(int, int)}
     * <p/>
     * 部分游戏如雀魂实际操作是一次操作，这种情况下也作为两次步骤进行。
     * <p/>
     * 立直打出一枚指示牌到还未放置立直棒的一刻可以被荣和，此时分数计算不会将立直棒的点数收益也算计在内。
     * 
     * @param position 立直的玩家
     * @param playerPoints 当前玩家的分数
     */
    default void reach2(int position, int[] playerPoints) {}

    /**
     * 宝牌增加时调用
     * 
     * @param newDoraDisplay 新的宝牌指示牌
     */
    default void addDora(int newDoraDisplay) {}

    /**
     * 和牌的时候调用（包含自摸和荣和）
     * 
     * @param position 和牌的玩家
     * @param from 荣和时为点铳的玩家，自摸时为自摸的玩家
     * @param yaku 役种
     * @param han 番数
     * @param hu 符
     * @param score 点数
     * @param increaseAndDecrease 各个玩家增减后的当前点数
     */
    default void agari(int position, int from, List<String> yaku, int han, int hu, int score, int[] increaseAndDecrease) {}

    /**
     * 流局时调用
     */
    default void ryuukyoku(int[] increaseAndDecrease, String type) {}

    /**
     * 玩家断线时调用
     * 
     * @param position 断线的玩家
     */
    default void disconnect(int position) {}

    /**
     * 玩家重新连接时调用
     * 
     * @param position 重连的玩家
     */
    default void reconnect(int position) {}
}
