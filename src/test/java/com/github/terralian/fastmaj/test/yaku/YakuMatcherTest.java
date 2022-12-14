package com.github.terralian.fastmaj.test.yaku;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.agari.ITehaiAgariDivider;
import com.github.terralian.fastmaj.encode.EncodeMark;
import com.github.terralian.fastmaj.game.KazeEnum;
import com.github.terralian.fastmaj.game.action.tehai.TehaiActionType;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.game.context.PlayerGameContextFactory;
import com.github.terralian.fastmaj.hai.HaiPool;
import com.github.terralian.fastmaj.player.RivalEnum;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.third.mjscore.MjscoreAdapter;
import com.github.terralian.fastmaj.util.CollectionUtil;
import com.github.terralian.fastmaj.util.EmptyUtil;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.IYakuMatcher;
import com.github.terralian.fastmaj.yaku.YakuMatcher;
import com.github.terralian.fastmaj.yaku.h1.Bakaze;
import com.github.terralian.fastmaj.yaku.h1.Haitei;
import com.github.terralian.fastmaj.yaku.h1.Haku;
import com.github.terralian.fastmaj.yaku.h1.Hatu;
import com.github.terralian.fastmaj.yaku.h1.Houtei;
import com.github.terralian.fastmaj.yaku.h1.Iipatu;
import com.github.terralian.fastmaj.yaku.h1.Iipeikou;
import com.github.terralian.fastmaj.yaku.h1.Jikaze;
import com.github.terralian.fastmaj.yaku.h1.Pinfu;
import com.github.terralian.fastmaj.yaku.h1.Reach;
import com.github.terralian.fastmaj.yaku.h1.Rinsyan;
import com.github.terralian.fastmaj.yaku.h1.Tanyaotyu;
import com.github.terralian.fastmaj.yaku.h1.Tuumo;
import com.github.terralian.fastmaj.yaku.h1.TyanKan;
import com.github.terralian.fastmaj.yaku.h1.Tyun;
import com.github.terralian.fastmaj.yaku.h13.Daisangen;
import com.github.terralian.fastmaj.yaku.h13.Daisuusii;
import com.github.terralian.fastmaj.yaku.h13.Kokusi;
import com.github.terralian.fastmaj.yaku.h13.Kokusi13;
import com.github.terralian.fastmaj.yaku.h13.Ryuuiisou;
import com.github.terralian.fastmaj.yaku.h13.SuuankouTanki;
import com.github.terralian.fastmaj.yaku.h13.Syousuusii;
import com.github.terralian.fastmaj.yaku.h13.Tinroutou;
import com.github.terralian.fastmaj.yaku.h13.Tuuiisou;
import com.github.terralian.fastmaj.yaku.h13.Tyuuren;
import com.github.terralian.fastmaj.yaku.h13.Tyuuren9;
import com.github.terralian.fastmaj.yaku.h2.DoubleReach;
import com.github.terralian.fastmaj.yaku.h2.HonTyanta;
import com.github.terralian.fastmaj.yaku.h2.Ittuu;
import com.github.terralian.fastmaj.yaku.h2.Sanankou;
import com.github.terralian.fastmaj.yaku.h2.Sankantu;
import com.github.terralian.fastmaj.yaku.h2.SansyokuDoujyun;
import com.github.terralian.fastmaj.yaku.h2.Tiitoitu;
import com.github.terralian.fastmaj.yaku.h2.Toitoi;
import com.github.terralian.fastmaj.yaku.h3.Honitu;
import com.github.terralian.fastmaj.yaku.h3.JyunTyanta;
import com.github.terralian.fastmaj.yaku.h3.Ryanpeikou;
import com.github.terralian.fastmaj.yaku.h6.Tinitu;

/**
 * {@link YakuMatcher}??????
 * <p>
 * ???????????????
 * <ul>
 * <li>?????????????????????
 * <li>??????????????????
 * <li>??????????????????
 * <li>??????????????????
 * </ul>
 * 
 * @author terra.lian 
 */
public class YakuMatcherTest {

    private IYakuMatcher yakuMatcher;
    private ITehaiAgariDivider tehaiAgariDivider;

    @Before
    public void before() {
        yakuMatcher = new YakuMatcher();
        tehaiAgariDivider = new MjscoreAdapter();
    }

