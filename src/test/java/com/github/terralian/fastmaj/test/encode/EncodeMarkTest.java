package com.github.terralian.fastmaj.test.encode;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.encode.EncodeMark;
import com.github.terralian.fastmaj.hai.HaiPool;
import com.github.terralian.fastmaj.hai.HaiTypeEnum;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yama.IYama;
import com.github.terralian.fastmaj.yama.IYamaWorker;
import com.github.terralian.fastmaj.yama.SimpleRandomYamaWorker;
import com.github.terralian.fastmaj.yama.Yama;

public class EncodeMarkTest {

    @Test
    public void maxValue() {
        assertEquals(9, EncodeMark.maxValue(HaiTypeEnum.M));
        assertEquals(9, EncodeMark.maxValue(HaiTypeEnum.P));
        assertEquals(9, EncodeMark.maxValue(HaiTypeEnum.S));
        assertEquals(EncodeMark.ZHONG, EncodeMark.maxValue(HaiTypeEnum.Z));
    }

    @Test
    public void encode_literal() {
        assertEquals("1m", EncodeMark.encode(1, HaiTypeEnum.M));
        assertEquals("2p", EncodeMark.encode(2, HaiTypeEnum.P));
        assertEquals("3s", EncodeMark.encode(3, HaiTypeEnum.S));
        assertEquals("4z", EncodeMark.encode(4, HaiTypeEnum.Z));
        assertEquals("0m", EncodeMark.encode(0, HaiTypeEnum.M));
        assertEquals("0p", EncodeMark.encode(0, HaiTypeEnum.P));
        assertEquals("0s", EncodeMark.encode(0, HaiTypeEnum.S));
    }

    @Test
    public void encode_hai() {
        assertEquals("1m", EncodeMark.encode(HaiPool.m(1)));
        assertEquals("2p", EncodeMark.encode(HaiPool.p(2)));
        assertEquals("3s", EncodeMark.encode(HaiPool.s(3)));
        assertEquals("4z", EncodeMark.encode(HaiPool.z(4)));

        assertEquals("5m", EncodeMark.encode(HaiPool.m(5)));
        assertEquals("5p", EncodeMark.encode(HaiPool.p(5)));
        assertEquals("5s", EncodeMark.encode(HaiPool.s(5)));

        assertEquals("0m", EncodeMark.encode(HaiPool.getByMark(0, HaiTypeEnum.M)));
        assertEquals("0p", EncodeMark.encode(HaiPool.getByMark(0, HaiTypeEnum.P)));
        assertEquals("0s", EncodeMark.encode(HaiPool.getByMark(0, HaiTypeEnum.S)));
    }

    @Test
    public void encode_tehai() {
        ITehai tehai = EncodeMark.toTehai("6p4p7s9s5p8s1z5m1z2m3m1m1z8m");
        assertEquals("1235m456p789s111z8m", EncodeMark.encode(tehai));

        tehai = EncodeMark.toTehai("6p0s3s4m2m4z2z6p7z7z6p3s3z");
        assertEquals("24m666p330s23477z", EncodeMark.encode(tehai));
    }

