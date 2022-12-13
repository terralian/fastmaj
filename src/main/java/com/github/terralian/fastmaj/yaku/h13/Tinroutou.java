package com.github.terralian.fastmaj.yaku.h13;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.encode.Encode34;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYakuman;
import com.github.terralian.fastmaj.yaku.YakuNamePool;

/**
 * 清老头
 * 
 * @author terra.lian 
 */
public class Tinroutou implements IYakuman {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        for (IHai hai : tehai.getAll()) {
            int value = hai.getValue();
            // 19m19p19s
            if (!(value == Encode34.MAN_1 || value == Encode34.MAN_9 //
                    || value == Encode34.PIN_1 || value == Encode34.PIN_9 //
                    || value == Encode34.SO_1 || value == Encode34.SO_9)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getName() {
        return YakuNamePool.Tinroutou;
    }

}
