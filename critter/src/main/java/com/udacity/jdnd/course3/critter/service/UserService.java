package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.domain.enums.EmployeeSkill;
import com.udacity.jdnd.course3.critter.exceptions.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.exceptions.EmployeeNotFoundException;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.domain.entity.Customer;
import com.udacity.jdnd.course3.critter.domain.entity.Employee;
import com.udacity.jdnd.course3.critter.domain.dto.EmployeeRequestDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserService {

    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;

    public UserService(CustomerRepository customerRepository, EmployeeRepository employeeRepository) {
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
    }

    public Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployee(long employeeId) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        return optionalEmployee.orElseThrow(EmployeeNotFoundException::new);
    }

    public Customer getCustomer(long customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        return optionalCustomer.orElseThrow(CustomerNotFoundException::new);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        Employee employee = optionalEmployee.orElseThrow(EmployeeNotFoundException::new);
        employee.setDaysAvailable(daysAvailable);
        employeeRepository.save(employee);
    }

    public List<Employee> getEmployeesByDaysAvailable(DayOfWeek day)
    {
        return employeeRepository.findAllByDaysAvailableContaining(day);
    }

    public List<Employee> getEmployeesWithoutSkill(EmployeeSkill skill)
    {
        return employeeRepository.findEmployeesWithoutSkill(skill);
    }
}