    /**
     * ?????????????????????????????????
     */
    @Test
    public void testWithNonContext() {
        // h1
        // ???
        singleYakuTest("123m456p11789s555z", Haku.class);
        // ???
        singleYakuTest("456m456p789s66677z", Hatu.class);
        // ?????????
        singleYakuTest("112233m234789s77z", Iipeikou.class);
        // ??????
        singleYakuTest("123m456789p22789s", Pinfu.class);
        // ??????
        singleYakuTest("234777m345678p44s", Tanyaotyu.class);
        // ???
        singleYakuTest("123m456p11789s777z", Tyun.class);

        // h13
        // ?????????
        singleYakuTest("11m234p555666777z", Daisangen.class);
        // ?????????
        singleYakuTest("11122233444z22s3z", Daisuusii.class);
        // ????????????
        singleYakuTest("19m1p19s12345677z9p", Kokusi.class);
        // ????????????13???
        singleYakuTest("19m19p19s12345677z", Kokusi13.class);
        // ?????????
        singleYakuTest("22334444666888s", Ryuuiisou.class);
        // ????????????????????????????????????????????????
        // ???????????????
        singleYakuTest("111333555m88899s", SuuankouTanki.class);
        // ?????????
        singleYakuTest("1112233444z234s3z", Syousuusii.class);
        // ?????????
        singleYakuTest("111999m11199p11s9p", Tinroutou.class);
        // ?????????
        singleYakuTest("11223344556677z", Tuuiisou.class);
        // ??????
        singleYakuTest("1112346789999m5m", Tyuuren.class);
        // ?????????
        singleYakuTest("1112345678999m9m", Tyuuren9.class);

        // h2
        // ??????
        singleYakuTest("123789m11p111222z", HonTyanta.class);
        // ?????????
        singleYakuTest("444999m111p123s22z", Sanankou.class);
        // ???????????????????????????????????????
        // ????????????
        singleYakuTest("234m234p23499s111z", SansyokuDoujyun.class);
        // ?????????????????????????????????????????????
        // ??????????????????????????????????????????
        // ?????????
        singleYakuTest("1133557799m2244p", Tiitoitu.class);

        // h3
        // ?????????
        singleYakuTest("123456788m333444z", Honitu.class);
        // ??????
        singleYakuTest("12378999m111999p", JyunTyanta.class);
        // ?????????
        singleYakuTest("112233556677m11p", Ryanpeikou.class);

        // ?????????
        singleYakuTest("12334455688999m", Tinitu.class);

        // ??????
        zeroYakuTest("123m456p789s33344z");
    }

    /**
     * ????????????
     * 
     * @param tehaiMark ???????????????
     * @param yakuClass ?????????
     */
    private void singleYakuTest(String tehaiMark, Class<?> yakuClass) {
        ITehai tehai = EncodeMark.toTehai(tehaiMark);
        List<DivideInfo> divideInfos = tehaiAgariDivider.divide(tehai);
        List<IYaku> result = yakuMatcher.match(tehai, CollectionUtil.getFirstElement(divideInfos), null);
        assertEquals(1, result.size());
        assertTrue(result.get(0).getClass() == yakuClass);
    }

    /**
     * ????????????
     * 
     * @param tehaiMark ???????????????
     */
    private void zeroYakuTest(String tehaiMark) {
        ITehai tehai = EncodeMark.toTehai(tehaiMark);
        List<DivideInfo> divideInfos = tehaiAgariDivider.divide(tehai);
        List<IYaku> result = yakuMatcher.match(tehai, CollectionUtil.getFirstElement(divideInfos), null);
        assertEquals(0, result.size());
    }

    // ----------------------------------------

