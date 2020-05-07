package com.mindex.challenge.data;

public class ReportingStructure {
    // matching type of employeeId in Employee for now but would be good to switch to java.util.UUID
    private String employeeId;
    private Integer numberOfReports;

    public ReportingStructure(String employeeId, Integer numberOfReports) {
        this.employeeId = employeeId;
        this.numberOfReports = numberOfReports;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getNumberOfReports() {
        return numberOfReports;
    }

    public void setNumberOfReports(Integer numberOfReports) {
        this.numberOfReports = numberOfReports;
    }
}
