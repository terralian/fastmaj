package com.github.terralian.fastmaj.tehai;

import java.util.List;

import com.github.terralian.fastmaj.encode.EncodeMark;
import com.github.terralian.fastmaj.hai.IHai;
import com.github.terralian.fastmaj.util.Assert;

/**
 * 手牌建造者，用于便捷构建和操作{@link Tehai}
 * <p/>
 * {@link ITehai}设计上仅使用IHai作为传输对象，这么做令接口保持简洁，但也使对人类友好上较差。
 * 这也是该类出现的目的，为了提供更好的构建和操作{@link Tehai}的方法。
 *
 * @see ITehai
 * @see Tehai
 */
public class TehaiBuilder {

    private final ITehai tehai;

    /**
     * 初始化构建一个建造者
     */
    public TehaiBuilder() {
        tehai = new Tehai();
    }

    /**
     * 根据一个已有的手牌初始化建造者，可以对该手牌进行便捷操作
     *
     * @param tehai 手牌
     */
    public TehaiBuilder(ITehai tehai) {
        this.tehai = tehai;
    }

    /**
     * 获取构建的{@link ITehai}
     */
    public ITehai get() {
        return tehai;
    }

    // -----------------------------------------------
    // EncodeMark
    // -----------------------------------------------

    /**
     * 为手牌增加一副面子（由3枚牌组成），如addMenzu("123s")
     * <p/>
     * 该方法不会校验手牌超过了14枚的限制，但是会校验面子参数必须是三枚牌
     *
     * @param menzu 面子，采用{@link EncodeMark}编码
     * @see EncodeMark
     */
    public void addMenzu(String menzu) {
        List<IHai> hais = EncodeMark.toHai(menzu);
        Assert.isTrue(hais.size() == 3, "面子需要仅为三枚牌组成：" + menzu);
        for (IHai hai : hais)
            tehai.draw(hai);
    }
}
