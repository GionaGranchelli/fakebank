package com.fakebank.assignment;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties="xml-schema=./src/main/resources/xml/schema-records.xsd")
class BackendApplicationTests {

	@Test
	void contextLoads() {
	}

}
