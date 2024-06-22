package com.project.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;

import com.project.debtCollectionManagement.DebtCollectionManagement1Application;
import com.project.entities.PaymentTrack;
import com.project.repositories.PaymentTrackRepository;
@DataJpaTest
@ContextConfiguration(classes = DebtCollectionManagement1Application.class)
class PaymentTrackRepositoryTest {
	
	@Autowired
	private PaymentTrackRepository paymentTrackRepository;

	@Autowired
	private TestEntityManager entityManager;
	@Test
	public void testSavePaymentTrackPositive() {
		PaymentTrack savedPaymentTrack=new PaymentTrack();
		
	
		savedPaymentTrack.setLoanAppId("LA002");
		
		savedPaymentTrack.setMonthNo(2);
		
		savedPaymentTrack.setStatus("Recieved");
		
		savedPaymentTrack.setDueDateOfPayment(LocalDate.parse("2024-04-11"));
		
		savedPaymentTrack.setPaymentRecievedDate(LocalDate.parse("2024-04-11"));
		paymentTrackRepository.save(savedPaymentTrack);
		
		Optional<PaymentTrack> payment=paymentTrackRepository.findById(savedPaymentTrack.getPaymentTrackId());
		System.out.println(savedPaymentTrack.getPaymentTrackId());
		assertTrue(payment.isPresent());
	}
	@Test
	public void testPaymentTrackDeletePositive() {
		PaymentTrack savedPaymentTrack=new PaymentTrack();
	
		savedPaymentTrack.setLoanAppId("LA002");
		
		savedPaymentTrack.setMonthNo(2);
		
		savedPaymentTrack.setStatus("Recieved");
		
		savedPaymentTrack.setDueDateOfPayment(LocalDate.parse("2024-04-11"));
		
		savedPaymentTrack.setPaymentRecievedDate(LocalDate.parse("2024-04-11"));
		entityManager.persist(savedPaymentTrack);
		paymentTrackRepository.delete(savedPaymentTrack);
		Optional<PaymentTrack> payment=paymentTrackRepository.findById(savedPaymentTrack.getPaymentTrackId());
		assertTrue(!payment.isPresent());
	}
	@Test
	public void testFindAllPositive() {
		PaymentTrack savedPaymentTrack=new PaymentTrack();
		
	
		savedPaymentTrack.setLoanAppId("LA002");
		
		savedPaymentTrack.setMonthNo(2);
		
		savedPaymentTrack.setStatus("Recieved");
		
		savedPaymentTrack.setDueDateOfPayment(LocalDate.parse("2024-04-11"));
		
		savedPaymentTrack.setPaymentRecievedDate(LocalDate.parse("2024-04-11"));
		entityManager.persist(savedPaymentTrack);
		
		Iterable<PaymentTrack> it=paymentTrackRepository.findAll();
		assertTrue(it.iterator().hasNext());
	}
	@Test
	public void testFindAllNegative() {
		Iterable<PaymentTrack> it=paymentTrackRepository.findAll();
		assertTrue(!it.iterator().hasNext());
	}
	
	@Test
	public void testFindByIdPositive() {
		PaymentTrack savedPaymentTrack=new PaymentTrack();
	
		savedPaymentTrack.setLoanAppId("LA002");
		
		savedPaymentTrack.setMonthNo(2);
		
		savedPaymentTrack.setStatus("Recieved");
		
		savedPaymentTrack.setDueDateOfPayment(LocalDate.parse("2024-04-11"));
		
		savedPaymentTrack.setPaymentRecievedDate(LocalDate.parse("2024-04-11"));
		entityManager.persist(savedPaymentTrack);
		Optional<PaymentTrack> payment=paymentTrackRepository.findById(savedPaymentTrack.getPaymentTrackId());
		assertTrue(payment.isPresent());
	}
	
	@Test
	public void testFindByIdNegative() {
		Optional<PaymentTrack> payment=paymentTrackRepository.findById("PT1001");
		assertTrue(!payment.isPresent());
	}

}
