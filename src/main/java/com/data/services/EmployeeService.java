package com.data.services;

import com.data.dto.EmployeeDTO;
import com.data.dto.LoginDTO;
import com.data.payload.response.LoginMesage.LoginMesage;

public interface EmployeeService {
    String addEmployee(EmployeeDTO employeeDTO);
    LoginMesage loginEmployee(LoginDTO loginDTO);
}