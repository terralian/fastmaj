package com.github.terralian.fastmaj.test.yaku.h1;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.encode.EncodeMark;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.h1.Tanyaotyu;

/**
 * {@link Tanyaotyu} 断幺九
 * 
 * @author terra.lian 
 */
public class TanyaotyuTest {

    @Test
    public void test() {
        IYaku yaku = new Tanyaotyu();
        // 不需要场况
        PlayerGameContext gameContext = null;
        // 手牌
        ITehai tehai = null;
        // 不需要手牌分割
        DivideInfo divide = null;
        // 结果
        boolean result = false;

        // 是断九幺
        tehai = EncodeMark.toTehai("234567m678p23477s");
        result = yaku.match(tehai, divide, gameContext);
        assertTrue(result);

        // 含幺九
        tehai = EncodeMark.toTehai("123456m678p23477s");
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);

        // 含字牌
        tehai = EncodeMark.toTehai("234567m678p234s11z");
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);

        tehai = EncodeMark.toTehai("234567m678p234s55z");
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);
    }
}
