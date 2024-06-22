package com.project.entities;

import java.time.LocalDate;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="payment_track")
public class PaymentTrack {
	@Id
	@GeneratedValue(generator = "custom-id")
	@GenericGenerator(name = "custom-id", strategy = "com.project.util.CustomIdGenerator")
	@Column(name="payment_track_id")
	private String paymentTrackId;
	@Column(name="loan_app_id", unique=true)
	private String loanAppId;
	@Column(name="month_no")
	private int monthNo;
	@Column(name="status")
	private String status;
	@Column(name="due_date_of_payment")
	private LocalDate dueDateOfPayment;
	@Column(name="payment_recieved_date")
	private LocalDate paymentRecievedDate;
}
