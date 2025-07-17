package com.wherewasi.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

// Class extends AbstractIT to provide it the required database connectivity

@SpringBootTest
@ActiveProfiles("test")
class WherewasiBackendApplicationTests extends AbstractIT {

    @Test
    void contextLoads() {
    }

}
