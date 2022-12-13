package com.github.terralian.fastmaj.test.yaku.h13;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.encode.EncodeMark;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.h13.Ryuuiisou;

/**
 * {@link Ryuuiisou}绿一色测试
 * 
 * @author terra.lian 
 */
public class RyuuiisouTest {

    @Test
    public void test() {
        IYaku yaku = new Ryuuiisou();
        // 不需要场况信息
        PlayerGameContext gameContext = null;
        // 需要手牌
        ITehai tehai = null;
        // 不需要手牌分割
        DivideInfo divide = null;
        // 结果
        boolean result = false;

        // 纯数牌绿一色
        tehai = EncodeMark.toTehai("22233344466688s");
        result = yaku.match(tehai, divide, gameContext);
        assertTrue(result);

        // 纯数牌，但是清一色
        tehai = EncodeMark.toTehai("1223334466688s");
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);

        // 纯数牌，但是不是索子
        tehai = EncodeMark.toTehai("22233344466688p");
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);

        // 包含发
        tehai = EncodeMark.toTehai("222333444666s66z");
        result = yaku.match(tehai, divide, gameContext);
        assertTrue(result);

        tehai = EncodeMark.toTehai("22233344666s666z");
        result = yaku.match(tehai, divide, gameContext);
        assertTrue(result);

        // 其他
        tehai = EncodeMark.toTehai("19m19s1p11234567z9p");
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);
    }
}
