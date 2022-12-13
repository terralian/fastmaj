package com.github.terralian.fastmaj.paifu.tenhou;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.github.terralian.fastmaj.yama.TenhouYamaWorker;

/**
 * 天凤牌谱XML解析器，该类用于将天凤牌谱文件中的编码信息进行解码，通过{@link ITenhouPaifuAnalyzer}返回可以看懂的解读信息。
 * 一个牌谱XML文件的解析流程为：
 * <ul>
 * <li>读取牌谱文件获取XML
 * <li>使用{@link TenhouPaifuDecodeHandler}将XML进行解密（实质是把压缩的信息提取出来）
 * <li>在执行到对应的步骤时，调用{@link ITenhouPaifuAnalyzer}的实例类处理可处理的解密信息
 * <li>处理完成后，由{@link ITenhouPaifuAnalyzer}返回所需的实体
 * </ul>
 * 由于使用SAX2进行XML解析，一次调用就是遍历整个XML。
 * <p>
 * 该方法代码来源为<code>tenhouvisualizer</code>，代码进行了一部分改造和补全
 * 
 * @author terra.lian
 * @see <a href=
 *      "https://github.com/CrazyBBB/tenhou-visualizer/blob/master/src/main/java/tenhouvisualizer/domain/analyzer/ParseHandler.java">类原始来源</a>
 * @see <a href=
 *      "https://m77.hatenablog.com/entry/2017/05/21/214529">天凤牌谱解析教程</a>
 * @see ITenhouPaifuAnalyzer
 */
public class TenhouPaifuDecodeHandler extends DefaultHandler {

    /** 是否三麻 */
    private boolean isSanma;
    /** 天凤桌 */
    private int taku;
    /** 是否东南 */
    private boolean isTonnan;
    /** 是否快速场 */
    private boolean isSoku;
    /** 是否含宝牌规则 */
    private boolean isUseAka;
    /** 是否食断规则 */
    private boolean isAriAri;
    /** 天凤玩家昵称 */
    private String[] playerNames = new String[4];
    /** 玩家分数 */
    private int[] playerRates = new int[4];
    /** 玩家段位 */
    private String[] playerDans = new String[4];

    /** 牌谱实际分析的接口 */
    private ITenhouPaifuAnalyzer analyzer;
    /** 天凤牌山生成器 */
    private TenhouYamaWorker tenhouLogYamaWorker;
    
    /**
     * 是否和了
     */
    private boolean isAgari;
    /**
     * 和了分数
     */
    private int[] increaseAndDecrease;

    /**
     * 实例化{@link TenhouPaifuDecodeHandler}
     * 
     * @param analyzer 天凤牌谱监听分析器
     */
    public TenhouPaifuDecodeHandler(ITenhouPaifuAnalyzer analyzer) {
        this.analyzer = analyzer;
        this.tenhouLogYamaWorker = new TenhouYamaWorker();
        isAgari = false;
    }

    @Override
    public void startElement(String uri, String localName, String tagName, Attributes attributes) throws SAXException {
        if ("SHUFFLE".equals(tagName)) {
            visitSHUFFLE(attributes);
        } else if ("GO".equals(tagName)) {
            visitGO(attributes);
        } else if ("UN".equals(tagName)) {
            visitUN(attributes);
        } else if ("TAIKYOKU".equals(tagName)) {
            visitTAIKYOKU();
        } else if ("INIT".equals(tagName)) {
            handleEndKyoku();
            visitINIT(attributes);
        } else if ("AGARI".equals(tagName)) {
            visitAGARI(attributes);
        } else if ("RYUUKYOKU".equals(tagName)) {
            visitRYUUKYOKU(attributes);
        } else if ("N".equals(tagName)) {
            visitN(attributes);
        } else if (tagName.matches("[T-W]\\d+")) {
            visitTUVW(tagName);
        } else if (tagName.matches("[D-G]\\d+")) {
            visitDEFG(tagName);
        } else if ("REACH".equals(tagName)) {
            visitREACH(attributes);
        } else if ("DORA".equals(tagName)) {
            visitDORA(attributes);
        } else if ("BYE".equals(tagName)) {
            visitBYE(attributes);
        }
    }

