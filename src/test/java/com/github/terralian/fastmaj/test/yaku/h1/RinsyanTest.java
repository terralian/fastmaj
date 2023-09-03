package com.github.terralian.fastmaj.test.yaku.h1;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.game.action.tehai.TehaiActionType;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.h1.Rinsyan;

/**
 * {@link Rinsyan} 岭上开花测试
 * 
 * @author terra.lian
 */
public class RinsyanTest {

    @Test
    public void test() {
        IYaku yaku = new Rinsyan();
        // 仅需要场况
        PlayerGameContext gameContext = new PlayerGameContext();
        // 不需要手牌
        ITehai tehai = null;
        // 不需要手牌分割
        DivideInfo divide = null;
        // 结果
        boolean result = false;

        // 非荣和，暗杠岭上
        gameContext.setEndByRon(false);
        gameContext.setLastTehaiActionType(TehaiActionType.ANNKAN);
        result = yaku.match(tehai, divide, gameContext);
        assertTrue(result);

        // 荣和，暗杠岭上（被人抢杠）
        gameContext.setEndByRon(true);
        gameContext.setLastTehaiActionType(TehaiActionType.ANNKAN);
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);

        // 非荣和，加杠岭上
        gameContext.setEndByRon(false);
        gameContext.setLastTehaiActionType(TehaiActionType.KAKAN);
        result = yaku.match(tehai, divide, gameContext);
        assertTrue(result);

        // 荣和，加杠岭上
        gameContext.setEndByRon(true);
        gameContext.setLastTehaiActionType(TehaiActionType.KAKAN);
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);

        // 非荣和，其他操作和了
        gameContext.setEndByRon(false);
        gameContext.setLastTehaiActionType(TehaiActionType.KIRI);
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);

        // 无最后玩家的操作信息，非岭上
        gameContext.setEndByRon(false);
        gameContext.setLastTehaiActionType(null);
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);
    }
}
