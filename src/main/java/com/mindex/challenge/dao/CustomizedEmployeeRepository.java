package com.mindex.challenge.dao;

import com.mindex.challenge.data.ReportingStructure;

public interface CustomizedEmployeeRepository {
    ReportingStructure getReportingStructureByEmployeeId(String employeeId);
}
