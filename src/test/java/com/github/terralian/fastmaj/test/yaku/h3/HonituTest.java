package com.github.terralian.fastmaj.test.yaku.h3;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.terralian.fastmaj.encode.EncodeMark;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.h3.Honitu;

/**
 * {@link Honitu} 混一色
 * 
 * @author terra.lian 
 */
public class HonituTest {

    @Test
    public void test() {
        IYaku yaku = new Honitu();
        // 不需要场况信息
        PlayerGameContext gameContext = new PlayerGameContext();
        // 手牌
        ITehai tehai = null;
        // 结果
        boolean result = false;

        // 是混一色
        tehai = EncodeMark.toTehai("123456789m11122z");
        result = yaku.match(tehai, null, gameContext);
        assertTrue(result);

        // 清一色，不合
        tehai = EncodeMark.toTehai("11223345678999m");
        result = yaku.match(tehai, null, gameContext);
        assertFalse(result);

        // 含其他色
        tehai = EncodeMark.toTehai("123456789m11p111z");
        result = yaku.match(tehai, null, gameContext);
        assertFalse(result);
    }
}
