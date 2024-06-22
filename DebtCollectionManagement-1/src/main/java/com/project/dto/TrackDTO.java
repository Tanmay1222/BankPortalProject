package com.project.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrackDTO {
	@NotBlank
	private String loanAppId;
	@Positive
	private int monthNo;
	@PastOrPresent
	private LocalDate dueDateOfPayment;
	@PastOrPresent
	private LocalDate paymentRecievedDate;
	@NotBlank
	private String loanType;
	@NotBlank
	private String firstName;
	@NotBlank
	private String lastName;
}
