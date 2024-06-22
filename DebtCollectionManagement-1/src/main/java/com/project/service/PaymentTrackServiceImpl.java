package com.project.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.dto.DisplayDTO;
import com.project.dto.StatusDTO;
import com.project.dto.TrackDTO;
import com.project.entities.PaymentTrack;
import com.project.exceptions.StatusUpdateException;
import com.project.repositories.PaymentTrackRepository;
import com.project.util.GenerateMockData;
@Service
public class PaymentTrackServiceImpl implements PaymentTrackService {
	
	@Autowired
	private GenerateMockData generateMockData;
	@Autowired
	private PaymentTrackRepository paymentTrackRepository;
	
	LocalDate localDate=LocalDate.now();
	
	//Get The Processed Loan Applications
	@Override
	public List<TrackDTO> pullProcessedLoanApplications(int month, int year) {
		
		List<TrackDTO> mockData=generateMockData.generateMockData();
		return mockData.stream()
				.filter(trackDTO-> trackDTO.getMonthNo()==month&& trackDTO.getDueDateOfPayment().getYear()==year)
				.collect(Collectors.toList());
	}
	
	
	
	//Saving the pulled Loan Applications

	@Override
	public boolean savePulledLoanApplications(List<TrackDTO> trackDTO) {

		
		List<PaymentTrack> paymentTrack=new ArrayList<>();
		Iterator<TrackDTO> trackDTOs=trackDTO.iterator();
		while(trackDTOs.hasNext()) {
			PaymentTrack paymentTracks=new PaymentTrack();
			TrackDTO track=trackDTOs.next();
			paymentTracks.setLoanAppId(track.getLoanAppId());
			paymentTracks.setMonthNo(track.getMonthNo());
			
			paymentTracks.setDueDateOfPayment(track.getDueDateOfPayment());
			paymentTracks.setPaymentRecievedDate(track.getPaymentRecievedDate());
			
			if(track.getPaymentRecievedDate()== null) {
				paymentTracks.setStatus("Not Recieved");
			}
			else {
				paymentTracks.setStatus("Recieved");
			}
			paymentTrack.add(paymentTracks);
			
			
		}
		if(paymentTrack.size()>0) {
			paymentTrackRepository.saveAll(paymentTrack);
			return true;
		}
		return false;
		
		
		
		
	}
	
	//Fetching the List Of Defaulters.
	@Override
	public List<TrackDTO> getListOfDefaulters() {
		
		List<PaymentTrack> defaulters=paymentTrackRepository.findPaymentRecievedDateAfter();

		List<TrackDTO> defaultersDTO=new ArrayList<>();
	
		for (PaymentTrack paymentTrack: defaulters) {
			TrackDTO trackDTO=new TrackDTO();
			trackDTO.setLoanAppId(paymentTrack.getLoanAppId());
			trackDTO.setMonthNo(paymentTrack.getMonthNo());
			trackDTO.setDueDateOfPayment(paymentTrack.getDueDateOfPayment());
			trackDTO.setPaymentRecievedDate(paymentTrack.getPaymentRecievedDate());
			defaultersDTO.add(trackDTO);
			
		}
		return defaultersDTO;
	}
	
	
	
	//Updating the Status of loan Application
	@Override
	public boolean updateStatus(String loanAppId, StatusDTO statusDTO) {
		PaymentTrack paymentTrack=paymentTrackRepository.findByLoanAppId(loanAppId);
		if(paymentTrack!=null) {
			if(statusDTO.getStatus().equals("Recieved") && !paymentTrack.getStatus().equals("Recieved")) {
				
				paymentTrack.setStatus(statusDTO.getStatus());
				paymentTrack.setPaymentRecievedDate(localDate);
				paymentTrackRepository.save(paymentTrack);
				return true; 
			}
			else if(statusDTO.getStatus().equals("Not-Recieved") && !paymentTrack.getStatus().equals("Not-Recieved")) {
				paymentTrack.setStatus(statusDTO.getStatus());
				paymentTrack.setPaymentRecievedDate(null);
				paymentTrackRepository.save(paymentTrack);
				return true; 
			}
			else {
				throw new StatusUpdateException("Status is already set to desired value");
				
			}
			
		}
		else {
			throw new StatusUpdateException("Payment track Not Found");
		}
		
	}



	@Override
	public List<DisplayDTO> viewToUpdateApplications() {
		List<PaymentTrack> viewAll=paymentTrackRepository.findAll();
		List<DisplayDTO> allRecords=new ArrayList<>();
		for(PaymentTrack paymentTrack:viewAll) {
			DisplayDTO displayDTO=new DisplayDTO();
			displayDTO.setLoanAppId(paymentTrack.getLoanAppId());
			displayDTO.setMonthNo(paymentTrack.getMonthNo());
			displayDTO.setDueDateOfPayment(paymentTrack.getDueDateOfPayment());
			displayDTO.setPaymentRecievedDate(paymentTrack.getPaymentRecievedDate());
			displayDTO.setStatus(paymentTrack.getStatus());
			allRecords.add(displayDTO);
		}
		return allRecords;
	}
	
	
	

	
	

}

