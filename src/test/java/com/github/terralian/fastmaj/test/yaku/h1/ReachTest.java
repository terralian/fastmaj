package com.github.terralian.fastmaj.test.yaku.h1;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.game.context.PlayerGameContextFactory;
import com.github.terralian.fastmaj.hai.HaiPool;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.h1.Reach;

/**
 * {@link Reach}立直测试
 * 
 * @author terra.lian
 */
public class ReachTest {

    @Test
    public void test() {
        IYaku yaku = new Reach();
        // 立直仅需要场况
        PlayerGameContext gameContext = PlayerGameContextFactory.buildByNormal(0);
        // 不需要手牌
        ITehai tehai = null;
        // 不需要手牌分割
        DivideInfo divide = null;
        // 结果
        boolean result = false;
        // 仅自己立直
        gameContext.setPosition(0);
        gameContext.getHaiRiver().chain() //
                .kiri(HaiPool.random()) //
                .reach(HaiPool.random());
        result = yaku.match(tehai, divide, gameContext);
        assertTrue(result);

        // // 没有人立直
        gameContext = PlayerGameContextFactory.buildByNormal(0);
        gameContext.getHaiRiver().chain() //
                .kiri(HaiPool.random()) //
                .kiri(HaiPool.random());
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);

        // 自己和多人立直
        gameContext = PlayerGameContextFactory.buildByNormal(0);
        gameContext.getHaiRiver().chain() //
                .kiri(HaiPool.random()) //
                .reach(HaiPool.random());
        gameContext.getHaiRiver(1).chain() //
                .kiri(HaiPool.random()) //
                .reach(HaiPool.random());
        gameContext.getHaiRiver(2).chain() //
                .kiri(HaiPool.random()) //
                .reach(HaiPool.random());
        result = yaku.match(tehai, divide, gameContext);
        assertTrue(result);

        // 自己没有立直，其他人立直了
        // 自己和多人立直
        gameContext = PlayerGameContextFactory.buildByNormal(0);
        gameContext.getHaiRiver().chain() //
                .kiri(HaiPool.random()) //
                .kiri(HaiPool.random());
        gameContext.getHaiRiver(2).chain() //
                .kiri(HaiPool.random()) //
                .reach(HaiPool.random());
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);

        // 自己的坐席是其他情况
        gameContext = PlayerGameContextFactory.buildByNormal(1);
        gameContext.getHaiRiver().chain() //
                .kiri(HaiPool.random()) //
                .reach(HaiPool.random());
        result = yaku.match(tehai, divide, gameContext);
        assertTrue(result);

        // 自己的坐席是其他情况
        gameContext = PlayerGameContextFactory.buildByNormal(2);
        gameContext.getHaiRiver().chain() //
                .kiri(HaiPool.random()) //
                .reach(HaiPool.random());
        result = yaku.match(tehai, divide, gameContext);
        assertTrue(result);

        // 自己的坐席是其他情况
        gameContext = PlayerGameContextFactory.buildByNormal(3);
        gameContext.getHaiRiver().chain() //
                .kiri(HaiPool.random()) //
                .reach(HaiPool.random());
        result = yaku.match(tehai, divide, gameContext);
        assertTrue(result);
    }

    /**
     * 校验立直中过滤了两立直
     */
    @Test
    public void doubleReachTest() {
        IYaku yaku = new Reach();
        // 立直仅需要场况
        PlayerGameContext gameContext = PlayerGameContextFactory.buildByNormal(0);
        // 不需要手牌
        ITehai tehai = null;
        // 不需要手牌分割
        DivideInfo divide = null;
        // 结果
        boolean result = false;

        // 第一巡两立直无人碰牌
        gameContext.getHaiRiver().reach(HaiPool.random(), true);
        gameContext.getHaiRiver().setSameFirstJun(true);
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);

        // 第一巡有人暗杠，两立直
        gameContext.getHaiRiver().reach(HaiPool.random(), false);
        gameContext.getHaiRiver().setSameFirstJun(false);
        result = yaku.match(tehai, divide, gameContext);
        assertTrue(result);
    }
}