    @Test
    public void toEncode34_literal() {
        assertEquals(Encode34.MAN_1, EncodeMark.toEncode34(1, HaiTypeEnum.M));
        assertEquals(Encode34.MAN_2, EncodeMark.toEncode34(2, HaiTypeEnum.M));
        assertEquals(Encode34.MAN_3, EncodeMark.toEncode34(3, HaiTypeEnum.M));
        assertEquals(Encode34.MAN_4, EncodeMark.toEncode34(4, HaiTypeEnum.M));
        assertEquals(Encode34.MAN_5, EncodeMark.toEncode34(5, HaiTypeEnum.M));
        assertEquals(Encode34.MAN_6, EncodeMark.toEncode34(6, HaiTypeEnum.M));
        assertEquals(Encode34.MAN_7, EncodeMark.toEncode34(7, HaiTypeEnum.M));
        assertEquals(Encode34.MAN_8, EncodeMark.toEncode34(8, HaiTypeEnum.M));
        assertEquals(Encode34.MAN_9, EncodeMark.toEncode34(9, HaiTypeEnum.M));

        assertEquals(Encode34.PIN_1, EncodeMark.toEncode34(1, HaiTypeEnum.P));
        assertEquals(Encode34.PIN_2, EncodeMark.toEncode34(2, HaiTypeEnum.P));
        assertEquals(Encode34.PIN_3, EncodeMark.toEncode34(3, HaiTypeEnum.P));
        assertEquals(Encode34.PIN_4, EncodeMark.toEncode34(4, HaiTypeEnum.P));
        assertEquals(Encode34.PIN_5, EncodeMark.toEncode34(5, HaiTypeEnum.P));
        assertEquals(Encode34.PIN_6, EncodeMark.toEncode34(6, HaiTypeEnum.P));
        assertEquals(Encode34.PIN_7, EncodeMark.toEncode34(7, HaiTypeEnum.P));
        assertEquals(Encode34.PIN_8, EncodeMark.toEncode34(8, HaiTypeEnum.P));
        assertEquals(Encode34.PIN_9, EncodeMark.toEncode34(9, HaiTypeEnum.P));

        assertEquals(Encode34.SO_1, EncodeMark.toEncode34(1, HaiTypeEnum.S));
        assertEquals(Encode34.SO_2, EncodeMark.toEncode34(2, HaiTypeEnum.S));
        assertEquals(Encode34.SO_3, EncodeMark.toEncode34(3, HaiTypeEnum.S));
        assertEquals(Encode34.SO_4, EncodeMark.toEncode34(4, HaiTypeEnum.S));
        assertEquals(Encode34.SO_5, EncodeMark.toEncode34(5, HaiTypeEnum.S));
        assertEquals(Encode34.SO_6, EncodeMark.toEncode34(6, HaiTypeEnum.S));
        assertEquals(Encode34.SO_7, EncodeMark.toEncode34(7, HaiTypeEnum.S));
        assertEquals(Encode34.SO_8, EncodeMark.toEncode34(8, HaiTypeEnum.S));
        assertEquals(Encode34.SO_9, EncodeMark.toEncode34(9, HaiTypeEnum.S));

        assertEquals(Encode34.DONG, EncodeMark.toEncode34(1, HaiTypeEnum.Z));
        assertEquals(Encode34.NAN, EncodeMark.toEncode34(2, HaiTypeEnum.Z));
        assertEquals(Encode34.XI, EncodeMark.toEncode34(3, HaiTypeEnum.Z));
        assertEquals(Encode34.BEI, EncodeMark.toEncode34(4, HaiTypeEnum.Z));
        assertEquals(Encode34.BAI, EncodeMark.toEncode34(5, HaiTypeEnum.Z));
        assertEquals(Encode34.FA, EncodeMark.toEncode34(6, HaiTypeEnum.Z));
        assertEquals(Encode34.ZHONG, EncodeMark.toEncode34(7, HaiTypeEnum.Z));
    }

    @Test
    public void toEncode34_string() {
        for (int i = 0; i < 10000; i++) {
            IYamaWorker worker = new SimpleRandomYamaWorker();
            int[] yamas = worker.getNextYama(1);
            IYama yama = new Yama(yamas, true, 1);
            ITehai tehai = yama.deals(0).get(0);
            String encodeString = EncodeMark.encode(tehai);
            int[] sourceValue = Encode34.toEncode34(tehai.getAll());
            int[] encodeValue = EncodeMark.toEncode34(encodeString);
            for (int j = 0; j < Encode34.length(); j++) {
                assertEquals(sourceValue[j], encodeValue[j]);
            }
        }
    }

    @Test
    public void toTehai() {

    }
}
