package com.spring.ebank;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
class EbankApplicationTests {
	public final EbankApplication ebankapplication= new EbankApplication();
	@Test
	void contextLoads() {
	}

	@Test
	public void testApiRoot(){
		assertEquals("Hello!", ebankapplication.apiRoot());
	}
}
