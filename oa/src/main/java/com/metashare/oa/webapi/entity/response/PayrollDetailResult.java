
package com.zjclugger.oa.webapi.entity.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.zjclugger.lib.api.entity.BaseEntity;

/**
 * 个人工资详情
 */
public class PayrollDetailResult extends BaseEntity implements Parcelable {
    @SerializedName("AbsentCharge")
    private double absentCharge;
    @SerializedName("BaseSalary")
    private double baseSalary;
    @SerializedName("BusinessTripWage")
    private double businessTripWage;
    @SerializedName("ContractWage")
    private double contractWage;
    @SerializedName("Description")
    private String description;
    @SerializedName("EmployeeName")
    private String employeeName;
    @SerializedName("EmplyeeNumber")
    private String employeeNumber;
    @SerializedName("EntityStatus")
    private int entityStatus;
    @SerializedName("HourlyWage")
    private double hourlyWage;
    @SerializedName("HousingFundCharge")
    private double housingFundCharge;
    @SerializedName("Id")
    private int id;
    @SerializedName("LeaveWage")
    private double leaveWage;
    @SerializedName("Name")
    private String name;
    @SerializedName("OverTimeWage")
    private double overTimeWage;
    @SerializedName("OwnerId")
    private int ownerId;
    @SerializedName("PayableWages")
    private double payableWages;
    @SerializedName("PerformancePay")
    private double performancePay;
    @SerializedName("PostSalary")
    private double postSalary;
    @SerializedName("RealWage")
    private double realWage;
    @SerializedName("ResidentSubsidy")
    private double residentSubsidy;
    @SerializedName("SeniorityBasedPay")
    private double seniorityBasedPay;
    @SerializedName("SettlementStartTime")
    private String settlementStartTime;
    @SerializedName("SettlementStopTime")
    private String settlementStopTime;
    @SerializedName("SocialSecurityCharge")
    private double socialSecurityCharge;
    @SerializedName("TrialPeriodSalary")
    private double trialPeriodSalary;
    @SerializedName("WagesAfterDeduction")
    private double wagesAfterDeduction;

