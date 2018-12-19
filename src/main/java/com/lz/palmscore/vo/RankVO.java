package com.lz.palmscore.vo;

/**
 * Created by 白 on 2018/12/19.
 *
 * 微信端 选手得分结果排名
 */
public class RankVO {
    private Integer orders;//出场顺序
    private String name;//选手姓名
    private double totalScore;//总分
    private Integer rank;//排名

    public RankVO(Integer orders, String name, double totalScore, Integer rank) {
        this.orders = orders;
        this.name = name;
        this.totalScore = totalScore;
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}
