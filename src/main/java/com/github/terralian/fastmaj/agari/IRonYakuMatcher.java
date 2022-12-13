package com.github.terralian.fastmaj.agari;

import java.util.List;

import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;

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
     * @param context 用户信息
     */
    List<IYaku> match(ITehai tehai, IHai agariHai, PlayerGameContext context);
}
