package com.github.terralian.fastmaj.test.yaku.h3;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.agari.ITehaiAgariDivider;
import com.github.terralian.fastmaj.encode.EncodeMark;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.third.mjscore.MjscoreAdapter;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.h3.JyunTyanta;

/**
 * {@link JyunTyanta}纯全带测试
 * 
 * @author terra.lian 
 */
public class JyunTyantaTest {

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
        IYaku yaku = new JyunTyanta();
        // 不需要场况信息
        PlayerGameContext gameContext = new PlayerGameContext();
        // 手牌
        ITehai tehai = null;
        // 结果
        boolean result = false;

        // 是纯全带
        tehai = EncodeMark.toTehai("123999m11p111999s");
        List<DivideInfo> divideInfos = tehaiAgariDivider.divide(tehai);
        result = yaku.match(tehai, divideInfos.get(0), gameContext);
        assertTrue(result);

        // 混全带
        tehai = EncodeMark.toTehai("123999m111999s11z");
        divideInfos = tehaiAgariDivider.divide(tehai);
        result = yaku.match(tehai, divideInfos.get(0), gameContext);
        assertFalse(result);

        // 清老头
        tehai = EncodeMark.toTehai("111999m11p111999s");
        divideInfos = tehaiAgariDivider.divide(tehai);
        result = yaku.match(tehai, divideInfos.get(0), gameContext);
        assertFalse(result);

        // 雀头非幺九
        tehai = EncodeMark.toTehai("12379m123p22789s8m");
        divideInfos = tehaiAgariDivider.divide(tehai);
        result = yaku.match(tehai, divideInfos.get(0), gameContext);
        assertFalse(result);
    }
}
