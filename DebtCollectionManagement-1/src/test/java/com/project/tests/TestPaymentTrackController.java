package com.project.tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.project.controller.PaymentTrackController;
import com.project.debtCollectionManagement.DebtCollectionManagement1Application;
import com.project.dto.TrackDTO;
import com.project.service.PaymentTrackServiceImpl;
@SpringBootTest(classes = DebtCollectionManagement1Application.class)
class TestPaymentTrackController {
	
	@Mock
	private PaymentTrackServiceImpl paymentTrackService;
	
	@InjectMocks
	private PaymentTrackController paymentTrackController;
	
	
	private MockMvc mockMvc;
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc=MockMvcBuilders.standaloneSetup(paymentTrackController).build();
		
		
	}
	
	
	@Test
	public void testPullProcessedApplication_Positive() {
		int month=3;
		int year=2024;
		
		TrackDTO trackDTO1 = new TrackDTO();
		trackDTO1.setLoanAppId("LA001");
		trackDTO1.setMonthNo(3);
		trackDTO1.setDueDateOfPayment(LocalDate.parse("2024-03-12"));
		trackDTO1.setPaymentRecievedDate(LocalDate.parse("2024-03-16"));

		TrackDTO trackDTO2 = new TrackDTO();
		trackDTO2.setLoanAppId("LA003");
		trackDTO2.setMonthNo(3);
		trackDTO2.setDueDateOfPayment(LocalDate.parse("2024-03-07"));
		trackDTO2.setPaymentRecievedDate(LocalDate.parse("2024-03-22"));

		List<TrackDTO> mockData = new ArrayList<>();
		mockData.add(trackDTO1);
		mockData.add(trackDTO2);
		
		
		when(paymentTrackService.pullProcessedLoanApplications(month, year)).thenReturn(mockData);
		ResponseEntity<?> response= paymentTrackController.pullProcessedLoanApplication(month, year);
		verify(paymentTrackService).pullProcessedLoanApplications(month, year);
		
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(mockData, response.getBody());
		
		
	}
	
	
	@Test
	public void testPullProcessedApplication_Negative() {
		int month=7;
		int year=2024;
		
		when(paymentTrackService.pullProcessedLoanApplications(month, year)).thenReturn(null);
		ResponseEntity<?> response= paymentTrackController.pullProcessedLoanApplication(month, year);
		verify(paymentTrackService).pullProcessedLoanApplications(month, year);
		
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	public void testPullProcessedApplicationUri_Positive() throws Exception{
		TrackDTO trackDTO1 = new TrackDTO();
		trackDTO1.setLoanAppId("LA001");
		trackDTO1.setMonthNo(3);
		trackDTO1.setDueDateOfPayment(LocalDate.parse("2024-03-12"));
		trackDTO1.setPaymentRecievedDate(LocalDate.parse("2024-03-16"));

		TrackDTO trackDTO2 = new TrackDTO();
		trackDTO2.setLoanAppId("LA003");
		trackDTO2.setMonthNo(3);
		trackDTO2.setDueDateOfPayment(LocalDate.parse("2024-03-07"));
		trackDTO2.setPaymentRecievedDate(LocalDate.parse("2024-03-22"));
		
		List<TrackDTO> mockData = new ArrayList<>();
		mockData.add(trackDTO1);
		mockData.add(trackDTO2);
		when(paymentTrackService.pullProcessedLoanApplications(3, 2024)).thenReturn(mockData);
		
		
		try {
			mockMvc.perform(get("http://localhost:8081/api/debtCollection/pull?month=3&year=2024"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(mockData.size()));
			
		}
		catch(Exception e) {
			System.out.println(e);
			
		}
		
	}


}
