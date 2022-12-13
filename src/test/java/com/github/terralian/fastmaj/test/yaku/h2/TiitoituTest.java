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
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.third.mjscore.MjscoreAdapter;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.h2.Tiitoitu;

/**
 * {@link Tiitoitu}七对子
 * 
 * @author terra.lian 
 */
public class TiitoituTest {

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
        IYaku yaku = new Tiitoitu();
        // 不需要场况信息
        PlayerGameContext gameContext = null;
        // 手牌
        ITehai tehai = null;
        // 结果
        boolean result = false;

        // 是七对
        tehai = EncodeMark.toTehai("11335577m224466p");
        List<DivideInfo> divideInfos = tehaiAgariDivider.divide(tehai);
        result = yaku.match(tehai, divideInfos.get(0), gameContext);
        assertTrue(result);

        // 两杯口（低目七对不成立）
        ITehai ftehai = EncodeMark.toTehai("11223344556677m");
        divideInfos = tehaiAgariDivider.divide(ftehai);
        result = divideInfos.stream().anyMatch(k -> yaku.match(ftehai, k, gameContext));
        assertFalse(result);
    }
}
