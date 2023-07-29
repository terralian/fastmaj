package com.github.terralian.fastmaj.test.encode;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.encode.EncodeMark;
import com.github.terralian.fastmaj.game.KazeEnum;
import com.github.terralian.fastmaj.hai.HaiPool;
import com.github.terralian.fastmaj.hai.HaiTypeEnum;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yama.IYama;
import com.github.terralian.fastmaj.yama.IYamaWorker;
import com.github.terralian.fastmaj.yama.worker.common.SimpleRandomYamaWorker;
import com.github.terralian.fastmaj.yama.Yama;

public class Encode34Test {

    @Test
    public void toMarkValue() {
        // 万
        assertEquals(1, Encode34.toMarkValue(Encode34.MAN_1));
        assertEquals(2, Encode34.toMarkValue(Encode34.MAN_2));
        assertEquals(3, Encode34.toMarkValue(Encode34.MAN_3));
        assertEquals(4, Encode34.toMarkValue(Encode34.MAN_4));
        assertEquals(5, Encode34.toMarkValue(Encode34.MAN_5));
        assertEquals(6, Encode34.toMarkValue(Encode34.MAN_6));
        assertEquals(7, Encode34.toMarkValue(Encode34.MAN_7));
        assertEquals(8, Encode34.toMarkValue(Encode34.MAN_8));
        assertEquals(9, Encode34.toMarkValue(Encode34.MAN_9));

        // 筒
        assertEquals(1, Encode34.toMarkValue(Encode34.PIN_1));
        assertEquals(2, Encode34.toMarkValue(Encode34.PIN_2));
        assertEquals(3, Encode34.toMarkValue(Encode34.PIN_3));
        assertEquals(4, Encode34.toMarkValue(Encode34.PIN_4));
        assertEquals(5, Encode34.toMarkValue(Encode34.PIN_5));
        assertEquals(6, Encode34.toMarkValue(Encode34.PIN_6));
        assertEquals(7, Encode34.toMarkValue(Encode34.PIN_7));
        assertEquals(8, Encode34.toMarkValue(Encode34.PIN_8));
        assertEquals(9, Encode34.toMarkValue(Encode34.PIN_9));

        // 索
        assertEquals(1, Encode34.toMarkValue(Encode34.SO_1));
        assertEquals(2, Encode34.toMarkValue(Encode34.SO_2));
        assertEquals(3, Encode34.toMarkValue(Encode34.SO_3));
        assertEquals(4, Encode34.toMarkValue(Encode34.SO_4));
        assertEquals(5, Encode34.toMarkValue(Encode34.SO_5));
        assertEquals(6, Encode34.toMarkValue(Encode34.SO_6));
        assertEquals(7, Encode34.toMarkValue(Encode34.SO_7));
        assertEquals(8, Encode34.toMarkValue(Encode34.SO_8));
        assertEquals(9, Encode34.toMarkValue(Encode34.SO_9));

        // 字
        assertEquals(EncodeMark.DONG, Encode34.toMarkValue(Encode34.DONG));
        assertEquals(EncodeMark.NAN, Encode34.toMarkValue(Encode34.NAN));
        assertEquals(EncodeMark.XI, Encode34.toMarkValue(Encode34.XI));
        assertEquals(EncodeMark.BEI, Encode34.toMarkValue(Encode34.BEI));
        assertEquals(EncodeMark.BAI, Encode34.toMarkValue(Encode34.BAI));
        assertEquals(EncodeMark.FA, Encode34.toMarkValue(Encode34.FA));
        assertEquals(EncodeMark.ZHONG, Encode34.toMarkValue(Encode34.ZHONG));

        // 红宝牌
        assertEquals(0, Encode34.toMarkValue(Encode34.MAN_5, true));
        assertEquals(5, Encode34.toMarkValue(Encode34.MAN_5, false));
        assertEquals(0, Encode34.toMarkValue(Encode34.MAN_1, true));
        assertEquals(1, Encode34.toMarkValue(Encode34.MAN_1, false));
    }

    @Test
    public void toMarkType() {
        for (int i = Encode34.MAN_1; i <= Encode34.MAN_9; i++) {
            assertEquals(HaiTypeEnum.M, Encode34.toMarkType(i));
        }
        for (int i = Encode34.PIN_1; i <= Encode34.PIN_9; i++) {
            assertEquals(HaiTypeEnum.P, Encode34.toMarkType(i));
        }
        for (int i = Encode34.SO_1; i <= Encode34.SO_9; i++) {
            assertEquals(HaiTypeEnum.S, Encode34.toMarkType(i));
        }
        for (int i = Encode34.DONG; i <= Encode34.ZHONG; i++) {
            assertEquals(HaiTypeEnum.Z, Encode34.toMarkType(i));
        }
    }

