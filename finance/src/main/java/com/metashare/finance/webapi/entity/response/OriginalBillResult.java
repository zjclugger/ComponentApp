package com.zjclugger.finance.webapi.entity.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.zjclugger.lib.api.entity.BaseEntity;

/**
 * 原始凭证
 */
public class OriginalBillResult extends BaseEntity implements Parcelable {

    @SerializedName("docName")
    private String docName; //票据名称
    @SerializedName("fileName")
    private String fileName;    //票据文件名称
    @SerializedName("purchaserName")
    private String purchaserName;   //购买方单位名称
    @SerializedName("purchaserBank")
    private String purchaserBank;   //购买方单位银行
    @SerializedName("sellerName")
    private String sellerName; //销售方单位名称
    @SerializedName("sellerBank")
    private String sellerBank; //销售方单位银行
    @SerializedName("status")
    private int status; //凭证状态（0/已识别，1/未识别）
    @SerializedName("isProofread")
    private boolean isProofread;    //是否校对（true/已校对，false/未校对）
    @SerializedName("uploader")
    private String uploader;    //上传人
    @SerializedName("uploadDate")
    private String uploadDate;  //上传时间
    @SerializedName("billDate")
    private String billDate;    //开票日期
    @SerializedName("payee")
    private String payee;    //收款人
    @SerializedName("review")
    private String review;    //复核人
    @SerializedName("drawer")
    private String drawer;    //出票人
    @SerializedName("totalAmount")
    private double totalAmount;  //总额
    @SerializedName("totalTaxAmount")
    private double totalTaxAmount;  //税总额
    @SerializedName("url")
    private String url; //凭证图片url
    @SerializedName("id")
    private String id; //凭证图片url
    @SerializedName("billType")
    private String billType;    //开票日期
    @SerializedName("proofStatus")
    /**
     * 待完善、待校对、待入账
     */
    private String proofStatus;
    @SerializedName("reason")
    private String reason;    //报销原由

    protected OriginalBillResult(Parcel in) {
        super(in);
        docName = in.readString();
        fileName = in.readString();
        purchaserName = in.readString();
        purchaserBank = in.readString();
        sellerName = in.readString();
        sellerBank = in.readString();
        status = in.readInt();
        isProofread = in.readByte() != 0;
        uploader = in.readString();
        uploadDate = in.readString();
        billDate = in.readString();
        payee = in.readString();
        review = in.readString();
        drawer = in.readString();
        totalAmount = in.readDouble();
        totalTaxAmount = in.readDouble();
        url = in.readString();
        id = in.readString();
        billType = in.readString();
        proofStatus = in.readString();
        reason = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(docName);
        dest.writeString(fileName);
        dest.writeString(purchaserName);
        dest.writeString(purchaserBank);
        dest.writeString(sellerName);
        dest.writeString(sellerBank);
        dest.writeInt(status);
        dest.writeByte((byte) (isProofread ? 1 : 0));
        dest.writeString(uploader);
        dest.writeString(uploadDate);
        dest.writeString(billDate);
        dest.writeString(payee);
        dest.writeString(review);
        dest.writeString(drawer);
        dest.writeDouble(totalAmount);
        dest.writeDouble(totalTaxAmount);
        dest.writeString(url);
        dest.writeString(id);
        dest.writeString(billType);
        dest.writeString(proofStatus);
        dest.writeString(reason);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OriginalBillResult> CREATOR = new Creator<OriginalBillResult>() {
        @Override
        public OriginalBillResult createFromParcel(Parcel in) {
            return new OriginalBillResult(in);
        }

        @Override
        public OriginalBillResult[] newArray(int size) {
            return new OriginalBillResult[size];
        }
    };

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
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

    public String getSellerBank() {
        return sellerBank;
    }

    public void setSellerBank(String sellerBank) {
        this.sellerBank = sellerBank;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isProofread() {
        return isProofread;
    }

    public void setProofread(boolean proofread) {
        isProofread = proofread;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    /**
     * 待完善、待校对、待入账
     */
    public String getProofStatus() {
        return proofStatus;
    }

    public void setProofStatus(String proofStatus) {
        this.proofStatus = proofStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
