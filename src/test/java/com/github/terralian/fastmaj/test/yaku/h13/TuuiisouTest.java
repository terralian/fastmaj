package com.github.terralian.fastmaj.test.yaku.h13;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.encode.EncodeMark;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.h13.Tuuiisou;

/**
 * {@link Tuuiisou} 字一色测试
 * 
 * @author terra.lian 
 */
public class TuuiisouTest {

    @Test
    public void test() {
        IYaku yaku = new Tuuiisou();
        // 不需要场况
        PlayerGameContext gameContext = null;
        // 需要手牌
        ITehai tehai = null;
        // 不需要手牌分割
        DivideInfo divide = null;
        // 结果
        boolean result = false;

        // 字一色
        tehai = EncodeMark.toTehai("11122233344455z");
        result = yaku.match(tehai, divide, gameContext);
        assertTrue(result);

        // 字一色
        tehai = EncodeMark.toTehai("11133355566777z");
        result = yaku.match(tehai, divide, gameContext);
        assertTrue(result);

        // 字一色七对子
        tehai = EncodeMark.toTehai("11223344556677z");
        result = yaku.match(tehai, divide, gameContext);
        assertTrue(result);

        // 混一色
        tehai = EncodeMark.toTehai("11m111222333444z");
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);
    }
}