    /**
     * ??????????????????
     */
    @Test
    public void testWithContext() {
        PlayerGameContext gameContext = PlayerGameContextFactory.buildByNormal(1);
        gameContext.setRon(true);
        // ??????????????????????????????????????????
        gameContext.getHaiRivers().forEach(k -> k.setSameFirstJun(false));

        // ??????
        gameContext.setBakaze(KazeEnum.DON);
        ITehai tehai = EncodeMark.toTehai("123456m567p11122z");
        List<IYaku> yakus = yakuMatcher.match(tehai, CollectionUtil.newArrayList(), gameContext);
        assertEquals(1, yakus.size());
        assertTrue(yakus.get(0).getClass() == Bakaze.class);

        // ??????
        gameContext.setBakaze(KazeEnum.DON);
        gameContext.setJikaze(KazeEnum.NAN);
        tehai = EncodeMark.toTehai("123456m567p11222z");
        yakus = yakuMatcher.match(tehai, CollectionUtil.newArrayList(), gameContext);
        assertEquals(1, yakus.size());
        assertTrue(yakus.get(0).getClass() == Jikaze.class);

        // ??????
        gameContext.setYamaCountdown(0);
        gameContext.setRon(false);
        tehai = EncodeMark.toTehai("123456m56799p11z");
        tehai.pon(HaiPool.p(9), RivalEnum.BOTTOM);
        yakus = yakuMatcher.match(tehai, CollectionUtil.newArrayList(), gameContext);
        assertEquals(1, yakus.size());
        assertTrue(yakus.get(0).getClass() == Haitei.class);

        // ??????
        gameContext.setYamaCountdown(0);
        gameContext.setRon(true);
        tehai = EncodeMark.toTehai("123456m56799p11z");
        tehai.pon(HaiPool.p(9), RivalEnum.BOTTOM);
        yakus = yakuMatcher.match(tehai, CollectionUtil.newArrayList(), gameContext);
        assertEquals(1, yakus.size());
        assertTrue(yakus.get(0).getClass() == Houtei.class);

        // ?????????????????????
        gameContext.setYamaCountdown(50);
        gameContext.setRon(true);
        gameContext.getHaiRivers().forEach(k -> {
            k.setSameJun(true);
            k.reach(HaiPool.random());
        });
        tehai = EncodeMark.toTehai("123456m567999p11z");
        yakus = yakuMatcher.match(tehai, CollectionUtil.newArrayList(), gameContext);
        assertEquals(2, yakus.size());
        assertTrue(yakus.stream().anyMatch(k -> k.getClass() == Iipatu.class));

        // ??????
        gameContext.setYamaCountdown(50);
        gameContext.setRon(true);
        gameContext.getHaiRivers().forEach(k -> {
            k.setSameJun(false);
            k.reach(HaiPool.random());
        });
        tehai = EncodeMark.toTehai("123456m567999p11z");
        yakus = yakuMatcher.match(tehai, CollectionUtil.newArrayList(), gameContext);
        assertEquals(1, yakus.size());
        assertTrue(yakus.get(0).getClass() == Reach.class);

        // ??????
        gameContext.setYamaCountdown(50);
        gameContext.setRon(false);

        gameContext.setLastTehaiActionType(TehaiActionType.ANNKAN);
        gameContext.getHaiRivers().forEach(k -> k.clear());
        tehai = EncodeMark.toTehai("123456m56799p11z");
        tehai.pon(HaiPool.p(9), RivalEnum.BOTTOM);
        yakus = yakuMatcher.match(tehai, CollectionUtil.newArrayList(), gameContext);
        assertEquals(1, yakus.size());
        assertTrue(yakus.get(0).getClass() == Rinsyan.class);

        // ??????
        // ??????????????????????????????????????????
        gameContext.getHaiRivers().forEach(k -> k.setSameFirstJun(false));
        gameContext.setYamaCountdown(50);
        gameContext.setRon(false);
        gameContext.setLastTehaiActionType(null);
        tehai = EncodeMark.toTehai("123456m567999p11z");
        yakus = yakuMatcher.match(tehai, CollectionUtil.newArrayList(), gameContext);
        assertEquals(1, yakus.size());
        assertTrue(yakus.get(0).getClass() == Tuumo.class);

        // ??????
        gameContext.setYamaCountdown(50);
        gameContext.setRon(true);
        gameContext.setLastTehaiActionType(TehaiActionType.KAKAN);
        gameContext.getHaiRivers().forEach(k -> k.clear());
        gameContext.getHaiRivers().forEach(k -> k.setSameFirstJun(false));
        tehai = EncodeMark.toTehai("123456m56799p11z9p");
        yakus = yakuMatcher.match(tehai, CollectionUtil.newArrayList(), gameContext);
        assertEquals(1, yakus.size());
        assertTrue(yakus.get(0).getClass() == TyanKan.class);

        // ?????????
        gameContext = PlayerGameContextFactory.buildByNormal(0);
        gameContext.getHaiRiver().reach(HaiPool.p(1));
        gameContext.setRon(true);
        gameContext.getHaiRivers().forEach(k -> {
            k.setSameFirstJun(true);
            k.setSameJun(false);
            k.reach(HaiPool.random(), true);
        });
        tehai = EncodeMark.toTehai("123456m56799p11z9p");
        yakus = yakuMatcher.match(tehai, CollectionUtil.newArrayList(), gameContext);
        assertEquals(1, yakus.size());
        assertTrue(yakus.get(0).getClass() == DoubleReach.class);
    }

    // ----------------------------------------

