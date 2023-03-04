package com.github.terralian.fastmaj.test.tehai.Syaten;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.encode.EncodeMark;
import com.github.terralian.fastmaj.hai.HaiPool;
import com.github.terralian.fastmaj.tehai.ISyatenCalculator;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.tehai.SyatenCalculator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * 向听数计算类测试{@link com.github.terralian.fastmaj.tehai.SyatenCalculator}
 * <p/>
 * 该包下的向听数问题集数据来源于ara
 *
 * @author terra.lian
 * @see <a href="https://mahjong.ara.black/etc/shanten/shanten9.htm">问题集来源
 * :ara</a>
 */
public class SyatenCalculatorTest {

    protected ISyatenCalculator calculator;

    @Before
    public void before() {
        calculator = new SyatenCalculator();
    }

    @Test
    public void test() {
        String nameMark = "123m27p06899s157z6p";
        ITehai tehai = EncodeMark.toTehai(nameMark);
        assertEquals(3, calculator.calcMin(tehai.getHand()));

        nameMark = "12345678m11112z";
        tehai = EncodeMark.toTehai(nameMark);
        assertEquals(1, calculator.calcMin(tehai.getHand()));

        nameMark = "123569m11112222z";
        tehai = EncodeMark.toTehai(nameMark);
        assertEquals(1, calculator.calcMin(tehai.getHand()));

        nameMark = "1m3s111122223333z";
        tehai = EncodeMark.toTehai(nameMark);
        assertEquals(2, calculator.calcMin(tehai.getHand()));

    }

    @Test
    public void specialSample() {
        String nameMark;
        ITehai tehai;

        nameMark = "11114444777m111s";
        tehai = EncodeMark.toTehai(nameMark);
        assertEquals(1, calculator.calcMin(tehai.getHand()));

        // 特例（当为1向听时，若存在3种字牌4枚，算1向听）
        nameMark = "23s111122223333z";
        tehai = EncodeMark.toTehai(nameMark);
        assertEquals(2, calculator.calcMin(tehai.getHand()));

        // 特例（当为0向听时，若存在2种字牌4枚，算听牌，允许立直）
        nameMark = "11112222333444z";
        tehai = EncodeMark.toTehai(nameMark);
        assertEquals(1, calculator.calcMin(tehai.getHand()));

        // 3向听
        nameMark = "123m245779p1355z5z";
        tehai = EncodeMark.toTehai(nameMark);
        assertEquals(2, calculator.calcMin(tehai.getHand()));
    }

    /**
     * 仅13枚手牌时，或者1枚时的向听计算
     */
    @Test
    public void mitiSizeTest() {
        // 吃碰杠后仅1枚待牌
        ITehai tehai = EncodeMark.toTehai("23232323m1p");
        tehai.chii(HaiPool.m(1), HaiPool.m(2), HaiPool.m(3));
        tehai.chii(HaiPool.m(1), HaiPool.m(2), HaiPool.m(3));
        tehai.chii(HaiPool.m(1), HaiPool.m(2), HaiPool.m(3));
        tehai.chii(HaiPool.m(1), HaiPool.m(2), HaiPool.m(3));
        int syaten = calculator.calcNormal(tehai.getHand());
        assertEquals(0, syaten);
        syaten = calculator.calcNormal(Encode34.toEncode34(tehai.getHand()));
        assertEquals(0, syaten);

        // 吃碰杠后仅2枚待牌
        tehai = EncodeMark.toTehai("23232323m12p");
        tehai.chii(HaiPool.m(1), HaiPool.m(2), HaiPool.m(3));
        tehai.chii(HaiPool.m(1), HaiPool.m(2), HaiPool.m(3));
        tehai.chii(HaiPool.m(1), HaiPool.m(2), HaiPool.m(3));
        tehai.chii(HaiPool.m(1), HaiPool.m(2), HaiPool.m(3));
        syaten = calculator.calcNormal(tehai.getHand());
        assertEquals(0, syaten);
        syaten = calculator.calcNormal(Encode34.toEncode34(tehai.getHand()));
        assertEquals(0, syaten);
    }

    /**
     * 使用问题集进行测试
     *
     * @see <a href="https://mahjong.ara.black/etc/shanten/shanten9.htm">问题集来源 :https://mahjong.ara.black/etc/shanten/shanten9.htm</a>
     */
    @Test
    public void problemsTest() {
        // 左14个数为牌，右3个数为向听数（一般，国士，七对子）
        List<String> list = readFileToList("p_hon_10000.txt");
        problemsTest0(list);

        list = readFileToList("p_koku_10000.txt");
        problemsTest0(list);

        list = readFileToList("p_tin_10000.txt");
        problemsTest0(list);
    }

    private void problemsTest0(List<String> list) {
        for (String string : list) {
            String[] splits = string.split(" ");
            int[] value34 = new int[14];
            for (int i = 0; i < 14; i++) {
                value34[i] = Integer.parseInt(splits[i]);
            }
            int normal = Integer.parseInt(splits[14]);
            int kokusi = Integer.parseInt(splits[15]);
            int tiitoitu = Integer.parseInt(splits[16]);

            int minSyatenInput = Math.min(normal, kokusi);
            minSyatenInput = Math.min(minSyatenInput, tiitoitu);

            // 手牌方法
            ITehai tehai = Encode34.toTehai(value34);
            value34 = Encode34.toEncode34(tehai.getAll());

            // 编码方法
            assertEquals(normal, calculator.calcNormal(value34));
            assertEquals(kokusi, calculator.calcKokusi(value34));
            assertEquals(tiitoitu, calculator.calcTiitoitu(value34));
            assertEquals(minSyatenInput, calculator.calcMin(value34));

            // 手牌测试
            assertEquals(normal, calculator.calcNormal(tehai.getHand()));
            assertEquals(kokusi, calculator.calcKokusi(tehai.getHand()));
            assertEquals(tiitoitu, calculator.calcTiitoitu(tehai.getHand()));
            assertEquals(minSyatenInput, calculator.calcMin(tehai.getHand()));
        }
    }

    /**
     * 读取文件List中
     *
     * @param fileName 文件名
     */
    private List<String> readFileToList(String fileName) {
        fileName = this.getClass().getResource("/").getPath() + "/syaten/" + fileName;
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)))) {
            return reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
