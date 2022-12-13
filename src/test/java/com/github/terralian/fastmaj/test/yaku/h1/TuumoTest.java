package com.github.terralian.fastmaj.test.yaku.h1;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.hai.HaiPool;
import com.github.terralian.fastmaj.player.RivalEnum;
import com.github.terralian.fastmaj.tehai.ITehaiLock;
import com.github.terralian.fastmaj.tehai.Tehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.h1.Tuumo;

/**
 * {@link Tuumo}自摸测试
 * 
 * @author terra.lian 
 */
public class TuumoTest {

    @Test
    public void test() {
        IYaku yaku = new Tuumo();
        // 需要场况
        PlayerGameContext gameContext = new PlayerGameContext();
        // 需要手牌，但是仅需要是否鸣牌信息，空手牌
        Tehai tehai = new Tehai();
        // 不需要手牌分割
        DivideInfo divide = null;
        // 结果
        boolean result = false;
        // 手牌固定区
        ITehaiLock lock = tehai.getLock();

        // 非荣和，非鸣牌
        gameContext.setRon(false);
        result = yaku.match(tehai, divide, gameContext);
        assertTrue(result);

        // 荣和，非鸣牌
        gameContext.setRon(true);

        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);

        // 非荣和，鸣牌
        gameContext.setRon(false);
        lock.pon(HaiPool.m(1), RivalEnum.BOTTOM);
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);

        // 荣和，鸣牌
        gameContext.setRon(true);
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);
    }
}