    @Test
    public void toMarkString() {
        // 万
        assertEquals("1m", Encode34.toMarkString(Encode34.MAN_1));
        assertEquals("2m", Encode34.toMarkString(Encode34.MAN_2));
        assertEquals("3m", Encode34.toMarkString(Encode34.MAN_3));
        assertEquals("4m", Encode34.toMarkString(Encode34.MAN_4));
        assertEquals("5m", Encode34.toMarkString(Encode34.MAN_5));
        assertEquals("6m", Encode34.toMarkString(Encode34.MAN_6));
        assertEquals("7m", Encode34.toMarkString(Encode34.MAN_7));
        assertEquals("8m", Encode34.toMarkString(Encode34.MAN_8));
        assertEquals("9m", Encode34.toMarkString(Encode34.MAN_9));

        // 筒
        assertEquals("1p", Encode34.toMarkString(Encode34.PIN_1));
        assertEquals("2p", Encode34.toMarkString(Encode34.PIN_2));
        assertEquals("3p", Encode34.toMarkString(Encode34.PIN_3));
        assertEquals("4p", Encode34.toMarkString(Encode34.PIN_4));
        assertEquals("5p", Encode34.toMarkString(Encode34.PIN_5));
        assertEquals("6p", Encode34.toMarkString(Encode34.PIN_6));
        assertEquals("7p", Encode34.toMarkString(Encode34.PIN_7));
        assertEquals("8p", Encode34.toMarkString(Encode34.PIN_8));
        assertEquals("9p", Encode34.toMarkString(Encode34.PIN_9));

        // 索
        assertEquals("1s", Encode34.toMarkString(Encode34.SO_1));
        assertEquals("2s", Encode34.toMarkString(Encode34.SO_2));
        assertEquals("3s", Encode34.toMarkString(Encode34.SO_3));
        assertEquals("4s", Encode34.toMarkString(Encode34.SO_4));
        assertEquals("5s", Encode34.toMarkString(Encode34.SO_5));
        assertEquals("6s", Encode34.toMarkString(Encode34.SO_6));
        assertEquals("7s", Encode34.toMarkString(Encode34.SO_7));
        assertEquals("8s", Encode34.toMarkString(Encode34.SO_8));
        assertEquals("9s", Encode34.toMarkString(Encode34.SO_9));

        // 字
        assertEquals("1z", Encode34.toMarkString(Encode34.DONG));
        assertEquals("2z", Encode34.toMarkString(Encode34.NAN));
        assertEquals("3z", Encode34.toMarkString(Encode34.XI));
        assertEquals("4z", Encode34.toMarkString(Encode34.BEI));
        assertEquals("5z", Encode34.toMarkString(Encode34.BAI));
        assertEquals("6z", Encode34.toMarkString(Encode34.FA));
        assertEquals("7z", Encode34.toMarkString(Encode34.ZHONG));
    }

    @Test
    public void toTehai_array() {
        for (int i = 0; i < 10000; i++) {
            Random random = new Random();
            int haiSize = random.nextInt(14);
            int[] haiValues = new int[haiSize];
            for (int j = 0; j < haiSize; j++) {
                haiValues[j] = random.nextInt(34);
            }
            ITehai tehai = Encode34.toTehai(haiValues);
            List<IHai> hais = tehai.getAll();
            assertEquals(haiSize, hais.size());
            for (int j = 0; j < haiSize; j++) {
                assertEquals(haiValues[j], hais.get(j).getValue());
            }
        }
    }

    @Test
    public void toTehai_list() {
        for (int i = 0; i < 10000; i++) {
            Random random = new Random();
            int haiSize = random.nextInt(14);
            List<Integer> haiValues = new ArrayList<>();
            for (int j = 0; j < haiSize; j++) {
                haiValues.add(random.nextInt(34));
            }
            ITehai tehai = Encode34.toTehai(haiValues);
            List<IHai> hais = tehai.getAll();
            assertEquals(haiSize, hais.size());
            for (int j = 0; j < haiSize; j++) {
                assertEquals(haiValues.get(j).intValue(), hais.get(j).getValue());
            }
        }
    }

    @Test
    public void toEncode34_136() {
        for (int i = 0; i < 136; i++) {
            assertEquals(HaiPool.getById(i).getValue(), Encode34.toEncode34(i));
        }
    }

    @Test
    public void toEncode34_kaze() {
        assertEquals(Encode34.DONG, Encode34.toEncode34(KazeEnum.DON));
        assertEquals(Encode34.XI, Encode34.toEncode34(KazeEnum.SYA));
        assertEquals(Encode34.NAN, Encode34.toEncode34(KazeEnum.NAN));
        assertEquals(Encode34.BEI, Encode34.toEncode34(KazeEnum.PEI));
    }

