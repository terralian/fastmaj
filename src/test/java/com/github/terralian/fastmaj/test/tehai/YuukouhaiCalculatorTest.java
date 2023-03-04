package com.github.terralian.fastmaj.test.tehai;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import com.github.terralian.fastmaj.FastMajong;
import org.junit.Before;
import org.junit.Test;

import com.github.terralian.fastmaj.encode.EncodeMark;
import com.github.terralian.fastmaj.hai.HaiPool;
import com.github.terralian.fastmaj.hai.HaiTypeEnum;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.player.RivalEnum;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.tehai.IYuukouhaiCalculator;
import com.github.terralian.fastmaj.tehai.YuukouhaiCalculator;

/**
 * {@link YuukouhaiCalculator} 有效牌测试类
 * 
 * @author terra.lian 
 */
public class YuukouhaiCalculatorTest {

    private IYuukouhaiCalculator yuukouhaiCalculator;

    @Before
    public void before() {
        yuukouhaiCalculator = new YuukouhaiCalculator(FastMajong.doGetSyatenCalculator());
    }

    @Test
    public void test() {
        ITehai tehai = EncodeMark.toTehai("6m234678p136678s9s");
        yuukouhaiCalculator.calcMin(tehai);

        tehai = EncodeMark.toTehai("678m999m2246888s");
        Set<IHai> before = yuukouhaiCalculator.calcMin(tehai);
        tehai.draw(HaiPool.getByMark(8, HaiTypeEnum.S));
        tehai.annkan(HaiPool.getByMark(8, HaiTypeEnum.S));
        Set<IHai> after = yuukouhaiCalculator.calcMin(tehai);
        assertEquals(before.size(), after.size());
        assertEquals(1, before.size());

        //
        tehai = EncodeMark.toTehai("234789m0p11155z");
        tehai.pon(HaiPool.z(5), RivalEnum.TOP);
        Set<IHai> yuukous = yuukouhaiCalculator.calcMin(tehai);
        assertEquals(1, yuukous.size());

        // 鸣牌情况
        tehai = EncodeMark.toTehai("456m2244p555s22z");
        tehai.minkan(HaiPool.s(5), RivalEnum.BOTTOM);
        tehai.pon(HaiPool.z(2), RivalEnum.OPPO);
        yuukous = yuukouhaiCalculator.calcMin(tehai);
        assertEquals(2, yuukous.size());
    }
}