    /**
     * 准备牌山，包含seed和ref两个标签，这里用不上
     */
    private void visitSHUFFLE(Attributes attributes) {
        String seed = attributes.getValue("seed");
        tenhouLogYamaWorker.setSeed(seed);
        tenhouLogYamaWorker.initialize();
        analyzer.shuffleMeta(seed);
    }

    /**
     * 对局开始，包含type和lobby两个字段，可通过type识别桌和规则
     */
    private void visitGO(Attributes attributes) {
        int type = Integer.parseInt(attributes.getValue("type"));
        isSanma = (type & 0b00010000) != 0;
        boolean takuBit1 = (type & 0b00100000) != 0;
        boolean takuBit2 = (type & 0b10000000) != 0;
        if (takuBit1) {
            if (takuBit2) {
                taku = 3;
            } else {
                taku = 2;
            }
        } else {
            if (takuBit2) {
                taku = 1;
            } else {
                taku = 0;
            }
        }
        // 东风战/东南战
        isTonnan = (type & 0b00001000) != 0;
        // 快速规则
        isSoku = (type & 0b01000000) != 0;
        // 有红宝牌规则
        isUseAka = (type & 0b00000010) == 0;
        // 食断规则
        isAriAri = (type & 0b00000100) == 0;
    }

    /**
     * 对局者信息，在重新连接时也会有一个记录，重连时只有当前玩家的名称，非重连时包含4人名称，段位，R值，性别信息
     * <p>
     * 玩家： n0,n1,n2,n3. n0="%2D%72%6F%6E%2D"，3麻时n3=""
     * <p>
     * 段位：dan="16,19,16,18"
     * <p>
     * R值：rate="2135.55,2260.11,2018.07,2198.52"
     * <p>
     * 性别：sx="M,M,M,M"
     * 
     * @param attributes
     */
    private void visitUN(Attributes attributes) {
        String danCsv = attributes.getValue("dan");
        // 正常时
        if (danCsv != null) {
            playerNames = new String[4];
            playerRates = new int[4];
            playerDans = new String[4];
            for (int i = 0; i < 4; i++) {
                try {
                    String name = URLDecoder.decode(attributes.getValue("n" + i), "UTF-8");
                    playerNames[i] = name;
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
            String rateCsv = attributes.getValue("rate");
            String[] splitedRateCsv = rateCsv.split(",");
            for (int j = 0; j < 4; j++) {
                playerRates[j] = Float.valueOf(splitedRateCsv[j]).intValue();
            }

            String[] splitedDanCsv = danCsv.split(",");
            for (int j = 0; j < 4; j++) {
                playerDans[j] = TenhouPaifuStringPool.DANS[Integer.valueOf(splitedDanCsv[j])];
            }
        } else {
            // 重连时
            for (int i = 0; i < 4; i++) {
                if (attributes.getValue("n" + i) != null) {
                    analyzer.reconnect(i);
                }
            }
        }
    }

    /**
     * 对局开始，含oya标签，但是oya固定为0
     */
    private void visitTAIKYOKU() {
        analyzer.startGame(isSanma, taku, isTonnan, isSoku, isUseAka, isAriAri, playerNames, playerRates, playerDans);
    }

    /**
     * 局开始时，包含配牌信息和本场点棒信息等
     */
    private void visitINIT(Attributes attributes) {
        int[] playerPoints = new int[4];
        String pointCsv = attributes.getValue("ten");
        String[] splitedPointCsv = pointCsv.split(",");
        for (int j = 0; j < 4; j++) {
            playerPoints[j] = Integer.valueOf(splitedPointCsv[j]) * 100;
        }
        int oya = Integer.parseInt(attributes.getValue("oya"));
        String seedCsv = attributes.getValue("seed");
        String[] splitedSeedCsv = seedCsv.split(",");
        int seedElementFirst = Integer.valueOf(splitedSeedCsv[0]);
        int bakaze = seedElementFirst / 4;
        int kyoku = seedElementFirst % 4 + 1;
        int honba = Integer.valueOf(splitedSeedCsv[1]);
        int kyotaku = Integer.valueOf(splitedSeedCsv[2]);
        int firstDoraDisplay = Integer.valueOf(splitedSeedCsv[5]);

        List<List<Integer>> playerHaipais = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            playerHaipais.add(new ArrayList<>());

            String haiCsv = attributes.getValue("hai" + i);
            if ("".equals(haiCsv))
                continue;

            String[] splitedHaiCsv = haiCsv.split(",");
            for (int j = 0; j < 13; j++) {
                int hai = Integer.parseInt(splitedHaiCsv[j]);
                playerHaipais.get(i).add(hai);
            }
        }

        int[] yamas = tenhouLogYamaWorker.getNextYama(1);
        analyzer.startKyoku(playerPoints, playerHaipais, oya, bakaze, kyoku, honba, kyotaku, firstDoraDisplay, yamas);
    }

    /**
     * 摸牌时调用
     */
    private void visitTUVW(String tagName) {
        int position = tagName.charAt(0) - 'T';
        int tsumoHai = Integer.parseInt(tagName.substring(1));
        analyzer.draw(position, tsumoHai);
    }

    /**
     * 切牌，手切和模切需要根据手牌才能匹配
     */
    private void visitDEFG(String tagName) {
        int position = tagName.charAt(0) - 'D';
        int kiriHai = Integer.parseInt(tagName.substring(1));
        analyzer.kiri(position, kiriHai);
    }

    /**
     * 鸣牌标签，吃碰杠暗杠加杠拔北都是该方法
     */
    private void visitN(Attributes attributes) {
        int m = Integer.parseInt(attributes.getValue("m"));
        int position = Integer.parseInt(attributes.getValue("who"));
        if ((m >> 2 & 1) == 1) {
            // 吃
            parseChow(position, m);
        } else if ((m >> 3 & 1) == 1) {
            // 碰
            parsePong(position, m);
        } else if ((m >> 4 & 1) == 1) {
            // 加杠
            parseKakan(position, m);
        } else if ((m >> 5 & 1) == 1) {
            // 拔北
            parseKita(position);
        } else if ((m & 3) == 0) {
            // 暗杠
            parseAnkan(position, m);
        } else {
            // 明杠
            parseMinkan(position, m);
        }
    }

    /**
     * 吃
     */
    private void parseChow(int position, int m) {
        int from = 3 - (m & 3); // 0: 上家, 1: 対面, 2: 下家, (3: 暗カンを表す)
        int tmp = (m >> 10) & 63;
        int r = tmp % 3; // 下から何番目の牌を鳴いたか

        tmp /= 3;
        tmp = tmp / 7 * 9 + tmp % 7;
        tmp *= 4; // 一番下の牌

        int[] h = new int[3];
        h[0] = tmp + ((m >> 3) & 3);
        h[1] = tmp + 4 + ((m >> 5) & 3);
        h[2] = tmp + 8 + ((m >> 7) & 3);
        int[] selfHai;
        int nakiHai;
        if (r == 0) {
            selfHai = new int[] {h[1], h[2]};
            nakiHai = h[0];
        } else if (r == 1) {
            selfHai = new int[] {h[0], h[2]};
            nakiHai = h[1];
        } else if (r == 2) {
            selfHai = new int[] {h[0], h[1]};
            nakiHai = h[2];
        } else {
            throw new RuntimeException();
        }
        analyzer.chi(position, from, selfHai, nakiHai);
    }

    /**
     * 碰
     */
    private void parsePong(int position, int m) {
        int from = 3 - (m & 3); // 0: 上家, 1: 対面, 2: 下家, (3: 暗カンを表す)

        int unused = (m >> 5) & 3;
        int tmp = (m >> 9) & 127;
        int r = tmp % 3;

        tmp /= 3;
        tmp *= 4;

        int[] selfHai = new int[2];
        int count = 0;
        int idx = 0;
        for (int i = 0; i < 4; i++) {
            if (i == unused)
                continue;
            if (count != r) {
                selfHai[idx++] = tmp + i;
            }
            count++;
        }
        int nakiHai = tmp + r;

        analyzer.pon(position, from, selfHai, nakiHai);
    }

    /**
     * 加杠
     */
    private void parseKakan(int position, int m) {
        int from = 3 - (m & 3); // 0: 上家, 1: 対面, 2: 下家, (3: 暗カンを表す)
        int unused = (m >> 5) & 3;
        int tmp = (m >> 9) & 127;
        int r = tmp % 3;

        tmp /= 3;
        tmp *= 4;

        int[] selfHai = new int[2];
        int count = 0;
        int idx = 0;
        for (int i = 0; i < 4; i++) {
            if (i == unused)
                continue;
            if (count != r) {
                selfHai[idx++] = tmp + i;
            }
            count++;
        }
        int nakiHai = tmp + r;
        int addHai = tmp + unused;
        analyzer.kakan(position, from, selfHai, nakiHai, addHai);
    }

    /**
     * 拔北
     */
    private void parseKita(int position) {
        analyzer.kita(position);
    }

    /**
     * 暗杠
     */
    private void parseAnkan(int position, int m) {
        int tmp = (m >> 8) & 255;

        tmp = tmp / 4 * 4;

        int[] selfHai = {tmp + 1, tmp, tmp + 2, tmp + 3};

        analyzer.ankan(position, selfHai);
    }

    /**
     * 明杠
     */
    private void parseMinkan(int position, int m) {
        int from = 3 - (m & 3); // 0: 上家, 1: 対面, 2: 下家, (3: 暗カンを表す)
        int nakiHai = (m >> 8) & 255; // 鳴いた牌

        int haiFirst = nakiHai / 4 * 4;

        int[] selfHai = new int[3];
        int idx = 0;
        for (int i = 0; i < 3; i++) {
            if (haiFirst + idx == nakiHai)
                idx++;
            selfHai[i] = haiFirst + idx;
            idx++;
        }

        analyzer.minkan(position, from, selfHai, nakiHai);
    }

    /**
     * 立直时调用，立直步骤分3步，这里处理13
     * <p>
     * 步骤为: 1. 立直宣言， 2. 打出一张牌， 3.放置立直棒
     * <p>
     * 按天凤规则，在立直打出一枚牌时才会翻新宝牌
     */
    private void visitREACH(Attributes attributes) {
        int position = Integer.parseInt(attributes.getValue("who"));
        int step = Integer.parseInt(attributes.getValue("step"));
        if (step == 1) {
            analyzer.reach1(position);
        } else {
            String tenCsv = attributes.getValue("ten");
            int[] playerPoints = new int[4];
            String[] splitedPointCsv = tenCsv.split(",");
            for (int i = 0; i < 4; i++) {
                playerPoints[i] = Integer.parseInt(splitedPointCsv[i]) * 100;
            }
            analyzer.reach2(position, playerPoints);
        }
    }

    /**
     * 新宝牌时调用
     */
    private void visitDORA(Attributes attributes) {
        int newDoraDisplay = Integer.parseInt(attributes.getValue("hai"));
        analyzer.addDora(newDoraDisplay);
    }

    /**
     * 玩家断线时调用
     */
    private void visitBYE(Attributes attributes) {
        int position = Integer.parseInt(attributes.getValue("who"));
        analyzer.disconnect(position);
    }

    /**
     * 和牌时调用，包含和牌信息和对局结束时的各家分数等
     * <p>
     * 一次和牌的人可能有多个（荣和的情况）
     * <p>
     * 若标签上存在游戏结束的owari，则处理游戏结束
     */
    private void visitAGARI(Attributes attributes) {
        int position = Integer.parseInt(attributes.getValue("who"));
        int from = Integer.parseInt(attributes.getValue("fromWho"));
        String scoreCsv = attributes.getValue("ten");
        String[] splitedScoreCsv = scoreCsv.split(",");
        int hu = Integer.parseInt(splitedScoreCsv[0]);
        int score = Integer.parseInt(splitedScoreCsv[1]);
        int han = 0;
        ArrayList<String> yaku = new ArrayList<>();
        String yakuCsv = attributes.getValue("yaku");
        if (yakuCsv != null) {
            String[] splitedYakuCsv = yakuCsv.split(",");
            for (int i = 0; i < splitedYakuCsv.length; i += 2) {
                int yakuId = Integer.parseInt(splitedYakuCsv[i]);
                int n = Integer.parseInt(splitedYakuCsv[i + 1]);
                if (yakuId < 52) {
                    yaku.add(TenhouPaifuStringPool.YANKUS[yakuId]);
                } else {
                    yaku.add(TenhouPaifuStringPool.YANKUS[yakuId] + (n >= 2 ? n : ""));
                }
                han += n;
            }
        }
        String yakumanCsv = attributes.getValue("yakuman");
        if (yakumanCsv != null) {
            String[] splitedYakumanCsv = yakumanCsv.split(",");
            for (String aSplitedYakumanCsv : splitedYakumanCsv) {
                yaku.add(TenhouPaifuStringPool.YANKUS[Integer.parseInt(aSplitedYakumanCsv)]);
            }
            han = splitedYakumanCsv.length * 13;
        }
        int[] increaseAndDecrease = new int[4];
        String scCsv = attributes.getValue("sc");
        String[] splitedScCsv = scCsv.split(",");
        for (int i = 0; i < 4; i++) {
            int transferPoint = Integer.parseInt(splitedScCsv[2 * i + 1]) * 100;
            increaseAndDecrease[i] = Integer.parseInt(splitedScCsv[2 * i]) * 100 + transferPoint;
        }
        analyzer.agari(position, from, yaku, han, hu, score, increaseAndDecrease);
        // 由于可能有多人荣和，此时不能直接调用结束对局
        // 需要在多人点数全部计算后，再结束。这个时点会触发在下一局开始，或者结束游戏时
        // 看startElement方法中INIT部分
        isAgari = true;
        this.increaseAndDecrease = increaseAndDecrease;

        String owariCsv = attributes.getValue("owari");
        if (owariCsv != null) {
            analyzer.endKyoku(increaseAndDecrease);
            owari(owariCsv);
        }
    }

    /**
     * 流局时调用
     */
    private void visitRYUUKYOKU(Attributes attributes) {
        int[] increaseAndDecrease = new int[4];
        String scCsv = attributes.getValue("sc");
        String type = attributes.getValue("type");
        String[] splitedScCsv = scCsv.split(",");
        for (int i = 0; i < 4; i++) {
            int transferPoint = Integer.parseInt(splitedScCsv[2 * i + 1]) * 100;
            increaseAndDecrease[i] = Integer.parseInt(splitedScCsv[2 * i]) * 100 + transferPoint;
        }
        analyzer.ryuukyoku(increaseAndDecrease, type);
        // 流局时可直接结束对局
        analyzer.endKyoku(increaseAndDecrease);

        String owariCsv = attributes.getValue("owari");
        owari(owariCsv);
    }

    /**
     * 处理上一局的对局结束事件，由{@link #startElement}中INIT分支调用。当上一局自摸或者荣和时，
     * 若比赛结束会立直处理。若存在存在下一局，则在下一局开始前调用该方法。目的是为了兼容多人和了的情形
     */
    private void handleEndKyoku() {
        if (isAgari) {
            analyzer.endKyoku(increaseAndDecrease);
            isAgari = false;
            increaseAndDecrease = null;
        }
    }

    /**
     * 对局结束，包含结束时的分数信息
     */
    private void owari(String owariCsv) {
        if (owariCsv != null) {
            int playerSize = isSanma ? 3 : 4;
            // 数组为 a的点数，a的分数，b的点数，b的分数...d的分数
            String[] splitedOwariCsv = owariCsv.split(",");
            int[] playerPoints = new int[playerSize];
            int[] playerScores = new int[playerSize];
            for (int i = 0, j = 0; i < splitedOwariCsv.length; i += 2, j++) {
                playerPoints[j] = (int) Float.parseFloat(splitedOwariCsv[i]) * 100;
                playerScores[j] = (int) Float.parseFloat(splitedOwariCsv[i + 1]);
            }
            analyzer.endGame(playerPoints, playerScores);
        }
    }
}