    @Test
    public void nextHai() {
        // 万
        assertEquals(Encode34.MAN_2, Encode34.nextHai(Encode34.MAN_1));
        assertEquals(Encode34.MAN_3, Encode34.nextHai(Encode34.MAN_2));
        assertEquals(Encode34.MAN_4, Encode34.nextHai(Encode34.MAN_3));
        assertEquals(Encode34.MAN_5, Encode34.nextHai(Encode34.MAN_4));
        assertEquals(Encode34.MAN_6, Encode34.nextHai(Encode34.MAN_5));
        assertEquals(Encode34.MAN_7, Encode34.nextHai(Encode34.MAN_6));
        assertEquals(Encode34.MAN_8, Encode34.nextHai(Encode34.MAN_7));
        assertEquals(Encode34.MAN_9, Encode34.nextHai(Encode34.MAN_8));
        assertEquals(Encode34.MAN_1, Encode34.nextHai(Encode34.MAN_9));

        // 筒
        assertEquals(Encode34.PIN_2, Encode34.nextHai(Encode34.PIN_1));
        assertEquals(Encode34.PIN_3, Encode34.nextHai(Encode34.PIN_2));
        assertEquals(Encode34.PIN_4, Encode34.nextHai(Encode34.PIN_3));
        assertEquals(Encode34.PIN_5, Encode34.nextHai(Encode34.PIN_4));
        assertEquals(Encode34.PIN_6, Encode34.nextHai(Encode34.PIN_5));
        assertEquals(Encode34.PIN_7, Encode34.nextHai(Encode34.PIN_6));
        assertEquals(Encode34.PIN_8, Encode34.nextHai(Encode34.PIN_7));
        assertEquals(Encode34.PIN_9, Encode34.nextHai(Encode34.PIN_8));
        assertEquals(Encode34.PIN_1, Encode34.nextHai(Encode34.PIN_9));

        // 索
        assertEquals(Encode34.SO_2, Encode34.nextHai(Encode34.SO_1));
        assertEquals(Encode34.SO_3, Encode34.nextHai(Encode34.SO_2));
        assertEquals(Encode34.SO_4, Encode34.nextHai(Encode34.SO_3));
        assertEquals(Encode34.SO_5, Encode34.nextHai(Encode34.SO_4));
        assertEquals(Encode34.SO_6, Encode34.nextHai(Encode34.SO_5));
        assertEquals(Encode34.SO_7, Encode34.nextHai(Encode34.SO_6));
        assertEquals(Encode34.SO_8, Encode34.nextHai(Encode34.SO_7));
        assertEquals(Encode34.SO_9, Encode34.nextHai(Encode34.SO_8));
        assertEquals(Encode34.SO_1, Encode34.nextHai(Encode34.SO_9));
        
        // 字
        assertEquals(Encode34.NAN, Encode34.nextHai(Encode34.DONG));
        assertEquals(Encode34.XI, Encode34.nextHai(Encode34.NAN));
        assertEquals(Encode34.BEI, Encode34.nextHai(Encode34.XI));
        assertEquals(Encode34.DONG, Encode34.nextHai(Encode34.BEI));
        assertEquals(Encode34.FA, Encode34.nextHai(Encode34.BAI));
        assertEquals(Encode34.ZHONG, Encode34.nextHai(Encode34.FA));
        assertEquals(Encode34.BAI, Encode34.nextHai(Encode34.ZHONG));
    }

    @Test
    public void isBigger() {
        assertTrue(Encode34.isBigger(HaiPool.m(9), HaiPool.m(1)));
        assertTrue(Encode34.isBigger(HaiPool.m(4), HaiPool.m(3)));
        assertTrue(Encode34.isBigger(HaiPool.m(6), HaiPool.m(1)));
        assertTrue(Encode34.isBigger(HaiPool.p(0), HaiPool.m(5)));

        assertFalse(Encode34.isBigger(HaiPool.m(0), HaiPool.m(5)));
    }

    @Test
    public void isYaotyuHai() {
        for (int i = 0; i < 136; i++) {
            IHai hai = HaiPool.getById(i);
            assertEquals(hai.isYaotyuHai(), Encode34.isYaotyuHai(hai.getValue()));
        }
    }

    @Test
    public void minArray() {
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            int size = random.nextInt(5) + 5;
            IHai[] array = new IHai[size];
            int minValue = 137;
            for (int j = 0; j < size; j++) {
                int r = random.nextInt(136);
                minValue = Math.min(minValue, r);
                array[j] = HaiPool.getById(r);
            }
            IHai minHai = Encode34.min(array);
            assertEquals(minValue / 4, minHai.getValue());
        }
    }

    @Test
    public void min() {
        assertEquals(Encode34.MAN_1, Encode34.min());
    }

    @Test
    public void length() {
        assertEquals(Encode34.max() + 1, Encode34.length());
    }

    @Test
    public void tehaiSize() {
        IYamaWorker yamaWorker = new SimpleRandomYamaWorker();
        for (int i = 0; i < 10000; i++) {
            IYama yama = new Yama(yamaWorker.getNextYama(1), true, 1);
            int tehaiSize = Encode34.tehaiSize(Encode34.toEncode34(yama.deals(0).get(0).getAll()));
            assertEquals(13, tehaiSize);
        }
    }


    @Test
    public void newArray() {
        int[] array = Encode34.newArray();
        assertEquals(Encode34.length(), array.length);
        for (int i = 0; i < array.length; i++) {
            assertEquals(0, array[i]);
        }
    }
}
