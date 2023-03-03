package com.github.terralian.fastmaj.test.yaku.h1;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.h1.Haitei;
import com.github.terralian.fastmaj.yama.DrawFrom;

/**
 * 海底摸月测试
 * 
 * @author terra.lian
 */
public class HaiteiTest {

    @Test
    public void test() {
        IYaku yaku = new Haitei();
        // 不需要手牌分割
        DivideInfo divide = null;
        // 需要场况信息
        PlayerGameContext gameContext = new PlayerGameContext();
        // 手牌，和手牌无关的役
        ITehai tehai = null;
        // 结果
        boolean result = false;
        // 从牌山自摸
        gameContext.setLastDrawFrom(DrawFrom.YAMA);

        // 最后一枚，非荣和
        gameContext.setRon(false);
        gameContext.setYamaCountdown(0);
        result = yaku.match(tehai, divide, gameContext);
        assertTrue(result);

        // 最后一枚，荣和
        gameContext.setRon(true);
        gameContext.setYamaCountdown(0);
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);

        // 非最后一枚，非荣和
        gameContext.setRon(false);
        gameContext.setYamaCountdown(1);
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);

        // 非最后一枚，荣和
        gameContext.setRon(true);
        gameContext.setYamaCountdown(1);
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);
    }

    @Test
    public void testKan() {
        IYaku yaku = new Haitei();
        // 不需要手牌分割
        DivideInfo divide = null;
        // 需要场况信息
        PlayerGameContext gameContext = new PlayerGameContext();
        // 手牌，和手牌无关的役
        ITehai tehai = null;
        // 结果
        boolean result = false;
        // 从牌山自摸
        gameContext.setLastDrawFrom(DrawFrom.RINSYIN);

        // 最后一枚，非荣和
        gameContext.setRon(false);
        gameContext.setYamaCountdown(0);
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);

        // 最后一枚，荣和
        gameContext.setRon(true);
        gameContext.setYamaCountdown(0);
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);
    }
}
