package com.github.terralian.fastmaj.game.option;

/**
 * 包牌规则
 *
 * @author Terra.Lian
 */
public enum YakuBlameRule {

    /**
     * 不使用包牌规则
     */
    DISABLED,

    /**
     * 复合役满下，仅有需要包牌的役进行包牌支付点数，其他复合役按照原样支付
     * <p/>
     * 如大三元字一色包牌时，包牌的玩家仅需包大三元的部分，雀魂采用该规则。
     */
    ONLY_BLAME_YAKU,

    /**
     * 复合役满下的所有点数都在包牌范围内，天凤采取的是这种规则、
     */
    ALL_YAKU,
}
