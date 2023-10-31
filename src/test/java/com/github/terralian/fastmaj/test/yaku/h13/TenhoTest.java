package com.github.terralian.fastmaj.test.yaku.h13;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.encode.EncodeMark;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.hai.HaiPool;
import com.github.terralian.fastmaj.river.HaiRiver;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.tehai.Tehai;
import com.github.terralian.fastmaj.util.CollectionHelper;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.h13.Tenho;

/**
 * {@link Tenho}天和测试
 * 
 * @author terra.lian 
 */
public class TenhoTest {

    @Test
    public void test() {
        IYaku yaku = new Tenho();
        // 纯场况役
        PlayerGameContext gameContext = new PlayerGameContext();
        // 需要手牌
        ITehai tehai = new Tehai();
        // 不需要手牌分割
        DivideInfo divide = null;
        // 结果
        boolean result = false;

        // 自己是庄，没人打过牌
        gameContext.setPosition(0);
        gameContext.setOya(0);
        gameContext.setHaiRivers(CollectionHelper.newArrayList(new HaiRiver(0), new HaiRiver(1), new HaiRiver(2)));
        result = yaku.match(tehai, divide, gameContext);
        assertTrue(result);

        // 自己不是庄
        gameContext.setOya(1);
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);

        // 自己是庄，但是开暗杠
        tehai = EncodeMark.toTehai("1111m123456789s11z");
        tehai.annkan(HaiPool.m(1));
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);
    }
}
