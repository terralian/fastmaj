package com.github.terralian.fastmaj.test.yaku.h13;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.encode.EncodeMark;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.h13.Tinroutou;

/**
 * {@link Tinroutou}清老头测试
 * 
 * @author terra.lian 
 */
public class TinroutouTest {

    @Test
    public void test() {
        IYaku yaku = new Tinroutou();
        // 不需要场况
        PlayerGameContext gameContext = null;
        // 需要手牌
        ITehai tehai = null;
        // 不需要手牌分割
        DivideInfo divide = null;
        // 结果
        boolean result = false;

        // 清老头
        tehai = EncodeMark.toTehai("111999m111999p11s");
        result = yaku.match(tehai, divide, gameContext);
        assertTrue(result);

        // 纯全
        tehai = EncodeMark.toTehai("123999m111999p11s");
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);

        // 混老头
        tehai = EncodeMark.toTehai("111999m111999p11z");
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);

        // 其他
        tehai = EncodeMark.toTehai("111333555777m22p");
        result = yaku.match(tehai, null, gameContext);
        assertFalse(result);
    }
}
