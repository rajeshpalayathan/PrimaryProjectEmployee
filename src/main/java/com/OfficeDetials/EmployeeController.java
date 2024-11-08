package com.OfficeDetials;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping(value = "/OfficeDetials")
public class EmployeeController {

	@Autowired
	EmployeeRepository rep;

	@Autowired
	RestTemplate restTemplate;

	@PostMapping(value = "/postData")
	// curl=http://localhost:8999/OfficeDetials/postData
	public List<EmployeeEntity> addData(@RequestBody List<EmployeeEntity> list) {
		rep.saveAll(list);
		return list;
	}

	@GetMapping(value = "/getDataBase")
	// curl=http://localhost:8999/OfficeDetials/getDataBase
	public List<EmployeeEntity> getDataBase() {
		List<EmployeeEntity> list = rep.findAll();
		return list;
	}

	@CircuitBreaker(name = "GetMobileNumber", fallbackMethod = "MobileNumberFails")
	@GetMapping(value = "/getMobileNumber/{empId}")
	// curl=http://localhost:8999/OfficeDetials/getMobileNumber/{empId}
	public String getMobileNumber(@PathVariable String empId) {
		String ObjectUrl = "http://localhost:8289/PersonalDetials/getDataBase";

		ResponseEntity<List<PersonalEntityPOJO>> response = restTemplate.exchange(ObjectUrl, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<PersonalEntityPOJO>>() {
				});
		List<PersonalEntityPOJO> personalList = response.getBody();
		List<EmployeeEntity> emplist = rep.findAll();
		for (int i = 0; i < personalList.size(); i++) {
			if (personalList.get(i).getEmpId().equalsIgnoreCase(empId)) {
				if (emplist.get(i).getEmpId().equalsIgnoreCase(empId)) {
					return "Employee Name: " + emplist.get(i).getName() + ", Employee Mobile : "
							+ personalList.get(i).getMobile();
				}
			}
		}
		return "Employee ID is Wrong";
	}

	public String MobileNumberFails(String empId, Throwable throwable) {
		return "Please wait for few Minutes Your MobileNumber will be Shown";
	}

	@CircuitBreaker(name = "GetMobileNumber", fallbackMethod = "MobileNumFails")
	@GetMapping(value = "/getMobile/{empId}")
	// curl=http://localhost:8999/OfficeDetials/getMobile/{empId}
	public List<String> getMobileNumber1(@PathVariable String empId) {
		String ObjectUrl = "http://localhost:8289/PersonalDetials/getDataBase";

		ResponseEntity<List<PersonalEntityPOJO>> response = restTemplate.exchange(ObjectUrl, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<PersonalEntityPOJO>>() {
				});
		List<PersonalEntityPOJO> personalList = response.getBody();
		ArrayList<String> output=new ArrayList<>();
		for(int i=0; i<personalList.size(); i++)
		{
			String mobileNumber=personalList.get(i).getMobile();
			output.add(mobileNumber);
		
		
			}
		
		return output;
		}
		
	
	public List<String> MobileNumFails(String empId, Throwable throwable) {
List<String> temporaryResponse=Arrays.asList("Please wait for few Minutes Your MobileNumber will be Shown");
	
				
return temporaryResponse;
	}


}
