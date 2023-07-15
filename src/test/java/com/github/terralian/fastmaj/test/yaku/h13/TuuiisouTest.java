package com.github.terralian.fastmaj.test.yaku.h13;

import com.github.terralian.fastmaj.encode.EncodeMark;
import com.github.terralian.fastmaj.hai.HaiPool;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.tehai.TehaiBuilder;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.h13.Tuuiisou;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * {@link Tuuiisou} 字一色测试
 *
 * @author terra.lian
 */
public class TuuiisouTest {

    private final IYaku yaku = new Tuuiisou();

    /**
     * 暗刻字一色
     */
    @Test
    public void test_kozu_tuuiisou() {
        ITehai tehai = EncodeMark.toTehai("11122233344455z");
        usingNullConfigTest(true, tehai);

        tehai = EncodeMark.toTehai("11133355566777z");
        usingNullConfigTest(true, tehai);
    }

    /**
     * 七对子字一色
     */
    @Test
    public void test_tiitoitu_tuuiisou() {
        ITehai tehai = EncodeMark.toTehai("11223344556677z");
        usingNullConfigTest(true, tehai);
    }

    /**
     * 若手牌全是白板（14枚），按定义这时候应当也是字一色，TRUE
     */
    @Test
    public void test_if_true_when_tehai_all_haku() {
        ITehai tehai = TehaiBuilder.empty() //
                .addSame(HaiPool.z(EncodeMark.BAI), 14) //
                .get();
        usingNullConfigTest(true, tehai);
    }

    /**
     * 混一色情况下，非字一色
     */
    @Test
    public void test_honitu() {
        ITehai tehai = EncodeMark.toTehai("11m111222333444z");
        usingNullConfigTest(false, tehai);
    }

    /**
     * 使用空参数(divide,gameContext)，对手牌进行测试，判定结果是否和预期相同
     *
     * @param expect 期待值
     * @param tehai 手牌
     */
    private void usingNullConfigTest(boolean expect, ITehai tehai) {
        assertEquals(expect, yaku.match(tehai, null, null));
    }
}
