package com.github.terralian.fastmaj.agari;

import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;

import java.util.List;

/**
 * 可以荣和的役匹配
 *
 * @author terra.lian
 */
public interface IRonYakuMatcher {

    /**
     * 匹配可以荣和的役
     *
     * @param tehai 手牌
     * @param agariHai 和了的牌
     * @param context 游戏上下文
     */
    List<IYaku> match(ITehai tehai, IHai agariHai, PlayerGameContext context);
}
