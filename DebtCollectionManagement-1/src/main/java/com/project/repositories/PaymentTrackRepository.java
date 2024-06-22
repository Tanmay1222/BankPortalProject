package com.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.entities.PaymentTrack;

@Repository
public interface PaymentTrackRepository extends JpaRepository<PaymentTrack, String> {

	PaymentTrack findByLoanAppId(String loanAppId);


	@Query(value = "SELECT * FROM PAYMENT_TRACK pt WHERE (DAY(pt.PAYMENT_RECIEVED_DATE)>15 AND MONTH(pt.PAYMENT_RECIEVED_DATE)=pt.MONTH_NO) OR "
			+ " (MONTH(pt.PAYMENT_RECIEVED_DATE)>MONTH(PT.DUE_DATE_OF_PAYMENT))", nativeQuery = true)
	List<PaymentTrack> findPaymentRecievedDateAfter();

}
