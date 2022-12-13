package com.github.terralian.fastmaj.test.agari;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.agari.FuCalculator;
import com.github.terralian.fastmaj.agari.IFuCalculator;
import com.github.terralian.fastmaj.agari.ITehaiAgariDivider;
import com.github.terralian.fastmaj.encode.EncodeMark;
import com.github.terralian.fastmaj.game.KazeEnum;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.hai.HaiPool;
import com.github.terralian.fastmaj.player.RivalEnum;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.third.mjscore.MjscoreAdapter;

/**
 * {@link FuCalculator}单元测试
 * 
 * @author terra.lian
 */
public class FuCalculatorTest {

    private IFuCalculator fuCalculator;
    private ITehaiAgariDivider agariDivider;

    @Before
    public void before() {
        fuCalculator = new FuCalculator();
        agariDivider = new MjscoreAdapter();
    }

    @Test
    public void test() {
        PlayerGameContext context = new PlayerGameContext();

        // 平和自摸
        ITehai tehai = EncodeMark.toTehai("23567m12223789s1m");
        List<DivideInfo> divideInfos = agariDivider.divide(tehai);
        context.setRon(false);
        int fu = fuCalculator.compute(tehai, tehai.getDrawHai(), divideInfos, context);
        assertEquals(20, fu);

        // 非平和自摸
        tehai = EncodeMark.toTehai("56m33p456678s444z4m");
        divideInfos = agariDivider.divide(tehai);
        context.setRon(false);
        fu = fuCalculator.compute(tehai, tehai.getDrawHai(), divideInfos, context);
        assertEquals(30, fu);

        // 非平和自摸
        // 底符20，幺九暗刻8，边张2，自摸2 -> 32 进位 40符
        tehai = EncodeMark.toTehai("067m44678999p89s7s");
        divideInfos = agariDivider.divide(tehai);
        context.setRon(false);
        fu = fuCalculator.compute(tehai, tehai.getDrawHai(), divideInfos, context);
        assertEquals(40, fu);

        // 单骑特例
        tehai = EncodeMark.toTehai("2223340m444p456s3m");
        divideInfos = agariDivider.divide(tehai);
        context.setRon(false);
        fu = fuCalculator.compute(tehai, tehai.getDrawHai(), divideInfos, context);
        assertEquals(40, fu);

        // 双碰荣和跳符
        tehai = EncodeMark.toTehai("12356788m56799p8m");
        divideInfos = agariDivider.divide(tehai);
        context.setRon(true);
        fu = fuCalculator.compute(tehai, tehai.getDrawHai(), divideInfos, context);
        assertEquals(40, fu);

        // 40符
        tehai = EncodeMark.toTehai("234m1p555z57s78p");
        tehai.chii(HaiPool.p(9), HaiPool.p(7), HaiPool.p(8));
        tehai.chii(HaiPool.s(6), HaiPool.s(5), HaiPool.s(7));
        tehai.minkan(HaiPool.z(5), RivalEnum.BOTTOM);
        tehai.draw(HaiPool.p(1));
        divideInfos = agariDivider.divide(tehai);
        context.setRon(false);
        fu = fuCalculator.compute(tehai, tehai.getDrawHai(), divideInfos, context);
        assertEquals(40, fu);

        // 30符
        tehai = EncodeMark.toTehai("34m11s06m1144z");
        tehai.pon(HaiPool.z(4), RivalEnum.TOP);
        tehai.pon(HaiPool.z(1), RivalEnum.TOP);
        tehai.chii(HaiPool.m(4), HaiPool.m(0), HaiPool.m(6));
        tehai.draw(HaiPool.m(5));
        divideInfos = agariDivider.divide(tehai);
        context.setRon(false);
        fu = fuCalculator.compute(tehai, tehai.getDrawHai(), divideInfos, context);
        assertEquals(30, fu);

        // 双风跳符，40符
        tehai = EncodeMark.toTehai("12367m114477z");
        tehai.pon(HaiPool.z(4), RivalEnum.TOP);
        tehai.pon(HaiPool.z(7), RivalEnum.TOP);
        tehai.draw(HaiPool.m(8));
        divideInfos = agariDivider.divide(tehai);
        context.setRon(true);
        context.setBakaze(KazeEnum.DON);
        context.setJikaze(KazeEnum.DON);
        fu = fuCalculator.compute(tehai, tehai.getDrawHai(), divideInfos, context);
        assertEquals(40, fu);
    }
}
