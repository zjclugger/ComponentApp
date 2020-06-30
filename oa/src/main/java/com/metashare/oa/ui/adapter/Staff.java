
package com.zjclugger.oa.ui.adapter;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.zjclugger.lib.entity.hr.common.EmployeeInsurance;
import com.zjclugger.lib.entity.hr.common.GearPosition;
import com.zjclugger.lib.entity.hr.common.HousingProvidentFund;
import com.zjclugger.lib.entity.hr.common.Post;
import com.zjclugger.lib.entity.hr.common.WorkingAgeSalary;

public class Staff implements Parcelable {

    @SerializedName("AlternateContactWay")
    private String alternateContactWay;
    @SerializedName("BankCardNumber")
    private String bankCardNumber;
    @SerializedName("BankNumber")
    private String bankNumber;
    @SerializedName("BaseSalary")
    private double baseSalary;
    @SerializedName("CertificateNumber")
    private String certificateNumber;
    @SerializedName("CertificateType")
    private String certificateType;
    @SerializedName("Company")
    private Company company;
    @SerializedName("CompanyMail")
    private String companyMail;
    @SerializedName("ContactWay")
    private String contactWay;
    @SerializedName("DateOfBirth")
    private String dateOfBirth;
    @SerializedName("Department")
    private Department department;
    @SerializedName("Description")
    private String description;
    @SerializedName("Education")
    private String education;
    @SerializedName("EmergencyContact")
    private String emergencyContact;
    @SerializedName("EmergencyContactPthon")
    private String emergencyContactPthon;
    @SerializedName("EmployeeInsurance")
    private EmployeeInsurance employeeInsurance;
    @SerializedName("EmployeeNature")
    private int employeeNature;
    @SerializedName("EmployeeStatus")
    private int employeeStatus;
    @SerializedName("EmplyeeNumber")
    private String emplyeeNumber;
    @SerializedName("EnglishName")
    private String englishName;
    @SerializedName("EntityStatus")
    private int entityStatus;
    @SerializedName("EntryTime")
    private String entryTime;
    @SerializedName("EstimatedPositiveTime")
    private String estimatedPositiveTime;
    @SerializedName("FinalEducation")
    private String finalEducation;
    @SerializedName("GearPosition")
    private GearPosition gearPosition;
    @SerializedName("GraduateInstitutions")
    private String graduateInstitutions;
    @SerializedName("HouseholdRegisterNature")
    private int householdRegisterNature;
    @SerializedName("HousingProvidentFund")
    private HousingProvidentFund housingProvidentFund;
    @SerializedName("Id")
    private int id;
    @SerializedName("IfSpecialCrowd")
    private boolean ifSpecialCrowd;
    @SerializedName("IsFromRS")
    private boolean isFromRS;
    @SerializedName("IsResident")
    private boolean isResident;
    @SerializedName("LaborContract")
    private String laborContract;
    @SerializedName("Major")
    private String major;
    @SerializedName("MaritalStatus")
    private int maritalStatus;
    @SerializedName("Name")
    private String name;
    @SerializedName("Nation")
    private String nation;
    @SerializedName("Nationality")
    private String nationality;
    @SerializedName("OwnerId")
    private int ownerId;
    @SerializedName("PermanentResidenceAddress")
    private String permanentResidenceAddress;
    @SerializedName("PersonalMail")
    private String personalMail;
    @SerializedName("PersonnelMaterial")
    private String personnelMaterial;
    @SerializedName("PositiveTime")
    private String positiveTime;
    @SerializedName("Post")
    private Post post;
    @SerializedName("ProbationPeriod")
    private boolean probationPeriod;
    @SerializedName("ResidentSubsidy")
    private int residentSubsidy;
    @SerializedName("Sex")
    private int sex;
    @SerializedName("SpecialCrowdNumber")
    private String specialCrowdNumber;
    @SerializedName("TimeOfGraduation")
    private String timeOfGraduation;
    @SerializedName("TrialPeriodSalary")
    private double trialPeriodSalary;
    @SerializedName("WageType")
    private int wageType;
    @SerializedName("WorkingAgeSalary")
    private WorkingAgeSalary workingAgeSalary;
    @SerializedName("WorkingTime")
    private int workingTime;

    public Staff() {
    }

