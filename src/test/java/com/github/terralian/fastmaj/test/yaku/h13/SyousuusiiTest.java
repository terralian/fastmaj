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
import com.github.terralian.fastmaj.yaku.h13.Syousuusii;

/**
 * {@link Syousuusii}小四喜
 * 
 * @author terra.lian 
 */
public class SyousuusiiTest {

    @Test
    public void test() {
        IYaku yaku = new Syousuusii();
        // 不需要场况
        PlayerGameContext gameContext = null;
        // 需要手牌
        ITehai tehai = null;
        // 不需要手牌分割
        DivideInfo divide = null;
        // 结果
        boolean result = false;

        // 小四喜
        tehai = EncodeMark.toTehai("111m11122233344z");
        result = yaku.match(tehai, divide, gameContext);
        assertTrue(result);

        // 大四喜
        tehai = EncodeMark.toTehai("11m111222333444z");
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);

        // 鸣牌小四喜
        tehai = EncodeMark.toTehai("111m11223344z");
        tehai.pon(HaiPool.z(1), false, RivalEnum.BOTTOM);
        tehai.pon(HaiPool.z(2), false, RivalEnum.BOTTOM);
        tehai.pon(HaiPool.z(3), false, RivalEnum.BOTTOM);
        result = yaku.match(tehai, divide, gameContext);
        assertTrue(result);

        // 其他
        tehai = EncodeMark.toTehai("11m112233445566z");
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);
    }
}
