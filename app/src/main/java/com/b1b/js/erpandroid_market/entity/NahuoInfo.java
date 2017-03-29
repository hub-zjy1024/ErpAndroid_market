package com.b1b.js.erpandroid_market.entity;

/**
 Created by 张建宇 on 2017/3/29. */

public class NahuoInfo {
//    String createDate = tJobj.getString("CreateDate");
//    String corpId = tJobj.getString("CorpID");
//    String state = tJobj.getString("State");
//    String depteId = tJobj.getString("DeptID");
//    String clientInvoice = tJobj.getString("ClientInvoice");
//    String employeeID = tJobj.getString("EmployeeID");
//    String note = tJobj.getString("Note");
//    String invoiceType = tJobj.getString("invoiceType");
//    String providerID = tJobj.getString("ProviderID");
//    String providerName = tJobj.getString("ProviderName");
//    String childCompany = tJobj.getString("分公司");
//    String deptName = tJobj.getString("部门");
//    String saleMan = tJobj.getString("业务员");
//    String kaipiaoType = tJobj.getString("客户开票");
//    String kaipiaoCompany = tJobj.getString("开票公司");
//    String kaipiaoFlag = tJobj.getString("是否开票");
    private String createDate;
    private String corpId;
    private String state;
    private String depteId;
    private String clientInvoice;
    private String employeeID;
    private String note;
    private String invoiceType;
    private String providerID;
    private String providerName;
    private String childCompany;
    private String deptName;
    private String saleMan;
    private String kaipiaoType;
    private String kaipiaoCompany;
    private String kaipiaoFlag;
    public NahuoInfo() {
    }

    public NahuoInfo(String createDate, String corpId, String state, String depteId, String clientInvoice, String employeeID, String note, String invoiceType, String providerID, String providerName, String childCompany, String deptName, String saleMan, String kaipiaoType, String kaipiaoCompany, String kaipiaoFlag) {
        this.createDate = createDate;
        this.corpId = corpId;
        this.state = state;
        this.depteId = depteId;
        this.clientInvoice = clientInvoice;
        this.employeeID = employeeID;
        this.note = note;
        this.invoiceType = invoiceType;
        this.providerID = providerID;
        this.providerName = providerName;
        this.childCompany = childCompany;
        this.deptName = deptName;
        this.saleMan = saleMan;
        this.kaipiaoType = kaipiaoType;
        this.kaipiaoCompany = kaipiaoCompany;
        this.kaipiaoFlag = kaipiaoFlag;
    }

    @Override
    public String toString() {
        return "NahuoInfo{" +
                "createDate='" + createDate + 
                ", state='" + state + 
                ", note='" + note + 
                ", providerName='" + providerName + 
                ", childCompany='" + childCompany + 
                ", deptName='" + deptName + 
                ", saleMan='" + saleMan + 
                ", kaipiaoType='" + kaipiaoType + 
                ", kaipiaoCompany='" + kaipiaoCompany + 
                ", kaipiaoFlag='" + kaipiaoFlag + 
                '}';
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDepteId() {
        return depteId;
    }

    public void setDepteId(String depteId) {
        this.depteId = depteId;
    }

    public String getClientInvoice() {
        return clientInvoice;
    }

    public void setClientInvoice(String clientInvoice) {
        this.clientInvoice = clientInvoice;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getProviderID() {
        return providerID;
    }

    public void setProviderID(String providerID) {
        this.providerID = providerID;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getChildCompany() {
        return childCompany;
    }

    public void setChildCompany(String childCompany) {
        this.childCompany = childCompany;
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

    public String getKaipiaoType() {
        return kaipiaoType;
    }

    public void setKaipiaoType(String kaipiaoType) {
        this.kaipiaoType = kaipiaoType;
    }

    public String getKaipiaoCompany() {
        return kaipiaoCompany;
    }

    public void setKaipiaoCompany(String kaipiaoCompany) {
        this.kaipiaoCompany = kaipiaoCompany;
    }

    public String getKaipiaoFlag() {
        return kaipiaoFlag;
    }

    public void setKaipiaoFlag(String kaipiaoFlag) {
        this.kaipiaoFlag = kaipiaoFlag;
    }
}
