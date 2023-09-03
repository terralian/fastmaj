package com.github.terralian.fastmaj.test.yaku.h13;

import java.util.List;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.agari.ITehaiAgariDivider;
import com.github.terralian.fastmaj.encode.EncodeMark;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.hai.HaiPool;
import com.github.terralian.fastmaj.player.RivalEnum;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.tehai.TehaiBuilder;
import com.github.terralian.fastmaj.third.mjscore.MjscoreAdapter;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.h13.SuuankouTanki;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * {@link SuuankouTanki} 四暗刻单骑测试
 *
 * @author terra.lian
 */
public class SuuankouTankiTest {

    /**
     * 和了时，手牌分割器
     */
    private ITehaiAgariDivider tehaiAgariDivider;
    private IYaku yaku;

    @Before
    public void prepare() {
        // 使用mjscore进行手牌分割
        tehaiAgariDivider = new MjscoreAdapter();
        yaku = new SuuankouTanki();
    }

    @Test
    public void test() {
        // 需要荣和信息
        PlayerGameContext gameContext = new PlayerGameContext();
        // 需要手牌
        ITehai tehai = null;
        // 结果
        boolean result = false;

        // 未鸣牌的自摸四暗刻
        tehai = EncodeMark.toTehai("11133355577m22p7m");
        List<DivideInfo> divideInfos = tehaiAgariDivider.divide(tehai);
        result = yaku.match(tehai, divideInfos.get(0), gameContext);
        assertFalse(result);

        // 四暗刻单骑
        tehai = EncodeMark.toTehai("111333555777m22p");
        divideInfos = tehaiAgariDivider.divide(tehai);
        result = yaku.match(tehai, divideInfos.get(0), gameContext);
        assertTrue(result);

        // 三暗杠对对和
        tehai = EncodeMark.toTehai("11133555777m22p");
        tehai.pon(HaiPool.m(3), false, RivalEnum.BOTTOM);
        divideInfos = tehaiAgariDivider.divide(tehai);
        result = yaku.match(tehai, divideInfos.get(0), gameContext);
        assertFalse(result);

        // 四暗刻，三杠子
        tehai = EncodeMark.toTehai("11133355577m22p7m");
        tehai.draw(HaiPool.m(1));
        tehai.annkan(HaiPool.m(1));
        tehai.draw(HaiPool.m(3));
        tehai.annkan(HaiPool.m(3));
        tehai.draw(HaiPool.m(5));
        tehai.annkan(HaiPool.m(5));
        divideInfos = tehaiAgariDivider.divide(tehai);
        result = yaku.match(tehai, divideInfos.get(0), gameContext);
        assertFalse(result);

        // 四暗刻，混老头
        tehai = EncodeMark.toTehai("111999m111p99s11z9s");
        divideInfos = tehaiAgariDivider.divide(tehai);
        result = yaku.match(tehai, divideInfos.get(0), gameContext);
        assertFalse(result);
    }

    /**
     * 未鸣牌的单暗杠的四暗刻单骑
     */
    @Test
    public void test_one_annkan_tanki() {
        PlayerGameContext gameContext = new PlayerGameContext();
        ITehai tehai = TehaiBuilder.from("777m1113s222z")
                .addAnnkan("8p")
                .addOne("3s")
                .get();
        gameContext.setEndByRon(true);
        gameContext.setAgariHai(HaiPool.s(3));
        List<DivideInfo> divideInfos = tehaiAgariDivider.divide(tehai);
        boolean res = yaku.match(tehai, divideInfos.get(0), gameContext);
        Assert.assertTrue(res);
    }
}
