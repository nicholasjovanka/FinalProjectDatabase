package Controller;

public class Staff {
    int staffId, storeId, ownerId, salaryMonthly;
    String staffName, jobType;

    public Staff(int staffId, String staffName, int storeId, int ownerId, String jobType, int salaryMonthly) {
        this.staffId = staffId;
        this.storeId = storeId;
        this.ownerId = ownerId;
        this.salaryMonthly = salaryMonthly;
        this.staffName = staffName;
        this.jobType = jobType;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getSalaryMonthly() {
        return salaryMonthly;
    }

    public void setSalaryMonthly(int salaryMonthly) {
        this.salaryMonthly = salaryMonthly;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }
}