    /**
     * ????????????
     */
    @SuppressWarnings("unchecked")
    @Test
    public void mutiYakuTest() {
        PlayerGameContext gameContext = PlayerGameContextFactory.buildByNormal(0);

        // ????????????????????????????????????????????????????????????
        gameContext.getHaiRivers().forEach(k -> {
            k.clear();
            k.setSameFirstJun(false);
            k.setSameJun(true);
            k.reach(HaiPool.random(), false);
        });
        ITehai tehai = EncodeMark.toTehai("11223345678999m");
        gameContext.setTehai(tehai);
        List<DivideInfo> divideInfos = tehaiAgariDivider.divide(tehai);
        List<IYaku> yakus = yakuMatcher.match(tehai, divideInfos, gameContext);
        assertEquals(7, yakus.size());
        assertYakus(yakus, Reach.class, Iipatu.class, Tuumo.class, Tinitu.class, Iipeikou.class, Ittuu.class);

        // ??????????????????
        // ????????????????????????????????????????????????????????????
        gameContext.getHaiRivers().forEach(k -> {
            k.clear();
            k.setSameFirstJun(false);
            k.setSameJun(false);
            k.kiri(HaiPool.random());
        });
        tehai = EncodeMark.toTehai("1112222333345p");
        // ???????????????1p
        tehai.minkan(HaiPool.p(1), RivalEnum.TOP);
        // ?????????4p
        tehai.draw(HaiPool.p(4));
        // ?????????2p
        tehai.annkan(HaiPool.p(2));
        // ?????????4p
        tehai.draw(HaiPool.p(4));
        // ?????????3p
        tehai.annkan(HaiPool.p(3));
        // ??????5p??????
        tehai.draw(HaiPool.p(0));
        // ??????????????????????????????
        gameContext.setLastTehaiActionType(TehaiActionType.ANNKAN);
        // ?????????
        gameContext.setRon(false);
        divideInfos = tehaiAgariDivider.divide(tehai);
        yakus = yakuMatcher.match(tehai, divideInfos, gameContext);
        assertEquals(5, yakus.size());
        assertYakus(yakus, Tinitu.class, Toitoi.class, Sanankou.class, Sankantu.class, Rinsyan.class);

        // ?????? ??????????????????????????????????????????????????????
        gameContext.getHaiRivers().forEach(k -> {
            k.clear();
            k.setSameFirstJun(false);
            k.setSameJun(false);
            k.kiri(HaiPool.random());
        });
        // ?????????
        gameContext.setRon(false);
        // ??????????????????????????????
        gameContext.setLastTehaiActionType(TehaiActionType.ANNKAN);
        // ?????????
        gameContext.setBakaze(KazeEnum.NAN);
        // ?????????
        gameContext.setJikaze(KazeEnum.SYA);
        gameContext.setPosition(3);
        tehai = EncodeMark.toTehai("8s111333556666z");
        tehai.minkan(HaiPool.z(1), RivalEnum.BOTTOM);
        tehai.minkan(HaiPool.z(3), RivalEnum.TOP);
        tehai.pon(HaiPool.z(5), RivalEnum.BOTTOM);
        tehai.annkan(HaiPool.z(6));
        tehai.draw(HaiPool.s(8));
        divideInfos = tehaiAgariDivider.divide(tehai);
        yakus = yakuMatcher.match(tehai, divideInfos, gameContext);
        assertEquals(7, yakus.size());
        assertYakus(yakus, Rinsyan.class, Haku.class, Hatu.class, Jikaze.class, Sankantu.class, Toitoi.class, Honitu.class);
    }

    /**
     * ???????????????
     * 
     * @param actuals ?????????????????????
     * @param expecteds ????????????
     */
    private void assertYakus(List<IYaku> actuals, Class<? extends IYaku>... expecteds) {
        assertTrue(EmptyUtil.isNotEmpty(actuals));
        for (Class<?> yaku : expecteds) {
            assertTrue(actuals.stream().anyMatch(k -> k.getClass() == yaku));
        }
    }

    // -----------------------------------------

    /**
     * ????????????
     */
    @Test
    public void yakumanTest() {
        PlayerGameContext gameContext = PlayerGameContextFactory.buildByNormal(0);

        // ?????????
        ITehai tehai = EncodeMark.toTehai("22233344466s6z");
        tehai.pon(HaiPool.s(6), RivalEnum.BOTTOM);
        tehai.draw(HaiPool.z(6));
        List<DivideInfo> divideInfos = tehaiAgariDivider.divide(tehai);
        List<IYaku> yakus = yakuMatcher.match(tehai, divideInfos, gameContext);
        assertEquals(1, yakus.size());
        assertEquals(Ryuuiisou.class, yakus.get(0).getClass());
    }
}
