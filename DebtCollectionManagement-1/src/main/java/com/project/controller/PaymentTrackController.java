package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.dto.DisplayDTO;
import com.project.dto.StatusDTO;
import com.project.dto.TrackDTO;
import com.project.dto.UserDTO;
import com.project.service.PaymentTrackService;
import com.project.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/debtCollection")
@Validated
@Slf4j
@Tag(name="Bank Officer",description = "Payment Track Rest API")
/**
 * @author Tanmay Shelke
 * This class represents Rest API endpoints for Payment Track
 */
public class PaymentTrackController {
	@Autowired
	private PaymentTrackService paymentTrackService;
	
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/pull")
	public ResponseEntity<?> pullProcessedLoanApplication(@RequestParam int month,@RequestParam int year){
		List<TrackDTO> processedLoanApplications=paymentTrackService.pullProcessedLoanApplications(month, year);
		log.info("INFO "+processedLoanApplications);
		
		if(processedLoanApplications!=null && !processedLoanApplications.isEmpty()) {
			return ResponseEntity.ok(processedLoanApplications);
		}
		
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
		}
		
		
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> savePulledLoanApplications(@Valid @RequestBody List<TrackDTO> trackDTO){
		if(paymentTrackService.savePulledLoanApplications(trackDTO)) {
			return ResponseEntity.status(HttpStatus.CREATED).build();
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();		}
		
	}
	
	@GetMapping("/viewall")
	public ResponseEntity<?> getAllApplications(){
		List<DisplayDTO> allApplications=paymentTrackService.viewToUpdateApplications();
		return ResponseEntity.ok(allApplications);
		
	}
	
	@PutMapping("/update/{loanAppId}")
	public ResponseEntity<?> updateStatus(@Valid @PathVariable String loanAppId,@Valid  @RequestBody StatusDTO statusDTO){
		boolean updated=paymentTrackService.updateStatus(loanAppId, statusDTO);
		
		if(updated) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).build();
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
	}
	
	
	
	@GetMapping("/defaulters")
	public ResponseEntity<?> getDefaulters(){
		List<TrackDTO> defaulters=paymentTrackService.getListOfDefaulters();
		return ResponseEntity.ok(defaulters);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserDTO userRequest){
		UserDTO user = userService.logIn(userRequest.getUserName(), userRequest.getPassword());
		if(user.getUserName().isEmpty()) {
			return new ResponseEntity<>(user,HttpStatus.BAD_REQUEST);
		}
		else {
			return new ResponseEntity<>(user,HttpStatus.OK);
		}
	}

}