    protected PayrollDetailResult(Parcel in) {
        super(in);
        absentCharge = in.readDouble();
        baseSalary = in.readDouble();
        businessTripWage = in.readDouble();
        contractWage = in.readDouble();
        description = in.readString();
        employeeName = in.readString();
        employeeNumber = in.readString();
        entityStatus = in.readInt();
        hourlyWage = in.readDouble();
        housingFundCharge = in.readDouble();
        id = in.readInt();
        leaveWage = in.readDouble();
        name = in.readString();
        overTimeWage = in.readDouble();
        ownerId = in.readInt();
        payableWages = in.readDouble();
        performancePay = in.readDouble();
        postSalary = in.readDouble();
        realWage = in.readDouble();
        residentSubsidy = in.readDouble();
        seniorityBasedPay = in.readDouble();
        settlementStartTime = in.readString();
        settlementStopTime = in.readString();
        socialSecurityCharge = in.readDouble();
        trialPeriodSalary = in.readDouble();
        wagesAfterDeduction = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeDouble(absentCharge);
        dest.writeDouble(baseSalary);
        dest.writeDouble(businessTripWage);
        dest.writeDouble(contractWage);
        dest.writeString(description);
        dest.writeString(employeeName);
        dest.writeString(employeeNumber);
        dest.writeInt(entityStatus);
        dest.writeDouble(hourlyWage);
        dest.writeDouble(housingFundCharge);
        dest.writeInt(id);
        dest.writeDouble(leaveWage);
        dest.writeString(name);
        dest.writeDouble(overTimeWage);
        dest.writeInt(ownerId);
        dest.writeDouble(payableWages);
        dest.writeDouble(performancePay);
        dest.writeDouble(postSalary);
        dest.writeDouble(realWage);
        dest.writeDouble(residentSubsidy);
        dest.writeDouble(seniorityBasedPay);
        dest.writeString(settlementStartTime);
        dest.writeString(settlementStopTime);
        dest.writeDouble(socialSecurityCharge);
        dest.writeDouble(trialPeriodSalary);
        dest.writeDouble(wagesAfterDeduction);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PayrollDetailResult> CREATOR =
            new Creator<PayrollDetailResult>() {
        @Override
        public PayrollDetailResult createFromParcel(Parcel in) {
            return new PayrollDetailResult(in);
        }

        @Override
        public PayrollDetailResult[] newArray(int size) {
            return new PayrollDetailResult[size];
        }
    };

    public double getAbsentCharge() {
        return absentCharge;
    }

    public void setAbsentCharge(double absentCharge) {
        this.absentCharge = absentCharge;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public double getBusinessTripWage() {
        return businessTripWage;
    }

    public void setBusinessTripWage(double businessTripWage) {
        this.businessTripWage = businessTripWage;
    }

    public double getContractWage() {
        return contractWage;
    }

    public void setContractWage(double contractWage) {
        this.contractWage = contractWage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public int getEntityStatus() {
        return entityStatus;
    }

    public void setEntityStatus(int entityStatus) {
        this.entityStatus = entityStatus;
    }

    public double getHourlyWage() {
        return hourlyWage;
    }

    public void setHourlyWage(double hourlyWage) {
        this.hourlyWage = hourlyWage;
    }

    public double getHousingFundCharge() {
        return housingFundCharge;
    }

    public void setHousingFundCharge(double housingFundCharge) {
        this.housingFundCharge = housingFundCharge;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLeaveWage() {
        return leaveWage;
    }

    public void setLeaveWage(double leaveWage) {
        this.leaveWage = leaveWage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getOverTimeWage() {
        return overTimeWage;
    }

    public void setOverTimeWage(double overTimeWage) {
        this.overTimeWage = overTimeWage;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public double getPayableWages() {
        return payableWages;
    }

    public void setPayableWages(double payableWages) {
        this.payableWages = payableWages;
    }

    public double getPerformancePay() {
        return performancePay;
    }

    public void setPerformancePay(double performancePay) {
        this.performancePay = performancePay;
    }

    public double getPostSalary() {
        return postSalary;
    }

    public void setPostSalary(double postSalary) {
        this.postSalary = postSalary;
    }

    public double getRealWage() {
        return realWage;
    }

    public void setRealWage(double realWage) {
        this.realWage = realWage;
    }

    public double getResidentSubsidy() {
        return residentSubsidy;
    }

    public void setResidentSubsidy(double residentSubsidy) {
        this.residentSubsidy = residentSubsidy;
    }

    public double getSeniorityBasedPay() {
        return seniorityBasedPay;
    }

    public void setSeniorityBasedPay(double seniorityBasedPay) {
        this.seniorityBasedPay = seniorityBasedPay;
    }

    public String getSettlementStartTime() {
        return settlementStartTime;
    }

    public void setSettlementStartTime(String settlementStartTime) {
        this.settlementStartTime = settlementStartTime;
    }

    public String getSettlementStopTime() {
        return settlementStopTime;
    }

    public void setSettlementStopTime(String settlementStopTime) {
        this.settlementStopTime = settlementStopTime;
    }

    public double getSocialSecurityCharge() {
        return socialSecurityCharge;
    }

    public void setSocialSecurityCharge(double socialSecurityCharge) {
        this.socialSecurityCharge = socialSecurityCharge;
    }

    public double getTrialPeriodSalary() {
        return trialPeriodSalary;
    }

    public void setTrialPeriodSalary(double trialPeriodSalary) {
        this.trialPeriodSalary = trialPeriodSalary;
    }

    public double getWagesAfterDeduction() {
        return wagesAfterDeduction;
    }

    public void setWagesAfterDeduction(double wagesAfterDeduction) {
        this.wagesAfterDeduction = wagesAfterDeduction;
    }
}
