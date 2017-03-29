package com.b1b.js.erpandroid_market.entity;

/**
 Created by 张建宇 on 2017/3/29. */

public class NahuoDan {
    //        "PID":"811245", "CreateDate":"2017-03-03", "FahuoDate":"2017/2/27 10:42:22", "State":
    //        "等待采购", "BuyerID":"101", "ClientID":"161095", "EmployeeID":"101", "分公司":"公司", "部门":
    //        "公司", "业务员":"管理员", "采购员":"管理员", "CorpID":"100", "DeptID":"101"

    private String pid;
    private String createDate;
    private String fahuoDate;
    private String state;
    private String buyerID;
    private String clientID;
    private String employeeID;
    private String branchCompanyName;
    private String deptName;
    private String saleMan;
    private String buyerName;
    private String corpID;
    private String deptID;

    public NahuoDan(String pid, String createDate, String fahuoDate, String state, String buyerID, String clientID, String employeeID, String branchCompanyName, String deptName, String saleMan, String buyerName, String corpID, String deptID) {
        this.pid = pid;
        this.createDate = createDate;
        this.fahuoDate = fahuoDate;
        this.state = state;
        this.buyerID = buyerID;
        this.clientID = clientID;
        this.employeeID = employeeID;
        this.branchCompanyName = branchCompanyName;
        this.deptName = deptName;
        this.saleMan = saleMan;
        this.buyerName = buyerName;
        this.corpID = corpID;
        this.deptID = deptID;
    }

    @Override
    public String toString() {
        return
                "单据号=" + pid +"\n"+
                        "创建日期=" + createDate +"\n"+
                        "发货日期=" + fahuoDate +"\n"+
                        "单据状态=" + state +"\n"+
                        "分公司名称=" + branchCompanyName +"\n"+
                        "部门名=" + deptName +"\n"+
                        "业务员=" + saleMan +"\n"+
                        "采购员=" + buyerName;
    }

    public NahuoDan() {
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getFahuoDate() {
        return fahuoDate;
    }

    public void setFahuoDate(String fahuoDate) {
        this.fahuoDate = fahuoDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBuyerID() {
        return buyerID;
    }

    public void setBuyerID(String buyerID) {
        this.buyerID = buyerID;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getBranchCompanyName() {
        return branchCompanyName;
    }

    public void setBranchCompanyName(String branchCompanyName) {
        this.branchCompanyName = branchCompanyName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getSaleMan() {
        return saleMan;
    }

    public void setSaleMan(String saleMan) {
        this.saleMan = saleMan;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getCorpID() {
        return corpID;
    }

    public void setCorpID(String corpID) {
        this.corpID = corpID;
    }

    public String getDeptID() {
        return deptID;
    }

    public void setDeptID(String deptID) {
        this.deptID = deptID;
    }
}
