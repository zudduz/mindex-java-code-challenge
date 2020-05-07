package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;

@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private CompensationRepository compensationRepository;

    @Override
    public Compensation create(Compensation compensation) {
        LOG.debug("Creating compensation [{}]", compensation);

        compensationRepository.insert(compensation);

        return compensation;
    }

    @Override
    public Compensation readLatestByEmployeeId(String employeeId) {
        LOG.debug("Reading compensation with employeeId [{}]", employeeId);

        Collection<Compensation> compensations = compensationRepository.findByEmployee_EmployeeId(employeeId);

        if (compensations == null || compensations.isEmpty()) {
            throw new RuntimeException("Invalid compensation for employeeId: " + employeeId);
        }

        // TODO get requirements on what to do with multiple hits from the data store and if necessary move sort to repository
        // Sort Descending by effective date
        // get latest date. Should we instead get based on the current date?
        return compensations.stream().min((c1, c2) -> {
            Instant c1Date = c1.getEffectiveDate();
            if (c1Date == null) {
                c1Date = Instant.MIN;
            }
            Instant c2Date = c2.getEffectiveDate();
            if (c2Date == null) {
                c2Date = Instant.MIN;
            }
            return c2Date.compareTo(c1Date);
        }).get();
    }
}
