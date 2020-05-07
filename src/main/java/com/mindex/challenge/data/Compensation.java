package com.mindex.challenge.data;

import java.time.Instant;

public class Compensation {
    private Employee employee;
    private Integer salary;
    private Instant effectiveDate;

    public Compensation() {
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Instant getEffectiveDate() {
        return effectiveDate;
    }

    // Sample String for JSON conversion "2019-05-07T20:55:32.266Z"
    public void setEffectiveDate(Instant effectiveDate) {
        this.effectiveDate = effectiveDate;

    }
}