    protected Staff(Parcel in) {
        alternateContactWay = in.readString();
        bankCardNumber = in.readString();
        bankNumber = in.readString();
        baseSalary = in.readDouble();
        certificateNumber = in.readString();
        certificateType = in.readString();
        company = in.readParcelable(Company.class.getClassLoader());
        companyMail = in.readString();
        contactWay = in.readString();
        dateOfBirth = in.readString();
        department = in.readParcelable(Department.class.getClassLoader());
        description = in.readString();
        education = in.readString();
        emergencyContact = in.readString();
        emergencyContactPthon = in.readString();
        employeeInsurance = in.readParcelable(EmployeeInsurance.class.getClassLoader());
        employeeNature = in.readInt();
        employeeStatus = in.readInt();
        emplyeeNumber = in.readString();
        englishName = in.readString();
        entityStatus = in.readInt();
        entryTime = in.readString();
        estimatedPositiveTime = in.readString();
        finalEducation = in.readString();
        gearPosition = in.readParcelable(GearPosition.class.getClassLoader());
        graduateInstitutions = in.readString();
        householdRegisterNature = in.readInt();
        housingProvidentFund = in.readParcelable(HousingProvidentFund.class.getClassLoader());
        id = in.readInt();
        ifSpecialCrowd = in.readByte() != 0;
        isFromRS = in.readByte() != 0;
        isResident = in.readByte() != 0;
        laborContract = in.readString();
        major = in.readString();
        maritalStatus = in.readInt();
        name = in.readString();
        nation = in.readString();
        nationality = in.readString();
        ownerId = in.readInt();
        permanentResidenceAddress = in.readString();
        personalMail = in.readString();
        personnelMaterial = in.readString();
        positiveTime = in.readString();
        post = in.readParcelable(Post.class.getClassLoader());
        probationPeriod = in.readByte() != 0;
        residentSubsidy = in.readInt();
        sex = in.readInt();
        specialCrowdNumber = in.readString();
        timeOfGraduation = in.readString();
        trialPeriodSalary = in.readDouble();
        wageType = in.readInt();
        workingAgeSalary = in.readParcelable(WorkingAgeSalary.class.getClassLoader());
        workingTime = in.readInt();
    }

    public static final Creator<Staff> CREATOR = new Creator<Staff>() {
        @Override
        public Staff createFromParcel(Parcel in) {
            return new Staff(in);
        }

        @Override
        public Staff[] newArray(int size) {
            return new Staff[size];
        }
    };

    public String getAlternateContactWay() {
        return alternateContactWay;
    }

    public void setAlternateContactWay(String alternateContactWay) {
        this.alternateContactWay = alternateContactWay;
    }

    public String getBankCardNumber() {
        return bankCardNumber;
    }

