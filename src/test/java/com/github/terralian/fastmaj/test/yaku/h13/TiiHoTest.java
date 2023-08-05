package com.github.terralian.fastmaj.test.yaku.h13;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.game.context.PlayerGameContextFactory;
import com.github.terralian.fastmaj.hai.HaiPool;
import com.github.terralian.fastmaj.river.HaiRiver;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.tehai.Tehai;
import com.github.terralian.fastmaj.util.CollectionUtil;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.h13.TiiHo;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * {@link TiiHo}地和测试
 *
 * @author terra.lian
 */
public class TiiHoTest {

    @Test
    public void test() {
        IYaku yaku = new TiiHo();
        // 纯场况役
        PlayerGameContext gameContext = PlayerGameContextFactory.buildByNormal(0);
        // 需要手牌
        ITehai tehai = new Tehai();
        // 不需要手牌分割
        DivideInfo divide = null;
        // 结果
        boolean result = false;

        // 自己是庄，没人打过牌
        gameContext.setPosition(0);
        gameContext.setOya(0);
        gameContext.setHaiRivers(CollectionUtil.newArrayList(new HaiRiver(0), new HaiRiver(1), new HaiRiver(2)));
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);

        // 自己不是庄，且没人鸣过牌
        gameContext.setOya(1);
        result = yaku.match(tehai, divide, gameContext);
        assertTrue(result);

        // 荣和
        gameContext.setRon(true);
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);

        // 自己不是庄，但是有人鸣过牌了
        gameContext.getHaiRivers().forEach(k -> k.setSameFirstJun(false));
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);

        // 自己不是庄，没有鸣过牌，但是第二轮了
        gameContext.getHaiRivers().forEach(k -> k.setSameFirstJun(true));
        gameContext.getHaiRiver().kiri(HaiPool.m(1));
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);
    }
}
