package com.data.serviceImpl;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.data.dto.EmployeeDTO;
import com.data.dto.LoginDTO;
import com.data.entity.Employee;
import com.data.payload.response.LoginMesage.LoginMesage;
import com.data.repo.EmployeeRepo;
import com.data.services.EmployeeService;
import java.util.Optional;
@Service
public class EmployeeIMPL implements EmployeeService {
    @Autowired
    private EmployeeRepo employeeRepo;
    @Override
    public String addEmployee(EmployeeDTO employeeDTO) {
        // Check if an employee with the given email already exists
        Employee existingEmployee = employeeRepo.findByEmail(employeeDTO.getEmail());
        if (existingEmployee != null) {
            throw new ServiceException("Duplicate email: " + employeeDTO.getEmail());
        }

        // Create a new Employee entity from the DTO
        Employee newEmployee = new Employee(
                employeeDTO.getEmployeeid(),
                employeeDTO.getEmployeename(),
                employeeDTO.getEmail(),
                employeeDTO.getPassword());

        // Save the new employee to the repository
        employeeRepo.save(newEmployee);

        // Return the name of the newly added employee
        return newEmployee.getEmployeename();
    }

    EmployeeDTO employeeDTO;
    @Override
    public LoginMesage  loginEmployee(LoginDTO loginDTO) {
        String msg = "";
        Employee employee1 = employeeRepo.findByEmailAndPassword(loginDTO.getEmail() , loginDTO.getPassword());
        if (employee1 != null) {
            String encodedPassword = employee1.getPassword();
           Optional<Employee> employee = employeeRepo.findOneByEmailAndPassword(loginDTO.getEmail(), encodedPassword);
                if (employee.isPresent()) {
                    return new LoginMesage("Login Success", true);
                } else {
                    return new LoginMesage("password Not Match", false);
                }
            } else {
                return new LoginMesage("User id invalid", false);
            }
        
    }
}