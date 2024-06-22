package com.project.tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.TransientDataAccessException;

import com.project.dto.DisplayDTO;
import com.project.dto.StatusDTO;
import com.project.dto.TrackDTO;
import com.project.entities.PaymentTrack;
import com.project.exceptions.StatusUpdateException;
import com.project.repositories.PaymentTrackRepository;
import com.project.service.PaymentTrackServiceImpl;
import com.project.util.GenerateMockData;

class TestPaymentTrackServiceImpl {

	@Mock
	private GenerateMockData generateMockData;
	@InjectMocks
	private PaymentTrackServiceImpl paymentTrackService;
	@Mock
	private PaymentTrackRepository paymentTrackRepository;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	// Test Cases for Pulling Processed Loan Applications
	@Test
	public void testPullProcessedLoanApplications_Positive() {
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

		when(generateMockData.generateMockData()).thenReturn(mockData);
		List<TrackDTO> result = paymentTrackService.pullProcessedLoanApplications(3, 2024);
		assertNotNull(result);
		assertEquals(2, result.size());

	}

	@Test
	public void testPullProcessedLoanApplications_Negative() {
		when(generateMockData.generateMockData()).thenReturn(Collections.emptyList());
		List<TrackDTO> result=paymentTrackService.pullProcessedLoanApplications(3, 2024);
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

	@Test
	public void testPullProcessedLoanApplications_Exception() {
		when(generateMockData.generateMockData()).thenThrow(new RuntimeException("Mock Exception"));
		assertThrows(RuntimeException.class, ()->paymentTrackService.pullProcessedLoanApplications(3, 2024));

	}

	// Test cases for Saving the Pulled Loan Application

	@Test
	public void testSavePulledLoanApplications_Positive() {
		PaymentTrack mockOfPaymentTrack = Mockito.mock(PaymentTrack.class);

		List<TrackDTO> trackDTO = new ArrayList<>();
		trackDTO.add(new TrackDTO("LA001", 3, LocalDate.parse("2024-03-07"), LocalDate.parse("2024-03-12"), "Home Loan", "Tanmay", "Shelke"));
		trackDTO.add(new TrackDTO("LA002", 3, LocalDate.parse("2024-03-11"), LocalDate.parse("2024-03-21"),  "Home Loan", "Tanmay", "Shelke"));

		paymentTrackService.savePulledLoanApplications(trackDTO);
		verify(paymentTrackRepository).saveAll(anyList());
	}
	@Test
	public void testSavePulledLoanApplications_Negative() {
		MockitoAnnotations.initMocks(this);
		List<TrackDTO> trackDTOs=new ArrayList<>();

		paymentTrackService.savePulledLoanApplications(trackDTOs);

//		verify(paymentTrackRepository).saveAll(anyList());
		verify(paymentTrackRepository, never()).saveAll(anyList());
	}
	
	
	@Test
	public void testGetListOfDefaulters_Positive() {
		List<PaymentTrack> defaulters=new ArrayList<>();
		PaymentTrack defaultersPayment=new PaymentTrack();
		defaultersPayment.setLoanAppId("LA001");
		defaultersPayment.setMonthNo(3);
		defaultersPayment.setDueDateOfPayment(LocalDate.parse("2024-03-07"));
		defaultersPayment.setPaymentRecievedDate(LocalDate.parse("2024-03-21"));
		defaulters.add(defaultersPayment);
		when(paymentTrackRepository.findPaymentRecievedDateAfter()).thenReturn(defaulters);
		List<TrackDTO> defaultersDTO=paymentTrackService.getListOfDefaulters();
		assertEquals(1,defaultersDTO.size());
		assertEquals("LA001", defaultersDTO.get(0).getLoanAppId());
		
		
		
	}
	@Test
	public void testGetListOfDefaulters_Negative() {
		List<PaymentTrack> defaulters=new ArrayList<>();
		PaymentTrack defaultersPayment=new PaymentTrack();

		when(paymentTrackRepository.findPaymentRecievedDateAfter()).thenReturn(defaulters);
		List<TrackDTO> defaultersDTO=paymentTrackService.getListOfDefaulters();
		assertEquals(0,defaultersDTO.size());
	}
	
	
	@Test
	public void testUpdateStatus_Positive() {
		String loanAppId="LA001";
		StatusDTO statusDTO=new StatusDTO();
		statusDTO.setStatus("Recieved");
		PaymentTrack paymentTrack=new PaymentTrack();
		
		paymentTrack.setLoanAppId("LA001");
		paymentTrack.setMonthNo(3);
		paymentTrack.setDueDateOfPayment(LocalDate.parse("2024-03-07"));
		paymentTrack.setPaymentRecievedDate(null);
		paymentTrack.setStatus("Not-Recieved");
		when(paymentTrackRepository.findByLoanAppId(loanAppId)).thenReturn(paymentTrack);
		boolean result=paymentTrackService.updateStatus("LA001", statusDTO);
		assertTrue(result);
		verify(paymentTrackRepository).save(paymentTrack);
		
		
	}
	
	@Test
	public void testUpdateStatus_Negative() {
		
		PaymentTrack paymentTrack=new PaymentTrack();
		paymentTrack.setLoanAppId("LA001");
		paymentTrack.setMonthNo(3);
		paymentTrack.setDueDateOfPayment(LocalDate.parse("2024-03-07"));
		StatusDTO statusDTO=new StatusDTO();
		paymentTrack.setStatus("Recieved");
		statusDTO.setStatus("Recieved");
		when(paymentTrackRepository.findByLoanAppId("LA001")).thenReturn(paymentTrack);
		assertThrows(StatusUpdateException.class, ()->paymentTrackService.updateStatus("LA001", statusDTO));
		verify(paymentTrackRepository,never()).save(any());
		
	}
	
	@Test
	public void testViewAllToUpdate() {
		
		List<PaymentTrack> paymentTracks=new ArrayList<>();
		PaymentTrack paymentTrack=new PaymentTrack();
		
		paymentTrack.setLoanAppId("LA001");
		paymentTrack.setMonthNo(3);
		paymentTrack.setDueDateOfPayment(LocalDate.parse("2024-03-07"));
		paymentTrack.setPaymentRecievedDate(null);
		paymentTrack.setStatus("Not-Recieved");
		paymentTracks.add(paymentTrack);
		when(paymentTrackRepository.findAll()).thenReturn(paymentTracks);
		
		List<DisplayDTO> result=paymentTrackService.viewToUpdateApplications();
		
		assertEquals(paymentTracks.size(), result.size());
		
		
	}
}
