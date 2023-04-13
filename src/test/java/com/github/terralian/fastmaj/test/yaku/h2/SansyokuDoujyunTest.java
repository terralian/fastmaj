package com.github.terralian.fastmaj.test.yaku.h2;

import java.util.List;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.agari.ITehaiAgariDivider;
import com.github.terralian.fastmaj.encode.EncodeMark;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.tehai.TehaiBuilder;
import com.github.terralian.fastmaj.third.mjscore.MjscoreAdapter;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.h2.SansyokuDoujyun;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * {@link SansyokuDoujyun} 三色同顺
 *
 * @author terra.lian
 */
public class SansyokuDoujyunTest {

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
        IYaku yaku = new SansyokuDoujyun();
        // 不需要场况信息
        PlayerGameContext gameContext = null;
        // 手牌
        ITehai tehai = null;
        // 结果
        boolean result = false;

        // 是三色
        tehai = EncodeMark.toTehai("123m123p123456s11z");
        List<DivideInfo> divideInfos = tehaiAgariDivider.divide(tehai);
        result = yaku.match(tehai, divideInfos.get(0), gameContext);
        assertTrue(result);

        // 三色，鸣牌
        tehai = TehaiBuilder.from("123m12p123456s11z") //
                .chi("3p", "12p") //
                .get();
        divideInfos = tehaiAgariDivider.divide(tehai);
        result = yaku.match(tehai, divideInfos.get(0), gameContext);
        assertTrue(result);

        // 不是三色
        tehai = EncodeMark.toTehai("234m123p123456s11z");
        divideInfos = tehaiAgariDivider.divide(tehai);
        result = yaku.match(tehai, divideInfos.get(0), gameContext);
        assertFalse(result);

        // 错误案例
        tehai = EncodeMark.toTehai("667788p2267999s8s");
        divideInfos = tehaiAgariDivider.divide(tehai);
        result = yaku.match(tehai, divideInfos.get(0), gameContext);
        assertFalse(result);
    }
}
