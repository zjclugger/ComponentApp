package com.zjclugger.finance.webapi.entity.response;

import com.google.gson.annotations.SerializedName;
import com.zjclugger.lib.api.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 原始凭证详情
 */
public class OriginalDetailResult extends BaseEntity {

    @SerializedName("id")
    private String id;   //票据ID
    @SerializedName("billType")
    private String billType; //票据类型
    @SerializedName("billTypeId")
    private String billTypeId; //票据类型ID
    @SerializedName("proofStatus")
    private int proofStatus;

    @SerializedName("requestNumber")
    private String requestNumber;   //请求识别号
    @SerializedName("docName")
    private String docName; //票据名称

    @SerializedName("totalLarge")
    private String totalLarge;  //大写
    @SerializedName("totalSmall")
    private double totalSmall;  //小写

    @SerializedName("totalAmount")
    private double totalAmount;  //总金额
    @SerializedName("totalTaxAmount")
    private double totalTaxAmount;  //税额

    @SerializedName("remark")
    private String remark;  //备注
    @SerializedName("code")
    private String code;    //发票代码
    @SerializedName("billNumber")
    private int billNumber; //发票号码
    @SerializedName("billDate")
    private String billDate;    //开票日期
    @SerializedName("payee")
    private String payee;   //收款人
    @SerializedName("review")
    private String review;  //复核
    @SerializedName("drawer")
    private String drawer;  //开票人
    @SerializedName("url")
    private String url; //图片url

    //购买方信息
    @SerializedName("purchaserName")
    private String purchaserName;  //购买方单位名称
    @SerializedName("purchaserNumber")
    private String purchaserNumber;  //购买方纳税人识别号
    @SerializedName("purchaserAddress")
    private String purchaserAddress;  //购买方地址
    @SerializedName("purchaserPhone")
    private String purchaserPhone;  //购买方电话
    @SerializedName("purchaserBank")
    private String purchaserBank;  //购买方账户

    //销售方信息
    @SerializedName("sellerName")
    private String sellerName;  //销售方单位
    @SerializedName("sellerNumber")
    private String sellerNumber;  //销售方纳税人识别号
    @SerializedName("sellerAddress")
    private String sellerAddress;  //销售方地址
    @SerializedName("sellerPhone")
    private String sellerPhone;  //销售方电话
    @SerializedName("sellerBank")
    private String sellerBank;  //销售方账户
    @SerializedName("detail")
    private List<OriginalServiceInfoResult> detailList = new ArrayList<>(); //购销信息集合

    public OriginalDetailResult() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getBillTypeId() {
        return billTypeId;
    }

    public void setBillTypeId(String billTypeId) {
        this.billTypeId = billTypeId;
    }

    public int getProofStatus() {
        return proofStatus;
    }

    public void setProofStatus(int proofStatus) {
        this.proofStatus = proofStatus;
    }

    public String getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(String requestNumber) {
        this.requestNumber = requestNumber;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getTotalLarge() {
        return totalLarge;
    }

    public void setTotalLarge(String totalLarge) {
        this.totalLarge = totalLarge;
    }

    public double getTotalSmall() {
        return totalSmall;
    }

    public void setTotalSmall(double totalSmall) {
        this.totalSmall = totalSmall;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalTaxAmount() {
        return totalTaxAmount;
    }

    public void setTotalTaxAmount(double totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(int billNumber) {
        this.billNumber = billNumber;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getDrawer() {
        return drawer;
    }

    public void setDrawer(String drawer) {
        this.drawer = drawer;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public String getPurchaserNumber() {
        return purchaserNumber;
    }

    public void setPurchaserNumber(String purchaserNumber) {
        this.purchaserNumber = purchaserNumber;
    }

    public String getPurchaserAddress() {
        return purchaserAddress;
    }

    public void setPurchaserAddress(String purchaserAddress) {
        this.purchaserAddress = purchaserAddress;
    }

    public String getPurchaserPhone() {
        return purchaserPhone;
    }

    public void setPurchaserPhone(String purchaserPhone) {
        this.purchaserPhone = purchaserPhone;
    }

    public String getPurchaserBank() {
        return purchaserBank;
    }

    public void setPurchaserBank(String purchaserBank) {
        this.purchaserBank = purchaserBank;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerNumber() {
        return sellerNumber;
    }

    public void setSellerNumber(String sellerNumber) {
        this.sellerNumber = sellerNumber;
    }

    public String getSellerAddress() {
        return sellerAddress;
    }

    public void setSellerAddress(String sellerAddress) {
        this.sellerAddress = sellerAddress;
    }

    public String getSellerPhone() {
        return sellerPhone;
    }

    public void setSellerPhone(String sellerPhone) {
        this.sellerPhone = sellerPhone;
    }

    public String getSellerBank() {
        return sellerBank;
    }

    public void setSellerBank(String sellerBank) {
        this.sellerBank = sellerBank;
    }

    public List<OriginalServiceInfoResult> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<OriginalServiceInfoResult> detailList) {
        this.detailList = detailList;
    }
}
