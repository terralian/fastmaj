package com.github.terralian.fastmaj.game.log;

import java.text.MessageFormat;
import java.util.List;

import com.github.terralian.fastmaj.encode.EncodeMark;
import com.github.terralian.fastmaj.game.IGameCore;
import com.github.terralian.fastmaj.game.KazeEnum;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.player.IPlayer;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.util.CollectionUtil;
import com.github.terralian.fastmaj.util.RankingUtil;
import com.github.terralian.fastmaj.util.StringUtil;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yama.DrawFrom;

/**
 * 使用{@link System#out}进行日志打印，在控制台输出日志信息
 * 
 * @author terra.lian
 * @since 2022-10-28
 */
public class PrintGameLogger implements IGameLogger {

    /**
     * 游戏核心
     */
    private IGameCore gameCore;

    /**
     * 打印简要的统计信息
     */
    private boolean shortKyokuSummary = false;

    /**
     * 构建一个空的日志打印器
     */
    public PrintGameLogger() {
    }

    @Override
    public void gameStart(List<IPlayer> players, IGameCore gameCore) {
        this.gameCore = gameCore;
        System.out.println("游戏开始，种子：" + gameCore.getSeed());
        System.out.println("--- --- ---");
    }

    @Override
    public void kyokuStart(int round, KazeEnum bakaze, int oya, int honba, List<ITehai> haipais) {
        if (shortKyokuSummary) {
            return;
        }
        System.out.println("==============================================");
        System.out.println(MessageFormat.format("对局开始：{0}{1}局，庄家：玩家{1}", bakaze, oya + 1));
        System.out.println("配牌:");
        System.out.println("玩家1:" + EncodeMark.encode(haipais.get(0)) + " 向听数：" + gameCore.getPlayerHide(0).getSyanten());
        System.out.println("玩家2:" + EncodeMark.encode(haipais.get(1)) + " 向听数：" + gameCore.getPlayerHide(1).getSyanten());
        System.out.println("玩家3:" + EncodeMark.encode(haipais.get(2)) + " 向听数：" + gameCore.getPlayerHide(2).getSyanten());
        System.out.println("玩家4:" + EncodeMark.encode(haipais.get(3)) + " 向听数：" + gameCore.getPlayerHide(3).getSyanten());
        System.out.println("---");
    }

    @Override
    public void kyokuEnd(int round, int[] playerPoints) {
        if (shortKyokuSummary) {
            return;
        }
        System.out.println("   对局结束   ");
        System.out.println("");
    }

    @Override
    public void gameEnd() {
        int[] ranking = RankingUtil.calcRanking(gameCore.getPlayerPoints());
        // 统计最终排名信息
        System.out.println("*** 游戏结束  ***");
        System.out.println("玩家1：第" + (ranking[0]) + "名 " + gameCore.getPlayerPoints()[0]);
        System.out.println("玩家2：第" + (ranking[1]) + "名 " + gameCore.getPlayerPoints()[1]);
        System.out.println("玩家3：第" + (ranking[2]) + "名 " + gameCore.getPlayerPoints()[2]);
        System.out.println("玩家4：第" + (ranking[3]) + "名 " + gameCore.getPlayerPoints()[3]);

        System.out.println("*** *** ***");
        System.out.println("");
        System.out.println("");
    }

    @Override
    public void draw(int position, IHai hai, DrawFrom drawFrom) {
        if (shortKyokuSummary) {
            return;
        }
        String message = MessageFormat.format("[{7}] {0}{1}局({2}本场) [{3}] 玩家{4} 摸牌 {5} 向听数:{6}", //
                gameCore.getBakaze(), //
                gameCore.getOya() + 1, //
                gameCore.getHonba(), //
                gameCore.getYamaCountdown(), //
                position + 1, //
                hai, //
                gameCore.getPlayerHide(position).getSyanten(), //
                gameCore.getActionCount()
        );
        System.out.println(message);
    }

    @Override
    public void kiri(int position, IHai hai, boolean reach, boolean handKiri) {
        if (shortKyokuSummary) {
            return;
        }
        String message = MessageFormat.format("[{9}] {0}{1}局({2}本场) [{3}] 玩家{4} {5} {6}{7} 向听数:{8}", //
                gameCore.getBakaze(), //
                gameCore.getOya() + 1, //
                gameCore.getHonba(), //
                gameCore.getYamaCountdown(), //
                position + 1, //
                handKiri ? "手切" : "模切",
                hai, //
                reach ? "(立直)" : "", //
                gameCore.getPlayerHide(position).getSyanten(), //
                gameCore.getActionCount()
        );
        System.out.println(message);
    }

    @Override
    public void reach2(int[] increaseAndDecrease, int kyotaku) {
        if (shortKyokuSummary) {
            return;
        }
        System.out.println("当前分数:" + StringUtil.join(",", increaseAndDecrease));
    }

    @Override
    public void chii(int position, int fromPosition, IHai chiiHai, IHai selfHai1, IHai selfHai2) {
        if (shortKyokuSummary) {
            return;
        }
        String message = MessageFormat.format("[{9}] {0}{1}局({2}本场) [{3}] 玩家{4}从玩家{5}吃{6}(搭子: {7}{8})", //
                gameCore.getBakaze(), //
                gameCore.getOya() + 1, //
                gameCore.getHonba(), //
                gameCore.getYamaCountdown(), //
                position + 1, //
                fromPosition + 1, //
                chiiHai, //
                selfHai1, //
                selfHai2, //
                gameCore.getActionCount()
        );
        System.out.println(message);
    }

