package com.data.controller;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.data.dto.EmployeeDTO;
import com.data.dto.LoginDTO;
import com.data.payload.response.LoginMesage.LoginMesage;
import com.data.services.EmployeeService;

@RestController
@CrossOrigin
@RequestMapping("api/v1/employee")
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

	@PostMapping(path = "/save")
	public ResponseEntity<String> saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
		try {
			String id = employeeService.addEmployee(employeeDTO);
			return ResponseEntity.ok(id);
		} catch (ServiceException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PostMapping(path = "/login")
	public ResponseEntity<?> loginEmployee(@RequestBody LoginDTO loginDTO) {
		try {
			LoginMesage loginResponse = employeeService.loginEmployee(loginDTO);
			return ResponseEntity.ok(loginResponse);
		} catch (ServiceException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
}
