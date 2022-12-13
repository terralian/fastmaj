package com.github.terralian.fastmaj.test.yaku.h1;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.encode.EncodeMark;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.h1.Haku;
import com.github.terralian.fastmaj.yaku.h1.Hatu;
import com.github.terralian.fastmaj.yaku.h1.Tyun;

/**
 * 三元牌测试: 白{@link Haku}， 发{@link Hatu}， 中{@link Tyun}
 * 
 * @author terra.lian 
 */
public class SanGenTest {

    @Test
    public void test() {
        // 不需要手牌分割
        DivideInfo divide = null;
        // 不需要上下文
        PlayerGameContext gameContext = null;

        // 白
        IYaku yaku = new Haku();

        // 手牌
        ITehai tehai = null;
        // 结果
        boolean result = false;

        tehai = EncodeMark.toTehai("111m111s111p55566z");
        result = yaku.match(tehai, divide, gameContext);
        assertTrue(result);

        tehai = EncodeMark.toTehai("111m111s111p66677z");
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);

        tehai = EncodeMark.toTehai("111m111s111p55777z");
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);

        // 发
        yaku = new Hatu();

        tehai = EncodeMark.toTehai("111m111s111p55666z");
        result = yaku.match(tehai, divide, gameContext);
        assertTrue(result);

        tehai = EncodeMark.toTehai("111m111s111p66777z");
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);

        tehai = EncodeMark.toTehai("111m111s111p55777z");
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);

        // 中
        yaku = new Tyun();

        tehai = EncodeMark.toTehai("111m111s111p55777z");
        result = yaku.match(tehai, divide, gameContext);
        assertTrue(result);

        tehai = EncodeMark.toTehai("111m111s111p66677z");
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);

        tehai = EncodeMark.toTehai("111m111s111p55666z");
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);
    }
}
