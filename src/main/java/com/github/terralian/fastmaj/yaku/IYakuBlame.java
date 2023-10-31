package com.github.terralian.fastmaj.yaku;

import com.github.terralian.fastmaj.game.GameConfig;
import com.github.terralian.fastmaj.player.RivalEnum;
import com.github.terralian.fastmaj.tehai.ITehai;

/**
 * 会触发包牌规则的役，如大三元和大四喜。
 * <p/>
 * 该译名的来源，包牌在日文中可以称为（責任払い），而Blame意为责备。这里使用该词来指代包牌。
 *
 * @author terra.lian
 */
public interface IYakuBlame extends IYaku {

    /**
     * 该役为包牌役
     */
    @Override
    default boolean isYakuBlame() {
        return true;
    }

    /**
     * 是否有包牌（パオ）规则。
     * <p/>
     * 比较常见的含包牌的有三种，大三元、大四喜、四杠子（天凤非包牌），若役中存在需要包牌的部分，那么分数计算时需要按包牌规则计算
     */
    boolean usingBlameRule(GameConfig config);

    /**
     * 根据手牌推断是否包牌，且包牌的玩家的大致对象（上家下家..)
     * <p/>
     * 若手牌记录中不含鸣牌形状，则会推断失败；亦或者该役不含包牌，都会返回null
     *
     * @param tehai 手牌
     * @return RivalEnum | null
     */
    RivalEnum deduceBlameRival(ITehai tehai);
}
