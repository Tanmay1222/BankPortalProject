package com.project.debtCollectionManagement;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.controller.PaymentTrackController;

@SpringBootTest
class DebtCollectionManagement1ApplicationTests {
	@Autowired
	private PaymentTrackController paymentTrackController;
	
	@Test
	void contextLoads() {
		assertNotNull(paymentTrackController);
	}

}
