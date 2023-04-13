package com.github.terralian.fastmaj.test.yaku.h13;

import java.util.List;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.agari.ITehaiAgariDivider;
import com.github.terralian.fastmaj.encode.EncodeMark;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.tehai.TehaiBuilder;
import com.github.terralian.fastmaj.third.mjscore.MjscoreAdapter;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.h13.Tyuuren9;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * {@link Tyuuren9}纯九莲
 *
 * @author terra.lian
 */
public class Tyuuren9Test {

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
        IYaku yaku = new Tyuuren9();
        // 不需要场况
        PlayerGameContext gameContext = null;
        // 需要手牌
        ITehai tehai = null;
        // 结果
        boolean result = false;

        // 九莲
        tehai = EncodeMark.toTehai("1112234567999m8m");
        List<DivideInfo> divideInfos = tehaiAgariDivider.divide(tehai);
        assertEquals(1, divideInfos.size());
        result = yaku.match(tehai, divideInfos.get(0), gameContext);
        assertFalse(result);

        // 纯九莲
        tehai = EncodeMark.toTehai("1112345678999m2m");
        divideInfos = tehaiAgariDivider.divide(tehai);
        assertEquals(1, divideInfos.size());
        result = yaku.match(tehai, divideInfos.get(0), gameContext);
        assertTrue(result);

        // 纯九莲，但是鸣牌
        tehai = TehaiBuilder.from("111245678999m") //
                .chi("3m", "24m") //
                .addOne("8m") //
                .get();
        divideInfos = tehaiAgariDivider.divide(tehai);
        assertEquals(1, divideInfos.size());
        result = yaku.match(tehai, divideInfos.get(0), gameContext);
        assertFalse(result);

        // 其他
        tehai = EncodeMark.toTehai("111333555777m22p");
        divideInfos = tehaiAgariDivider.divide(tehai);
        assertEquals(1, divideInfos.size());
        result = yaku.match(tehai, divideInfos.get(0), gameContext);
        assertFalse(result);
    }
}
