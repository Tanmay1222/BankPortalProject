package com.project.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.project.dto.TrackDTO;
@Component
public class GenerateMockData {
	public  List<TrackDTO> generateMockData(){
		List<TrackDTO> mockData=new ArrayList<>();
		mockData.add(new TrackDTO("HL0001", 3, LocalDate.parse("2024-03-01"), null, "Home Loan", "Tanmay", "Shelke"));
		mockData.add(new TrackDTO("PL0002", 3, LocalDate.parse("2024-03-03"), LocalDate.parse("2024-03-12"), "Personal Loan", "Ajay", "Patil"));
		mockData.add(new TrackDTO("HL0003", 4, LocalDate.parse("2024-04-01"), LocalDate.parse("2024-04-06"),"Home Loan", "Akash", "Patil"));
		mockData.add(new TrackDTO("HL0012", 3, LocalDate.parse("2023-03-01"), LocalDate.parse("2023-03-30"),"Home Loan", "Mayur", "Gosavi"));
		mockData.add(new TrackDTO("CL0004", 5, LocalDate.parse("2023-05-01"), LocalDate.parse("2023-05-30"),"Car Loan", "Shubham", "Awate"));
		mockData.add(new TrackDTO("CL0001", 3, LocalDate.parse("2024-03-01"), LocalDate.parse("2024-04-28"), "Car Loan", "Alice", "Smith"));
		mockData.add(new TrackDTO("CL0002", 3, LocalDate.parse("2024-03-02"), null, "Car Loan", "Bob", "Johnson"));
		mockData.add(new TrackDTO("CL0003", 5, LocalDate.parse("2023-05-03"), LocalDate.parse("2023-06-01"), "Car Loan", "Charlie", "Brown"));
		mockData.add(new TrackDTO("HL0066", 4, LocalDate.parse("2024-04-04"), LocalDate.parse("2024-04-22"), "Home Loan", "David", "Lee"));
		mockData.add(new TrackDTO("HL0033", 4, LocalDate.parse("2024-04-05"), LocalDate.parse("2024-04-24"), "Home Loan", "Eva", "Garcia"));
		mockData.add(new TrackDTO("PL0001", 3, LocalDate.parse("2024-03-06"), null, "Personal Loan", "Frank", "Miller"));
		mockData.add(new TrackDTO("PL0012", 5, LocalDate.parse("2023-05-07"), LocalDate.parse("2023-06-05"), "Personal Loan", "Grace", "Wang"));
		mockData.add(new TrackDTO("EL0001", 5, LocalDate.parse("2023-05-08"), null, "Education Loan", "Henry", "Chen"));
		mockData.add(new TrackDTO("EL0002", 5, LocalDate.parse("2023-05-09"), LocalDate.parse("2023-06-07"), "Education Loan", "Isabella", "Lopez"));
		
	
		
		return mockData;
	}

}
