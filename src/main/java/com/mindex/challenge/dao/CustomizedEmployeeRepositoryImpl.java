package com.mindex.challenge.dao;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomizedEmployeeRepositoryImpl implements CustomizedEmployeeRepository {
    @Autowired
    @Lazy
    EmployeeRepository employeeRepository;

    @Override
    public ReportingStructure getReportingStructureByEmployeeId(String employeeId) {
        Employee root = employeeRepository.findByEmployeeId(employeeId);
        if (root == null) {
            return null;
        }
        if (root.getDirectReports() == null) {
            return new ReportingStructure(employeeId, 0);
        }

        // holds all children and then all grandchildren, etc as the loop iterates
        List<Employee> generation = Collections.singletonList(root);
        // Don't count the root employee
        int count = 0;
        while (!generation.isEmpty()) {
            // Get the Employee objects of the next generation. If an employee is listed as a direct report but there is
            // no matching employee object with the id it will not be counted.
            generation = employeeRepository.findByEmployeeIdIsIn(generation.stream()
                    .flatMap(e -> e.getDirectReports() == null ? Stream.empty() : e.getDirectReports().stream())
                    .map(Employee::getEmployeeId)
                    .collect(Collectors.toList()));
            count += generation.size();
        }
        return new ReportingStructure(employeeId, count);
    }

    /*
    This attempt was unsuccessful due to the following error
    {
        "timestamp": "2020-05-07T16:03:44.823+0000",
        "status": 500,
        "error": "Internal Server Error",
        "message": "Command failed with error 40324 (Location40324): 'Unrecognized pipeline stage name: '$graphLookup'' on server 127.0.0.1:46821. The full response is {\"$err\": \"Unrecognized pipeline stage name: '$graphLookup'\", \"errmsg\": \"Unrecognized pipeline stage name: '$graphLookup'\", \"code\": 40324, \"codeName\": \"Location40324\", \"ok\": 0}; nested exception is com.mongodb.MongoCommandException: Command failed with error 40324 (Location40324): 'Unrecognized pipeline stage name: '$graphLookup'' on server 127.0.0.1:46821. The full response is {\"$err\": \"Unrecognized pipeline stage name: '$graphLookup'\", \"errmsg\": \"Unrecognized pipeline stage name: '$graphLookup'\", \"code\": 40324, \"codeName\": \"Location40324\", \"ok\": 0}",
        "path": "/employee/16a596ae-edd3-4847-99fe-c4518e82c86f/reporting-structure"
    }
    I'm assuming that modifying the database to enable graphLookup is outside the scope of this exercise.

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<ReportingStructure> getReportingStructureByEmployeeId(String employeeId) {
        List<AggregationOperation> aggregations = new LinkedList<>();

        aggregations.add(Aggregation.match(Criteria.where("employeeId").is(employeeId)));
        aggregations.add(Aggregation
                        .graphLookup("employee")
                        .startWith("employeeId")
                        .connectFrom("employeeId")
                        .connectTo("directReports.employeeId")
                        .as("children"));
        aggregations.add(Aggregation.project("numberOfReports").andExpression("children").size());

        TypedAggregation<ReportingStructure> agg = Aggregation.newAggregation(ReportingStructure.class, aggregations);
        return mongoTemplate.aggregate(agg, ReportingStructure.class, ReportingStructure.class).getMappedResults();
    }
    */
}
