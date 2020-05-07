package com.mindex.challenge.controller;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportingServiceController {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingServiceController.class);

    @Autowired
    private ReportingStructureService reportingStructureService;

    @GetMapping("/employee/{id}/reporting-structure")
    public ReportingStructure read(@PathVariable String id) {
        LOG.debug("Received employee read request for id [{}]", id);

        return reportingStructureService.read(id);
    }
}
