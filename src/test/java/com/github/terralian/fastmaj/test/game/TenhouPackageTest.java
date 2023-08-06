package com.github.terralian.fastmaj.test.game;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.StreamMajongGame;
import com.github.terralian.fastmaj.game.builder.MajongGameBuilder;
import com.github.terralian.fastmaj.paifu.IPaifuParser;
import com.github.terralian.fastmaj.paifu.domain.PaifuGame;
import com.github.terralian.fastmaj.paifu.source.GZIPSource;
import com.github.terralian.fastmaj.paifu.source.InputSource;
import com.github.terralian.fastmaj.paifu.tenhou.TenhouPaifuGameParseHandler;
import com.github.terralian.fastmaj.paifu.tenhou.TenhouPaifuParser;
import com.github.terralian.fastmaj.paifu.tenhou.TenhouRuleTimelineHandler;
import com.github.terralian.fastmaj.player.PaifuGameQueueReplayPlayerBuilder;
import com.github.terralian.fastmaj.player.QueueReplayPlayer;
import com.github.terralian.fastmaj.util.TestResourceUtil;
import com.github.terralian.fastmaj.util.ZipUtil;
import com.github.terralian.fastmaj.yama.worker.UnsupportedYamaSeedException;
import com.github.terralian.fastmaj.yama.worker.tenhou.TenhouYamaWorker;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TenhouPackageTest {

    private IPaifuParser<PaifuGame> paifuParser;
    private TenhouRuleTimelineHandler ruleTimelineHandler;

    @Before
    public void before() {
        paifuParser = new TenhouPaifuParser(new TenhouPaifuGameParseHandler());
        ruleTimelineHandler = new TenhouRuleTimelineHandler();
    }

    /**
     * 大批量使用天凤位牌谱进行测试
     * <p/>
     * 天凤位中，ウルトラ立直的牌谱ZIP包中，前面存在不少0KB的错误，建议跳过mjlog_pf4-20_n10.zip
     */
    @Test
    public void testBaseOnTenhouPackage() {
        String prefix = "mjlog_pf4-20_n";
        for (int i = 14; i <= 22; i++) {
            String packageName = prefix + i + ".zip";
            System.out.println("--- " + packageName + " ---");
            testBaseOnTenhouPackage0(packageName);
        }
    }

    /**
     * 旧版测试逻辑，先保留用于保证代码不会被改错
     */
    @Test
    public void old_test_package() {
        System.out.println("--- mjlog_pf4-20_n19 ---");
        testBaseOnTenhouPackage0("mjlog_pf4-20_n19.zip");
        System.out.println("--- mjlog_pf4-20_n20 ---");
        testBaseOnTenhouPackage0("mjlog_pf4-20_n20.zip");
        System.out.println("--- mjlog_pf4-20_n22 ---");
        testBaseOnTenhouPackage0("mjlog_pf4-20_n22.zip");
    }

    private void testBaseOnTenhouPackage0(String fileName) {
        File file;
        try {
            file = TestResourceUtil.readToFile("tenhou", fileName);
        } catch (Exception e) {
            System.out.println("未设置测试资源牌谱包，跳过测试：" + fileName);
            return;
        }

        byte[] data = null;
        String currentName = null;
        try (ZipFile zipFile = new ZipFile(file, Charset.forName("CP866"))) {
            Enumeration<? extends ZipEntry> enums = zipFile.entries();
            while (enums.hasMoreElements()) {
                ZipEntry entry = enums.nextElement();
                if (entry.isDirectory()) {
                    continue;
                }

                // 读取到内存，若出错则保存
                String zipString = ZipUtil.unGzip(zipFile.getInputStream(entry));
                String[] splits = entry.getName().split("/");
                currentName = splits[splits.length - 1];
                // 转回GZIP
                ByteArrayOutputStream bi = new ByteArrayOutputStream();
                GZIPOutputStream gzipStream = new GZIPOutputStream(bi);
                gzipStream.write(zipString.getBytes());
                gzipStream.flush();
                gzipStream.finish();
                data = bi.toByteArray();
                // 解析
                testBaseOnTenhouPackage1(entry.getName(), new ByteArrayInputStream(data));
            }
        } catch (Error | Exception e) {
            // 保存文件
            String projectPath = System.getProperty("user.dir").replaceAll("\\\\", "/");
            String resourcePath = projectPath + "/src/test/resources/tenhou";
            String fullName = resourcePath + "/" + currentName;
            file = new File(fullName);
            try (FileOutputStream fo = new FileOutputStream(file)) {
                fo.write(data);
                fo.flush();
                System.out.println("保存异常牌谱完成：" + currentName);
            } catch (Exception e1) {
                System.out.println("保存异常牌谱失败");
            }
            throw new RuntimeException(e);
        }
    }

    private int order = 1;

    private void testBaseOnTenhouPackage1(String name, InputStream gzipStream) throws Exception {
        System.out.print(order + ": " + name);
        order++;

        PaifuGame paifuGame;
        try {
            paifuGame = paifuParser.parse(new GZIPSource(new InputSource(gzipStream)));

        } catch (UnsupportedYamaSeedException e) {
            System.out.println("   > 旧牌谱，跳过...");
            return;
        }
        System.out.println();

        GameConfig config = GameConfig.useTenhou(paifuGame);
        ruleTimelineHandler.deduce(config, paifuGame, name);

        List<QueueReplayPlayer> players = PaifuGameQueueReplayPlayerBuilder.toPlayer(paifuGame);

        StreamMajongGame majongGame = MajongGameBuilder.withDefault() //
                .setYamaWorker(new TenhouYamaWorker(paifuGame.getSeed())) //
                .setConfig(config) //
                // .addGameLogger(new PrintGameLogger())//
                .build(players);
        // 东风战
        majongGame.setConfig(config);

        majongGame.startGame();

        int[] expected = paifuGame.getEndPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }
}
