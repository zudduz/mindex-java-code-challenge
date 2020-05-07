package com.mindex.challenge.dao;

import com.mindex.challenge.data.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String>, CustomizedEmployeeRepository {
    Employee findByEmployeeId(String employeeId);
    List<Employee> findByEmployeeIdIsIn(List<String> employeeId);
}
