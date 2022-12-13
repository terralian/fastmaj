package com.github.terralian.fastmaj.test.yaku.h2;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.terralian.fastmaj.encode.EncodeMark;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.h2.Syousangen;

/**
 * {@link Syousangen}小三元测试
 * 
 * @author terra.lian 
 */
public class SyousangenTest {

    @Test
    public void test() {
        IYaku yaku = new Syousangen();
        // 不需要场况信息
        PlayerGameContext gameContext = null;
        // 手牌
        ITehai tehai = null;
        // 结果
        boolean result = false;

        // 是小三元
        tehai = EncodeMark.toTehai("111m222p55566677z");
        result = yaku.match(tehai, null, gameContext);
        assertTrue(result);

        // 大三元
        tehai = EncodeMark.toTehai("111m22p555666777z");
        result = yaku.match(tehai, null, gameContext);
        assertFalse(result);

        // 都不是
        tehai = EncodeMark.toTehai("11m22p1122556677z");
        result = yaku.match(tehai, null, gameContext);
        assertFalse(result);
    }
}
