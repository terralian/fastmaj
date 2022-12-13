package com.github.terralian.fastmaj.test.yaku.h2;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.agari.ITehaiAgariDivider;
import com.github.terralian.fastmaj.encode.EncodeMark;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.hai.HaiPool;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.third.mjscore.MjscoreAdapter;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.h2.Ittuu;

/**
 * {@link Ittuu} 一气
 * 
 * @author terra.lian 
 */
public class IttuuTest {

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
        IYaku yaku = new Ittuu();
        // 不需要场况信息
        PlayerGameContext gameContext = null;
        // 手牌
        ITehai tehai = null;
        // 结果
        boolean result = false;

        // 一气
        tehai = EncodeMark.toTehai("123456789m123p11z");
        List<DivideInfo> divideInfos = tehaiAgariDivider.divide(tehai);
        result = yaku.match(tehai, divideInfos.get(0), gameContext);
        assertTrue(result);

        // 一气，且鸣牌了
        tehai = EncodeMark.toTehai("12356789m123p11z");
        tehai.chii(HaiPool.m(4), HaiPool.m(5), HaiPool.m(6));
        divideInfos = tehaiAgariDivider.divide(tehai);
        result = yaku.match(tehai, divideInfos.get(0), gameContext);
        assertTrue(result);

        // 一气的搭子，但是并不是一气
        tehai = EncodeMark.toTehai("123345678999m11z");
        divideInfos = tehaiAgariDivider.divide(tehai);
        result = yaku.match(tehai, divideInfos.get(0), gameContext);
        assertFalse(result);
    }
}
