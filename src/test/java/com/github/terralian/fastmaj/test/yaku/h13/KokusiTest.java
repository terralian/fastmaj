package com.github.terralian.fastmaj.test.yaku.h13;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.encode.EncodeMark;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.h13.Kokusi;

/**
 * {@link Kokusi} 国士无双
 * 
 * @author terra.lian 
 */
public class KokusiTest {

    @Test
    public void test() {
        IYaku yaku = new Kokusi();
        // 国士判定需要和了牌信息
        PlayerGameContext gameContext = null;
        // 需要手牌
        ITehai tehai = null;
        // 不需要手牌分割
        DivideInfo divide = null;
        // 结果
        boolean result = false;

        // 国士，和了牌是其中一枚
        tehai = EncodeMark.toTehai("19m19s1p11234567z9p");
        result = yaku.match(tehai, divide, gameContext);
        assertTrue(result);

        // 国士13面
        tehai = EncodeMark.toTehai("19m19s19p1234567z1z");
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);

        // 非国士
        tehai = EncodeMark.toTehai("12355m555666777z");
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);
    }
}