    public void setBankCardNumber(String bankCardNumber) {
        this.bankCardNumber = bankCardNumber;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getCompanyMail() {
        return companyMail;
    }

    public void setCompanyMail(String companyMail) {
        this.companyMail = companyMail;
    }

    public String getContactWay() {
        return contactWay;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getEmergencyContactPthon() {
        return emergencyContactPthon;
    }

    public void setEmergencyContactPthon(String emergencyContactPthon) {
        this.emergencyContactPthon = emergencyContactPthon;
    }

    public EmployeeInsurance getEmployeeInsurance() {
        return employeeInsurance;
    }

    public void setEmployeeInsurance(EmployeeInsurance employeeInsurance) {
        this.employeeInsurance = employeeInsurance;
    }

    public int getEmployeeNature() {
        return employeeNature;
    }

    public void setEmployeeNature(int employeeNature) {
        this.employeeNature = employeeNature;
    }

    public int getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(int employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    public String getEmplyeeNumber() {
        return emplyeeNumber;
    }

    public void setEmplyeeNumber(String emplyeeNumber) {
        this.emplyeeNumber = emplyeeNumber;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public int getEntityStatus() {
        return entityStatus;
    }

    public void setEntityStatus(int entityStatus) {
        this.entityStatus = entityStatus;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getEstimatedPositiveTime() {
        return estimatedPositiveTime;
    }

    public void setEstimatedPositiveTime(String estimatedPositiveTime) {
        this.estimatedPositiveTime = estimatedPositiveTime;
    }

    public String getFinalEducation() {
        return finalEducation;
    }

    public void setFinalEducation(String finalEducation) {
        this.finalEducation = finalEducation;
    }

    public GearPosition getGearPosition() {
        return gearPosition;
    }

    public void setGearPosition(GearPosition gearPosition) {
        this.gearPosition = gearPosition;
    }

    public String getGraduateInstitutions() {
        return graduateInstitutions;
    }

    public void setGraduateInstitutions(String graduateInstitutions) {
        this.graduateInstitutions = graduateInstitutions;
    }

    public int getHouseholdRegisterNature() {
        return householdRegisterNature;
    }

    public void setHouseholdRegisterNature(int householdRegisterNature) {
        this.householdRegisterNature = householdRegisterNature;
    }

    public HousingProvidentFund getHousingProvidentFund() {
        return housingProvidentFund;
    }

    public void setHousingProvidentFund(HousingProvidentFund housingProvidentFund) {
        this.housingProvidentFund = housingProvidentFund;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIfSpecialCrowd() {
        return ifSpecialCrowd;
    }

    public void setIfSpecialCrowd(boolean ifSpecialCrowd) {
        this.ifSpecialCrowd = ifSpecialCrowd;
    }

    public boolean isFromRS() {
        return isFromRS;
    }

    public void setFromRS(boolean fromRS) {
        isFromRS = fromRS;
    }

    public boolean isResident() {
        return isResident;
    }

    public void setResident(boolean resident) {
        isResident = resident;
    }

    public String getLaborContract() {
        return laborContract;
    }

    public void setLaborContract(String laborContract) {
        this.laborContract = laborContract;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(int maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getPermanentResidenceAddress() {
        return permanentResidenceAddress;
    }

    public void setPermanentResidenceAddress(String permanentResidenceAddress) {
        this.permanentResidenceAddress = permanentResidenceAddress;
    }

    public String getPersonalMail() {
        return personalMail;
    }

    public void setPersonalMail(String personalMail) {
        this.personalMail = personalMail;
    }

    public String getPersonnelMaterial() {
        return personnelMaterial;
    }

    public void setPersonnelMaterial(String personnelMaterial) {
        this.personnelMaterial = personnelMaterial;
    }

    public String getPositiveTime() {
        return positiveTime;
    }

    public void setPositiveTime(String positiveTime) {
        this.positiveTime = positiveTime;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public boolean isProbationPeriod() {
        return probationPeriod;
    }

    public void setProbationPeriod(boolean probationPeriod) {
        this.probationPeriod = probationPeriod;
    }

    public int getResidentSubsidy() {
        return residentSubsidy;
    }

    public void setResidentSubsidy(int residentSubsidy) {
        this.residentSubsidy = residentSubsidy;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getSpecialCrowdNumber() {
        return specialCrowdNumber;
    }

    public void setSpecialCrowdNumber(String specialCrowdNumber) {
        this.specialCrowdNumber = specialCrowdNumber;
    }

    public String getTimeOfGraduation() {
        return timeOfGraduation;
    }

    public void setTimeOfGraduation(String timeOfGraduation) {
        this.timeOfGraduation = timeOfGraduation;
    }

    public double getTrialPeriodSalary() {
        return trialPeriodSalary;
    }

    public void setTrialPeriodSalary(double trialPeriodSalary) {
        this.trialPeriodSalary = trialPeriodSalary;
    }

    public int getWageType() {
        return wageType;
    }

    public void setWageType(int wageType) {
        this.wageType = wageType;
    }

    public WorkingAgeSalary getWorkingAgeSalary() {
        return workingAgeSalary;
    }

    public void setWorkingAgeSalary(WorkingAgeSalary workingAgeSalary) {
        this.workingAgeSalary = workingAgeSalary;
    }

    public int getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(int workingTime) {
        this.workingTime = workingTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(alternateContactWay);
        dest.writeString(bankCardNumber);
        dest.writeString(bankNumber);
        dest.writeDouble(baseSalary);
        dest.writeString(certificateNumber);
        dest.writeString(certificateType);
        dest.writeParcelable(company, flags);
        dest.writeString(companyMail);
        dest.writeString(contactWay);
        dest.writeString(dateOfBirth);
        dest.writeParcelable(department, flags);
        dest.writeString(description);
        dest.writeString(education);
        dest.writeString(emergencyContact);
        dest.writeString(emergencyContactPthon);
        dest.writeParcelable(employeeInsurance, flags);
        dest.writeInt(employeeNature);
        dest.writeInt(employeeStatus);
        dest.writeString(emplyeeNumber);
        dest.writeString(englishName);
        dest.writeInt(entityStatus);
        dest.writeString(entryTime);
        dest.writeString(estimatedPositiveTime);
        dest.writeString(finalEducation);
        dest.writeParcelable(gearPosition, flags);
        dest.writeString(graduateInstitutions);
        dest.writeInt(householdRegisterNature);
        dest.writeParcelable(housingProvidentFund, flags);
        dest.writeInt(id);
        dest.writeByte((byte) (ifSpecialCrowd ? 1 : 0));
        dest.writeByte((byte) (isFromRS ? 1 : 0));
        dest.writeByte((byte) (isResident ? 1 : 0));
        dest.writeString(laborContract);
        dest.writeString(major);
        dest.writeInt(maritalStatus);
        dest.writeString(name);
        dest.writeString(nation);
        dest.writeString(nationality);
        dest.writeInt(ownerId);
        dest.writeString(permanentResidenceAddress);
        dest.writeString(personalMail);
        dest.writeString(personnelMaterial);
        dest.writeString(positiveTime);
        dest.writeParcelable(post, flags);
        dest.writeByte((byte) (probationPeriod ? 1 : 0));
        dest.writeInt(residentSubsidy);
        dest.writeInt(sex);
        dest.writeString(specialCrowdNumber);
        dest.writeString(timeOfGraduation);
        dest.writeDouble(trialPeriodSalary);
        dest.writeInt(wageType);
        dest.writeParcelable(workingAgeSalary, flags);
        dest.writeInt(workingTime);
    }
}
