package com.github.terralian.fastmaj.test.yaku.h1;

import java.util.List;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.agari.ITehaiAgariDivider;
import com.github.terralian.fastmaj.encode.EncodeMark;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.tehai.TehaiBuilder;
import com.github.terralian.fastmaj.third.mjscore.MjscoreAdapter;
import com.github.terralian.fastmaj.util.CollectionUtil;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.h1.Iipeikou;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * {@link Iipeikou} 一杯口测试
 *
 * @author terra.lian
 */
public class IipeikouTest {

    /**
     * 和了时，手牌分割器
     */
    private ITehaiAgariDivider tehaiAgariDivider;

    @Before
    public void prepare() {
        // 使用mjscore进行手牌分割
        tehaiAgariDivider = new MjscoreAdapter();
    }

    @Test
    public void test() {
        IYaku yaku = new Iipeikou();
        // 不需要场况
        PlayerGameContext gameContext = null;
        // 手牌，需要手牌
        ITehai tehai = null;
        // 结果
        boolean result = false;

        // 未鸣牌，是一杯口
        tehai = EncodeMark.toTehai("112233m789p456s11z");
        List<DivideInfo> divideInfos = tehaiAgariDivider.divide(tehai);
        assertEquals(1, divideInfos.size());
        DivideInfo divide = divideInfos.get(0);
        result = yaku.match(tehai, divide, gameContext);
        assertTrue(result);

        // 一杯口，但是鸣牌了
        tehai = TehaiBuilder.from("112233m789p45s11z") //
                .chi("6s", "45s") //
                .get();
        divideInfos = tehaiAgariDivider.divide(tehai);
        assertEquals(1, divideInfos.size());
        divide = divideInfos.get(0);
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);

        // 通常牌，未鸣牌
        tehai = EncodeMark.toTehai("123456m678p456s11z");
        divideInfos = tehaiAgariDivider.divide(tehai);
        assertEquals(1, divideInfos.size());
        divide = divideInfos.get(0);
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);

        // 一杯口
        tehai = EncodeMark.toTehai("112223334m456p11z");
        divideInfos = tehaiAgariDivider.divide(tehai);
        assertEquals(1, divideInfos.size());
        divide = divideInfos.get(0);
        result = yaku.match(tehai, divide, gameContext);
        assertTrue(result);

        // 像一杯口，但是七对子
        tehai = EncodeMark.toTehai("112233m55p6677s11z");
        divideInfos = tehaiAgariDivider.divide(tehai);
        assertEquals(1, divideInfos.size());
        divide = divideInfos.get(0);
        result = yaku.match(tehai, divide, gameContext);
        assertFalse(result);

        // 多种分割结果，其中包含一杯口
        ITehai finalTehai = EncodeMark.toTehai("11122233344455m");
        divideInfos = tehaiAgariDivider.divide(finalTehai);
        assertTrue(divideInfos.size() > 1);
        divide = divideInfos.get(0);
        result = CollectionUtil.anyMatch(divideInfos, k -> yaku.match(finalTehai, k, gameContext));
        assertTrue(result);

        // 还没和牌
        tehai = EncodeMark.toTehai("27m067p12s112447z");
        result = yaku.match(tehai, null, gameContext);
        assertFalse(result);
    }
}
