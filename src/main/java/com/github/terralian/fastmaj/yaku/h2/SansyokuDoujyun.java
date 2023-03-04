package com.github.terralian.fastmaj.yaku.h2;

import java.util.List;

import com.github.terralian.fastmaj.agari.DivideInfo;
import com.github.terralian.fastmaj.encode.EncodeMark;
import com.github.terralian.fastmaj.game.context.PlayerGameContext;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.tehai.ITehai;
import com.github.terralian.fastmaj.yaku.IYaku;
import com.github.terralian.fastmaj.yaku.YakuNamePool;


/**
 * 三色同顺
 * 
 * @author terra.lian 
 */
public class SansyokuDoujyun implements IYaku {

    @Override
    public boolean match(ITehai tehai, DivideInfo divide, PlayerGameContext holder) {
        if (divide == null || divide.getAllShunzuFirst().size() < 3) {
            return false;
        }
        List<IHai> allFirst = divide.getAllShunzuFirst();
        // 若第二个不是那就不是了
        for (int i = 0; i < 2; i++) {
            // 取第一枚牌，作为值和类型的基准（第一候选）
            IHai firstHai = allFirst.get(i);
            int firstLiteral = EncodeMark.clearRed(firstHai.getLiteral());
            // 用于存储第二枚牌（第二候选）
            IHai secondHai = null;
            // 循环后面的牌
            for (int j = i + 1; j < allFirst.size(); j++) {
                // 取待校验的牌
                IHai tmpHai = allFirst.get(j);
                int tmpLiteral = EncodeMark.clearRed(tmpHai.getLiteral());
                // 若待校验牌值不等或者类型相同，则不是三色候选
                if (firstLiteral != tmpLiteral || firstHai.geHaiType() == tmpHai.geHaiType()) {
                    continue;
                }
                // 是的话，没有第二候选，设置为第二候选
                if (secondHai == null) {
                    secondHai = tmpHai;
                }
                // 有第二候选，则作为第三候选和第一候选，第二候选比较，只要3个类型都不同，就是三色
                else if (firstHai.geHaiType() != tmpHai.geHaiType() && secondHai.geHaiType() != tmpHai.geHaiType()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getName() {
        return YakuNamePool.SansyokuDoujyun;
    }

    @Override
    public int getHan(boolean isNaki) {
        return isNaki ? 1 : 2;
    }

}
