package com.github.terralian.fastmaj.test.yaku.h1;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.encode.EncodeMark;
import com.github.terralian.fastmaj.game.KazeEnum;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.h1.Bakaze;

/**
 * 场风测试
 * 
 * @author terra.lian 
 */
public class BakazeTest {

    @Test
    public void test() {
        IYaku yaku = new Bakaze();
        // 不需要手牌分割
        DivideInfo divide = null;
        // 仅需要场风信息
        PlayerGameContext gameContext = new PlayerGameContext();
        // 手牌
        ITehai tehai = null;
        // 结果
        boolean result = false;

        gameContext.setBakaze(KazeEnum.DON);
        // 三个东，含场风
        tehai = EncodeMark.toTehai("111m111s111p11122z");
        result = yaku.match(tehai, divide, gameContext);
        assertTrue(result);

        // 2个东
        tehai = EncodeMark.toTehai("111m111s111p11222z");
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);

        // 没有东
        tehai = EncodeMark.toTehai("111m111s111p22233z");
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);

        // 南
        gameContext.setBakaze(KazeEnum.NAN);
        tehai = EncodeMark.toTehai("111m111s111p11222z");
        result = yaku.match(tehai, divide, gameContext);
        assertTrue(result);

        // 西
        gameContext.setBakaze(KazeEnum.SYA);
        tehai = EncodeMark.toTehai("111m111s111p22333z");
        result = yaku.match(tehai, divide, gameContext);
        assertTrue(result);

        // 北
        gameContext.setBakaze(KazeEnum.PEI);
        tehai = EncodeMark.toTehai("111m111s111p33444z");
        result = yaku.match(tehai, divide, gameContext);
        assertTrue(result);
    }
}
