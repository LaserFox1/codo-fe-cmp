package com.lkww.codo.codo;

import com.epam.reportportal.junit5.ReportPortalExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith({ReportPortalExtension.class})
class ApplicationTests {
	@Test
	void contextLoads(ApplicationContext applicationContext) {
        assertNotNull(applicationContext);
	}
}
