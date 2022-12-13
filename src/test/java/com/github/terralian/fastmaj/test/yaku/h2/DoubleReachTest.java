package com.github.terralian.fastmaj.test.yaku.h2;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.hai.HaiPool;
import com.github.terralian.fastmaj.river.HaiRiver;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.util.CollectionUtil;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.h2.DoubleReach;

/**
 * {@link DoubleReach}两立直测试
 * 
 * @author terra.lian
 */
public class DoubleReachTest {

    @Test
    public void test() {
        IYaku yaku = new DoubleReach();
        // 纯场况役
        PlayerGameContext gameContext = new PlayerGameContext();
        // 不需要手牌
        ITehai tehai = null;
        // 不需要手牌分割
        DivideInfo divide = null;
        // 结果
        boolean result = false;

        // 第一巡立直
        gameContext.setPosition(0);
        gameContext.setHaiRivers(CollectionUtil.newArrayList(new HaiRiver(0), new HaiRiver(1), new HaiRiver(2)));
        gameContext.getHaiRivers().forEach(k -> k.setSameFirstJun(true));
        gameContext.getHaiRiver().reach(HaiPool.m(1), true);
        result = yaku.match(tehai, divide, gameContext);
        assertTrue(result);

        // 庄家暗杠
        gameContext.setPosition(1);
        gameContext.setHaiRivers(CollectionUtil.newArrayList(new HaiRiver(0), new HaiRiver(1), new HaiRiver(2)));
        gameContext.getHaiRivers().forEach(k -> k.setSameFirstJun(false));
        gameContext.getHaiRivers().get(1).reach(HaiPool.m(1));
        gameContext.getHaiRivers().get(1).reach(HaiPool.m(1));
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);

        // 第二巡才立直
        gameContext.setPosition(1);
        gameContext.setHaiRivers(CollectionUtil.newArrayList(new HaiRiver(0), new HaiRiver(1), new HaiRiver(2)));
        gameContext.getHaiRivers().forEach(k -> k.setSameFirstJun(true));
        gameContext.getHaiRivers().get(1).chain() //
                .kiri(HaiPool.m(1)) //
                .reach(HaiPool.m(1));
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);
    }
}
