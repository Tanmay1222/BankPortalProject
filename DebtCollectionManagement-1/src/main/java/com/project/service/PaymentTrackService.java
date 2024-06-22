package com.project.service;

import java.util.List;

import com.project.dto.DisplayDTO;
import com.project.dto.StatusDTO;
import com.project.dto.TrackDTO;

public interface PaymentTrackService {
	List<TrackDTO> pullProcessedLoanApplications(int month,int year);
	boolean savePulledLoanApplications(List<TrackDTO> trackDTO);
	List<TrackDTO> getListOfDefaulters();
	boolean updateStatus(String loanAppId, StatusDTO statusDTO);
	List<DisplayDTO> viewToUpdateApplications();
	

}
