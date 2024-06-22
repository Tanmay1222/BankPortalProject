package com.project.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisplayDTO {
	@NotBlank
	private String loanAppId;
	@Positive
	private int monthNo;
	@NotBlank
	@Pattern(regexp ="^(Recieved|Not-Recieved)$", message="Updation status is incorrect")
	private String status;
	@PastOrPresent
	 private LocalDate dueDateOfPayment;
	@PastOrPresent
	private LocalDate paymentRecievedDate;

}
