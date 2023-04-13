package com.github.terralian.fastmaj.test.tehai;

import java.util.Set;

import com.github.terralian.fastmaj.FastMajong;
import com.github.terralian.fastmaj.encode.EncodeMark;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.tehai.IYuukouhaiCalculator;
import com.github.terralian.fastmaj.tehai.TehaiBuilder;
import com.github.terralian.fastmaj.tehai.YuukouhaiCalculator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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

        TehaiBuilder builder = TehaiBuilder.from("678m999m2246888s");
        Set<IHai> before = yuukouhaiCalculator.calcMin(builder.get());

        builder.addOne("8s").annkan("8s");
        Set<IHai> after = yuukouhaiCalculator.calcMin(builder.get());
        assertEquals(before.size(), after.size());
        assertEquals(1, before.size());

        //
        tehai = TehaiBuilder.from("234789m0p11155z") //
                .pon("5z") //
                .get();
        Set<IHai> yuukous = yuukouhaiCalculator.calcMin(tehai);
        assertEquals(1, yuukous.size());

        // 鸣牌情况
        tehai = TehaiBuilder.from("456m2244p555s22z") //
                .minkan("5s") //
                .pon("2z") //
                .get();
        yuukous = yuukouhaiCalculator.calcMin(tehai);
        assertEquals(2, yuukous.size());
    }
}
