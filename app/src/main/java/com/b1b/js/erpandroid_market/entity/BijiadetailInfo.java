package com.b1b.js.erpandroid_market.entity;

/**
 Created by 张建宇 on 2017/2/23. */

public class BijiadetailInfo {
    private String startTime;
    private String uid;
    private String userName;
    private String mark;
    private String price;
    private String hasFapiao;
    private String providerName;
    private String fukuanType;
    private String jiezhangDate;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getHasFapiao() {
        return hasFapiao;
    }

    public void setHasFapiao(String hasFapiao) {
        this.hasFapiao = hasFapiao;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getFukuanType() {
        return fukuanType;
    }

    public void setFukuanType(String fukuanType) {
        this.fukuanType = fukuanType;
    }

    public String getJiezhangDate() {
        return jiezhangDate;
    }

    public void setJiezhangDate(String jiezhangDate) {
        this.jiezhangDate = jiezhangDate;
    }

    @Override
    public String toString() {
        return
                "时间=" + startTime + "\n" +
                        "用户ID=" + uid + "\n" +
                        "用户名=" + userName + "\n" +
                        "比价价格=" + price + "\n" +
                        "付款类型=" + fukuanType + "\n" +
                        "是否开票=" + hasFapiao + "\n" +
                        "供应商名称=" + providerName + "\n" +
                        "结账日期=" + jiezhangDate + "\n" +
                        "备注=" + mark;

    }


    public BijiadetailInfo() {
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public BijiadetailInfo(String startTime, String uid, String userName, String mark) {
        this.startTime = startTime;
        this.uid = uid;
        this.userName = userName;
        this.mark = mark;
    }
}
