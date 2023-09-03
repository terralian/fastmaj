package com.github.terralian.fastmaj.test.agari;

import java.util.List;
import java.util.function.Consumer;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.agari.FuCalculator;
import com.github.terralian.fastmaj.agari.IFuCalculator;
import com.github.terralian.fastmaj.agari.ITehaiAgariDivider;
import com.github.terralian.fastmaj.encode.EncodeMark;
import com.github.terralian.fastmaj.game.KazeEnum;
import com.github.terralian.fastmaj.game.action.river.RiverActionType;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.player.space.IPlayerSpaceManager;
import com.github.terralian.fastmaj.player.space.PlayerSpaceManager;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.tehai.TehaiBuilder;
import com.github.terralian.fastmaj.third.mjscore.MjscoreAdapter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
        IPlayerSpaceManager spaceManager = new PlayerSpaceManager(4);
        PlayerGameContext context = new PlayerGameContext();
        context.setLastPlayerPosition(1);
        context.setPublicSpaces(spaceManager.clonePublicSpace(1));

        // 当前上下文在模拟荣和设置下，使用的是事件判定比较麻烦
        Consumer<Boolean> ronSetter = (isRon) -> {
            context.getPublicSpace(context.getLastPlayerPosition()).setLastRiverActionType(isRon ? RiverActionType.RON : null);
        };

        // 平和自摸
        ITehai tehai = EncodeMark.toTehai("23567m12223789s1m");
        List<DivideInfo> divideInfos = agariDivider.divide(tehai);
        ronSetter.accept(false);
        int fu = fuCalculator.compute(tehai, tehai.getDrawHai(), divideInfos, context);
        assertEquals(20, fu);

        // 非平和自摸
        tehai = EncodeMark.toTehai("56m33p456678s444z4m");
        divideInfos = agariDivider.divide(tehai);
        ronSetter.accept(false);
        fu = fuCalculator.compute(tehai, tehai.getDrawHai(), divideInfos, context);
        assertEquals(30, fu);

        // 非平和自摸
        // 底符20，幺九暗刻8，边张2，自摸2 -> 32 进位 40符
        tehai = EncodeMark.toTehai("067m44678999p89s7s");
        divideInfos = agariDivider.divide(tehai);
        ronSetter.accept(false);
        fu = fuCalculator.compute(tehai, tehai.getDrawHai(), divideInfos, context);
        assertEquals(40, fu);

        // 单骑特例
        tehai = EncodeMark.toTehai("2223340m444p456s3m");
        divideInfos = agariDivider.divide(tehai);
        ronSetter.accept(false);
        fu = fuCalculator.compute(tehai, tehai.getDrawHai(), divideInfos, context);
        assertEquals(40, fu);

        // 双碰荣和跳符
        tehai = EncodeMark.toTehai("12356788m56799p8m");
        divideInfos = agariDivider.divide(tehai);
        ronSetter.accept(true);
        fu = fuCalculator.compute(tehai, tehai.getDrawHai(), divideInfos, context);
        assertEquals(40, fu);

        // 40符
        tehai = TehaiBuilder.from("234m", "1p") //
                .addChi("9s", "78s") //
                .addChi("6s", "57s") //
                .addMinkan("5z") //
                .addOne("1p") //
                .get();
        divideInfos = agariDivider.divide(tehai);
        ronSetter.accept(false);
        fu = fuCalculator.compute(tehai, tehai.getDrawHai(), divideInfos, context);
        assertEquals(40, fu);

        // 30符
        tehai = TehaiBuilder.from("34m11s06m1144z") //
                .pon("4z") //
                .pon("1z") //
                .chi("4m", "06m") //
                .addOne("5m") //
                .get();
        divideInfos = agariDivider.divide(tehai);
        ronSetter.accept(false);
        fu = fuCalculator.compute(tehai, tehai.getDrawHai(), divideInfos, context);
        assertEquals(30, fu);

        // 双风跳符，40符
        tehai = TehaiBuilder.from(EncodeMark.toTehai("12367m114477z")) //
                .pon("4z") //
                .pon("7z") //
                .addOne("8m") //
                .get();
        divideInfos = agariDivider.divide(tehai);
        ronSetter.accept(true);
        context.setBakaze(KazeEnum.DON);
        context.setJikaze(KazeEnum.DON);
        fu = fuCalculator.compute(tehai, tehai.getDrawHai(), divideInfos, context);
        assertEquals(40, fu);
    }
}
