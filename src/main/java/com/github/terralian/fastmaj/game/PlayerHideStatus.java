package com.github.terralian.fastmaj.game;

/**
 * 玩家在牌局中的隐藏状态，这些状态对手不可见，但是玩家本身可见
 * 
 * @author terra.lian
 * @since 2022-10-14
 */
public class PlayerHideStatus {

    /**
     * 玩家的向听数
     */
    private int syaten;

    // 振听
    /**
     * 是否同巡振听
     */
    private boolean tempFuriten;
    /**
     * 是否立直振听
     */
    private boolean reachFuriten;
    /**
     * 是否切牌振听
     */
    private boolean kiriFuriten;

    /**
     * 初始化构建{@link PlayerHideStatus}
     */
    public PlayerHideStatus() {
        reset();
    }

    /**
     * 初始化
     */
    public void reset() {
        syaten = 13;
        tempFuriten = false;
        reachFuriten = false;
        kiriFuriten = false;
    }

    // Get - Set

    /**
     * 获取向听数
     */
    public int getSyaten() {
        return syaten;
    }

    /**
     * 设置向听数
     * 
     * @param syaten 向听数
     */
    public void setSyaten(int syaten) {
        this.syaten = syaten;
    }

    /**
     * 是否同巡振听
     */
    public boolean isTempFuriten() {
        return tempFuriten;
    }

    /**
     * 设置同巡振听
     * 
     * @param tempFuriten 同巡振听
     */
    public void setTempFuriten(boolean tempFuriten) {
        this.tempFuriten = tempFuriten;
    }

    /**
     * 是否立直振听
     */
    public boolean isReachFuriten() {
        return reachFuriten;
    }

    /**
     * 设置立直振听
     * 
     * @param reachFuriten 立直振听
     */
    public void setReachFuriten(boolean reachFuriten) {
        this.reachFuriten = reachFuriten;
    }

    /**
     * 是否是模切振听
     */
    public boolean isKiriFuriten() {
        return kiriFuriten;
    }

    /**
     * 设置模切振听
     * 
     * @param kiriFuriten 模切振听
     */
    public void setKiriFuriten(boolean kiriFuriten) {
        this.kiriFuriten = kiriFuriten;
    }

    /**
     * 是否振听
     */
    public boolean isFuriten() {
        return tempFuriten || reachFuriten || kiriFuriten;
    }
}
