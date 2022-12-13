package com.github.terralian.fastmaj.test.yaku.h1;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.game.context.PlayerGameContextFactory;
import com.github.terralian.fastmaj.hai.HaiPool;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.tehai.NakiEnum;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.h1.Iipatu;

/**
 * {@link Iipatu}测试
 * 
 * @author terra.lian
 */
public class IipatuTest {

    @Test
    public void test() {
        IYaku yaku = new Iipatu();
        // 不需要手牌分割
        DivideInfo divide = null;
        // 需要场况信息
        PlayerGameContext gameContext = PlayerGameContextFactory.buildByNormal(0);
        // 手牌，和手牌无关的役
        ITehai tehai = null;
        // 结果
        boolean result = false;

        // 自己是0
        gameContext.setPosition(0);
        // 仅自己立直，没有人吃碰杠
        gameContext.getHaiRiver().chain() //
                .kiri(HaiPool.random()) //
                .reach(HaiPool.random());
        result = yaku.match(tehai, divide, gameContext);
        assertTrue(result);
        // 仅自己立直，有人吃碰杠
        gameContext.getHaiRiver().chain() //
                .clear() //
                .kiri(HaiPool.random()) //
                .reach(HaiPool.random()) //
                .naki(2, NakiEnum.PON) //
                .setSameJun(false);
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);

        // 没有人立直，没有人吃碰杠
        gameContext.getHaiRivers().forEach(k -> k.clear());
        gameContext.getHaiRivers().forEach(k -> k.kiri(HaiPool.random()));
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);

        // 没有人立直，有人吃碰杠
        gameContext.getHaiRivers().forEach(k -> k.clear());
        gameContext.getHaiRivers().forEach(k -> k.chain().kiri(HaiPool.random()).setSameJun(false));
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);

        // 多人立直，没有人吃碰杠
        gameContext.getHaiRivers().forEach(k -> k.clear());
        gameContext.getHaiRivers().forEach(k -> k.chain().reach(HaiPool.random()));
        result = yaku.match(tehai, divide, gameContext);
        assertTrue(result);

        // 自己立直，下家吃破一发，对家立直，上家没有鸣牌
        gameContext.getHaiRivers().forEach(k -> k.clear());
        gameContext.getHaiRiver(0).chain()//
                .reach(HaiPool.random()) //
                .naki(1, NakiEnum.CHI) //
                .setSameJun(false);
        gameContext.getHaiRiver(1).kiri(HaiPool.random());
        gameContext.getHaiRiver(2).reach(HaiPool.random());
        gameContext.getHaiRiver(3).kiri(HaiPool.random());
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);

        // 除自己外其他人都立直，没有人鸣牌
        gameContext.getHaiRivers().forEach(k -> k.clear());
        gameContext.getHaiRiver(0).kiri(HaiPool.random());
        gameContext.getHaiRiver(1).reach(HaiPool.random());
        gameContext.getHaiRiver(2).reach(HaiPool.random());
        gameContext.getHaiRiver(3).reach(HaiPool.random());
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);
    }
}