    @Override
    public void pon(int position, int fromPosition, IHai ponHai) {
        if (shortKyokuSummary) {
            return;
        }
        String message = MessageFormat.format("[{7}] {0}{1}局({2}本场) [{3}] 玩家{4}从玩家{5}碰{6}", //
                gameCore.getBakaze(), //
                gameCore.getOya() + 1, //
                gameCore.getHonba(), //
                gameCore.getYamaCountdown(), //
                position + 1, //
                fromPosition + 1, //
                ponHai, //
                gameCore.getActionCount());
        System.out.println(message);
    }

    @Override
    public void minkan(int position, int fromPosition, IHai minkan) {
        if (shortKyokuSummary) {
            return;
        }
        String message = MessageFormat.format("[{7}] {0}{1}局({2}本场) [{3}] 玩家{4}从玩家{5}杠{6}", //
                gameCore.getBakaze(), //
                gameCore.getOya() + 1, //
                gameCore.getHonba(), //
                gameCore.getYamaCountdown(), //
                position + 1, //
                fromPosition + 1, //
                minkan, //
                gameCore.getActionCount());
        System.out.println(message);
    }

    @Override
    public void kakan(int position, IHai kanHai) {
        if (shortKyokuSummary) {
            return;
        }
        String message = MessageFormat.format("[{6}] {0}{1}局({2}本场) [{3}] 玩家{4}加杠{5}", //
                gameCore.getBakaze(), //
                gameCore.getOya() + 1, //
                gameCore.getHonba(), //
                gameCore.getYamaCountdown(), //
                position + 1, //
                kanHai, //
                gameCore.getActionCount());
        System.out.println(message);
    }

    @Override
    public void annkan(int position, IHai annkanHai) {
        if (shortKyokuSummary) {
            return;
        }
        String message = MessageFormat.format("[{6}] {0}{1}局({2}本场) [{3}] 玩家{4}暗杠{5}", //
                gameCore.getBakaze(), //
                gameCore.getOya() + 1, //
                gameCore.getHonba(), //
                gameCore.getYamaCountdown(), //
                position + 1, //
                annkanHai, //
                gameCore.getActionCount());
        System.out.println(message);
    }

    @Override
    public void kita(int position, IHai hai) {
        if (shortKyokuSummary) {
            return;
        }
        String message = MessageFormat.format("[{5}] {0}{1}局({2}本场) [{3}] 玩家{4}拔北", //
                gameCore.getBakaze(), //
                gameCore.getOya() + 1, //
                gameCore.getHonba(), //
                gameCore.getYamaCountdown(), //
                position + 1, //
                hai, //
                gameCore.getActionCount());
        System.out.println(message);
    }

    @Override
    public void ron(int position, int fromPosition, List<IYaku> yakus, int ban, int fu, int score, int[] increaseAndDecrease) {
        if (!shortKyokuSummary) {
            System.out.println("--------------------------------------------");
        }
        String message = MessageFormat.format("[{9}] {0}{1}局({2}本场) 玩家{3}从玩家{4}荣和点{5}({6}番 {7}符) 分数：{8}", //
                gameCore.getBakaze(), //
                gameCore.getOya() + 1, //
                gameCore.getHonba(), //
                position + 1, //
                fromPosition + 1, //
                score,
                ban, //
                fu, //
                StringUtil.join(",", increaseAndDecrease), //
                gameCore.getActionCount());
        System.out.println(message);
        List<String> yakuNames = CollectionUtil.mapToList(yakus, k -> k.getName());
        System.out.println(StringUtil.join(" ", yakuNames));
        System.out.println("--------------------------------------------");
    }

    @Override
    public void tsumo(int position, List<IYaku> yakus, int ban, int fu, int score, int[] increaseAndDecrease) {
        if (!shortKyokuSummary) {
            System.out.println("--------------------------------------------");
        }
        System.out.println(MessageFormat.format("[{8}] {0}{1}局({2}本场) 玩家{3}自摸{4}点({5}番 {6}符) 分数：{7}", //
                gameCore.getBakaze(), //
                gameCore.getOya() + 1, //
                gameCore.getHonba(), //
                position + 1, //
                String.valueOf(score), //
                ban,  //
                fu, //
                StringUtil.join(",", increaseAndDecrease), //
                gameCore.getActionCount()
        ));
        System.out.println("手牌：" + EncodeMark.encode(gameCore.getTehai(position)));
        List<String> yakuNames = CollectionUtil.mapToList(yakus, k -> k.getName());
        System.out.println(StringUtil.join(" ", yakuNames));
        System.out.println("--------------------------------------------");
    }

    @Override
    public void ryuukyoku(String ryuukyokuName, int[] increaseAndDecrease) {
        System.out.println(MessageFormat.format("[{5}] {0}{1}局({2}本场) 流局：{3} 分数：{4}", //
                gameCore.getBakaze(), //
                gameCore.getOya() + 1, //
                gameCore.getHonba(), //
                ryuukyokuName, //
                StringUtil.join(",", increaseAndDecrease), //
                gameCore.getActionCount()
        ));
    }

    @Override
    public void switchPlayer(int prevPlayer, int nextPlayer) {
    }

    @Override
    public void nextDoraDisplay(IHai doraDisplay) {
        if (shortKyokuSummary) {
            return;
        }
        System.out.println("新宝牌：" + doraDisplay.toString());
    }

    /**
     * 设置简短的打印对局信息
     * 
     * @param shortKyokuSummary 是否简要对局信息
     */
    public PrintGameLogger setShortKyokuSummary(boolean shortKyokuSummary) {
        this.shortKyokuSummary = shortKyokuSummary;
        return this;
    }
}
