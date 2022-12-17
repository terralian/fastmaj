package com.github.terralian.fastmaj.test.game;

import static org.junit.Assert.assertEquals;

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

import org.junit.Test;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.game.StreamMajongGame;
import com.github.terralian.fastmaj.game.builder.MajongGameBuilder;
import com.github.terralian.fastmaj.player.QueueReplayPlayer;
import com.github.terralian.fastmaj.util.ZipUtil;
import com.github.terralian.fastmaj.yama.TenhouYamaWorker;

public class TenhouPackageTest {

    /**
     * 大批量使用天凤位牌谱进行测试
     */
    @Test
    public void testBaseOnTenhouPackage() {
        System.out.println("--- mjlog_pf4-20_n19 ---");
        testBaseOnTenhouPackage0("mjlog_pf4-20_n19.zip");
        System.out.println("--- mjlog_pf4-20_n20 ---");
        testBaseOnTenhouPackage0("mjlog_pf4-20_n20.zip");
        System.out.println("--- mjlog_pf4-20_n22 ---");
        testBaseOnTenhouPackage0("mjlog_pf4-20_n22.zip");
    }

    private void testBaseOnTenhouPackage0(String fileName) {
        File file = null;
        try {
            file = new File(this.getClass().getClassLoader().getResource("tehou/" + fileName).getPath());
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
                String zipString = ZipUtil.ungzip(zipFile.getInputStream(entry));
                currentName = entry.getName().split("/")[1];
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
            String resourcePath = projectPath + "/src/test/resources/tehou";
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
        System.out.println(order + ": " + name);
        order++;

        TenhouTestPlayerBuilder builder = new TenhouTestPlayerBuilder();
        builder.getPaifuParser().parseStream(gzipStream);

        GameConfig config = builder.getConfig();
        List<QueueReplayPlayer> players = builder.getValue();

        StreamMajongGame majongGame = MajongGameBuilder.withDefault() //
                .setYamaWorker(new TenhouYamaWorker(builder.getSeed())) //
                .setConfig(config) //
                // .addGameLogger(new PrintGameLogger())//
                .build(players);
        // 东风战
        majongGame.setConfig(builder.getConfig());

        majongGame.startGame();

        int[] expected = builder.getPlayerPoints();
        int[] actual = majongGame.getGameCore().getPlayerPoints();
        for (int i = 0; i < players.size(); i++) {
            assertEquals(expected[i], actual[i]);
        }
    }
}
