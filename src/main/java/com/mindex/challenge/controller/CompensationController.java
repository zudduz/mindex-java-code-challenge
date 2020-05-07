package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private CompensationService compensationService;

    @PostMapping("/employee/{id}/compensation")
    public Compensation create(@PathVariable String id, @RequestBody Compensation compensation) {
        LOG.debug("Received compensation create request for compensation [{}] with path id [{}]", compensation, id);

        if (!id.equals(compensation.getEmployee().getEmployeeId())) {
            throw new RuntimeException("Employee Id does in Compensation does not match the id specified in the path");
        }
        return compensationService.create(compensation);
    }

    @GetMapping("/employee/{id}/compensation")
    public Compensation read(@PathVariable String id) {
        LOG.debug("Received employee read request for id [{}]", id);

        return compensationService.readLatestByEmployeeId(id);
    }
}
