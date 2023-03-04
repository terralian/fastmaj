package com.github.terralian.fastmaj.test.yaku.h13;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.terralian.fastmaj.encode.EncodeMark;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.hai.HaiPool;
import com.github.terralian.fastmaj.player.RivalEnum;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.h13.Suukanzu;

/**
 * {@link com.github.terralian.fastmaj.yaku.h13.Suukanzu}四杠子测试
 * 
 * @author terra.lian 
 */
public class SuukanzuTest {

    @Test
    public void test() {
        IYaku yaku = new Suukanzu();
        // 需要荣和信息
        PlayerGameContext gameContext = null;
        // 需要手牌
        ITehai tehai = null;
        // 结果
        boolean result = false;

        // 四暗刻
        tehai = EncodeMark.toTehai("111333555777m22p");
        result = yaku.match(tehai, null, gameContext);
        assertFalse(result);

        // 四暗刻，四杠子
        tehai = EncodeMark.toTehai("111122223333444455m");
        tehai.annkan(HaiPool.m(1));
        tehai.annkan(HaiPool.m(2));
        tehai.annkan(HaiPool.m(3));
        tehai.annkan(HaiPool.m(4));
        result = yaku.match(tehai, null, gameContext);
        assertTrue(result);

        // 鸣牌四杆子
        tehai = EncodeMark.toTehai("1112223333444455m");
        tehai.minkan(HaiPool.m(1), RivalEnum.BOTTOM);
        tehai.minkan(HaiPool.m(2), RivalEnum.BOTTOM);
        tehai.annkan(HaiPool.m(3));
        tehai.annkan(HaiPool.m(4));
        result = yaku.match(tehai, null, gameContext);
        assertTrue(result);
    }
}
