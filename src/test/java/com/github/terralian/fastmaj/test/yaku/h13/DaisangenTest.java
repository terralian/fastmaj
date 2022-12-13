package com.github.terralian.fastmaj.test.yaku.h13;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.encode.EncodeMark;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.hai.HaiPool;
import com.github.terralian.fastmaj.player.RivalEnum;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.h13.Daisangen;

/**
 * {@link Daisangen}大三元测试
 * 
 * @author terra.lian 
 */
public class DaisangenTest {

    @Test
    public void test() {
        IYaku yaku = new Daisangen();
        // 不需要场况
        PlayerGameContext gameContext = null;
        // 需要手牌
        ITehai tehai = null;
        // 不需要手牌分割
        DivideInfo divide = null;
        // 结果
        boolean result = false;

        // 门清大三元
        tehai = EncodeMark.toTehai("12355m555666777z");
        result = yaku.match(tehai, divide, gameContext);
        assertTrue(result);

        // 鸣牌大三元
        tehai = EncodeMark.toTehai("12355m556677z");
        tehai.pon(HaiPool.z(5), false, RivalEnum.BOTTOM);
        tehai.pon(HaiPool.z(6), false, RivalEnum.BOTTOM);
        tehai.pon(HaiPool.z(7), false, RivalEnum.BOTTOM);
        result = yaku.match(tehai, divide, gameContext);
        assertTrue(result);

        // 三连暗杠岭上大三元
        tehai = EncodeMark.toTehai("1235m555666777z");
        tehai.draw(HaiPool.z(5));
        tehai.annkan(HaiPool.z(5));
        tehai.draw(HaiPool.z(6));
        tehai.annkan(HaiPool.z(6));
        tehai.draw(HaiPool.z(7));
        tehai.annkan(HaiPool.z(7));
        tehai.draw(HaiPool.m(5));
        result = yaku.match(tehai, divide, gameContext);
        assertTrue(result);

        // 小三元
        tehai = EncodeMark.toTehai("123555m55566677z");
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);
    }
}
