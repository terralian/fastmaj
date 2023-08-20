package com.github.terralian.fastmaj.test.game;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.github.terralian.fastmaj.paifu.source.StringSource;
import com.github.terralian.fastmaj.util.TestResourceUtil;
import com.github.terralian.fastmaj.util.ZipUtil;
import com.github.terralian.fastmaj.yama.worker.UnsupportedYamaSeedException;
import org.junit.Test;

/**
 * 使用天凤位的牌谱集进行牌谱测试
 *
 * @author Terra.Lian
 */
public abstract class AbstractTenhouPaifuPackageTest extends AbstractTenhouPaifuTest {

    private int order = 1;

    /**
     * 大批量使用天凤位牌谱进行测试
     * <p/>
     * 天凤位中，ウルトラ立直的牌谱ZIP包中，前面存在不少0KB的错误，建议跳过mjlog_pf4-20_n10.zip
     */
    @Test
    public void testBaseOnTenhouPackage() {
        order = 1;

        String prefix = "mjlog_pf4-20_n";
        for (int i = 14; i <= 22; i++) {
            String packageName = prefix + i + ".zip";
            System.out.println("--- " + packageName + " ---");
            // testBaseOnTenhouPackage0(packageName);
        }
    }

    @Test
    public void old_test_package() {
        order = 1;

        System.out.println("--- mjlog_pf4-20_n19 ---");
        testBaseOnTenhouPackage0("mjlog_pf4-20_n19.zip");
        System.out.println("--- mjlog_pf4-20_n20 ---");
        testBaseOnTenhouPackage0("mjlog_pf4-20_n20.zip");
        System.out.println("--- mjlog_pf4-20_n22 ---");
        testBaseOnTenhouPackage0("mjlog_pf4-20_n22.zip");
    }

    private void testBaseOnTenhouPackage0(String fileName) {
        File file = TestResourceUtil.tryReadToFile("tenhou", fileName);
        if (file == null) {
            System.out.println("未设置测试资源牌谱包，跳过测试：" + fileName);
            return;
        }

        String data = null;
        String currentName = null;
        try (ZipFile zipFile = new ZipFile(file, Charset.forName("CP866"))) {
            Enumeration<? extends ZipEntry> enums = zipFile.entries();
            while (enums.hasMoreElements()) {
                ZipEntry entry = enums.nextElement();
                if (entry.isDirectory()) {
                    continue;
                }
                // 读取到内存，若出错则保存
                data = ZipUtil.unGzip(zipFile.getInputStream(entry));
                String[] splits = entry.getName().split("/");
                currentName = splits[splits.length - 1];
                // 解析
                testBaseOnTenhouPackage1(entry.getName(), data);
            }
        } catch (Error | Exception e) {
            if (data == null) {
                throw new RuntimeException(e);
            }
            // 保存文件
            String projectPath = System.getProperty("user.dir").replaceAll("\\\\", "/");
            String resourcePath = projectPath + "/src/test/resources/tenhou";
            String fullName = resourcePath + "/" + currentName;
            file = new File(fullName);
            try (FileOutputStream fo = new FileOutputStream(file)) {
                // 转回GZIP
                ByteArrayOutputStream bi = new ByteArrayOutputStream();
                GZIPOutputStream gzipStream = new GZIPOutputStream(bi);
                gzipStream.write(data.getBytes(StandardCharsets.UTF_8));
                gzipStream.flush();
                gzipStream.finish();
                byte[] array = bi.toByteArray();
                fo.write(array);
                fo.flush();
                System.out.println("保存异常牌谱完成：" + currentName);
            } catch (Exception e1) {
                System.out.println("保存异常牌谱失败");
            }
            throw new RuntimeException(e);
        }
    }

    private void testBaseOnTenhouPackage1(String name, String paifuContent) throws Exception {
        System.out.print(order + ": " + name);
        order++;
        try {
            simulate_run_game(name, new StringSource(paifuContent), LogType.NONE);
        } catch (UnsupportedYamaSeedException e) {
            System.out.println("   > 旧牌谱，跳过...");
        } finally {
            System.out.println();
        }
    }
}
