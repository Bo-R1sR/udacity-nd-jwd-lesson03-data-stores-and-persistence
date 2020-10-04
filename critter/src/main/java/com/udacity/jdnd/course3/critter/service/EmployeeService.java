package com.udacity.jdnd.course3.critter.service;


import com.udacity.jdnd.course3.critter.domain.entity.Employee;
import com.udacity.jdnd.course3.critter.domain.dto.EmployeeDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class EmployeeService {
    public EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }

    public Employee convertEmployeeDTOtoEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }

    public List<EmployeeDTO> convertEmployeesToEmployeesDTO(List<Employee> employees) {
        List<EmployeeDTO> employeesDTO = new ArrayList<>();
        EmployeeDTO employeeDTO;
        for (Employee employee : employees) {
            employeeDTO = convertEmployeeToEmployeeDTO(employee);
            employeesDTO.add(employeeDTO);
        }
        return employeesDTO;
    }
}
