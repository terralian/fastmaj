package com.github.terralian.fastmaj.test.tehai.syanten;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.github.terralian.fastmaj.tehai.FastSyantenCalculator;
import com.github.terralian.fastmaj.util.StopWatch;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * {@link FastSyantenCalculator} 测试
 * 
 * @author terra.lian 
 */
public class FastSyantenCalculatorTest extends SyantenCalculatorTest {

    @Before
    @Override
    public void before() {
        super.calculator = new FastSyantenCalculator();
    }

    @Test
    public void fastSampleTest() {
        int[] testCase = new int[]{1, 1, 1, 0, 0, 0, 0, 0, 0, // manzu
                0, 1, 0, 1, 1, 0, 2, 0, 1, // pinzu
                0, 0, 0, 0, 0, 0, 0, 0, 0, // souzu
                1, 0, 1, 0, 3, 0, 0// jihai
        };
        int syaten = calculator.calcNormal(testCase);
        assertEquals(2, syaten);
    }

    @Test
    public void loadSyantenTimeTest() throws IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String fileName = FastSyantenCalculator.class.getClassLoader().getResource("syanten.zip").getPath();
        File file = new File(fileName);
        try (ZipFile zipFile = new ZipFile(file)) {
            Enumeration<? extends ZipEntry> enums = zipFile.entries();
            Map<String, ZipEntry> map = new HashMap<>();
            while (enums.hasMoreElements()) {
                ZipEntry entry = enums.nextElement();
                map.put(entry.getName(), entry);
            }
            System.out.println(zipFile.getInputStream(map.get("index_s.csv")));
            System.out.println(zipFile.getInputStream(map.get("index_h.csv")));
        }
        stopWatch.stop();
        System.out.println(stopWatch.shortSummary());
    }
}
