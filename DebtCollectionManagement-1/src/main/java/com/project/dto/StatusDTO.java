package com.project.dto;



import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
@Data
public class StatusDTO {
	@NotBlank
	@Pattern(regexp ="^(Recieved|Not-Recieved)$", message="Updation status is incorrect")
	private String status;
	private LocalDate paymentRecievedDate;
}
