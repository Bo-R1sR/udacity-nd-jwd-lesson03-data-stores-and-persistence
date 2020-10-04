package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.domain.entity.Customer;
import com.udacity.jdnd.course3.critter.domain.entity.Employee;
import com.udacity.jdnd.course3.critter.domain.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.domain.dto.EmployeeDTO;
import com.udacity.jdnd.course3.critter.domain.dto.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.domain.enums.EmployeeSkill;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final PetService petService;
    private final CustomerService customerService;
    private final EmployeeService employeeService;

    public UserController(UserService userService, PetService petService, CustomerService customerService, EmployeeService employeeService) {
        this.userService = userService;
        this.petService = petService;
        this.customerService = customerService;
        this.employeeService = employeeService;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = customerService.convertCustomerDTOtoCustomer(customerDTO);
        Customer savedCustomer = userService.saveCustomer(customer);
        CustomerDTO returnCustomerDTO = customerService.convertCustomerToCustomerDTO(savedCustomer);
        return returnCustomerDTO;
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeService.convertEmployeeDTOtoEmployee(employeeDTO);
        Employee savedEmployee = userService.saveEmployee(employee);
        EmployeeDTO returnEmployeeDTO = employeeService.convertEmployeeToEmployeeDTO(savedEmployee);
        return returnEmployeeDTO;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customers = userService.getAllCustomers();
        List<CustomerDTO> customersDTO = customerService.convertCustomersToCustomersDTO(customers);
        return customersDTO;
    }

    @GetMapping("/employee")
    public List<EmployeeDTO> getAllEmployees(){
        List<Employee> employees = userService.getAllEmployees();
        List<EmployeeDTO> employeesDTO = employeeService.convertEmployeesToEmployeesDTO(employees);
        return employeesDTO;
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = userService.getEmployee(employeeId);
        EmployeeDTO employeeDTO = employeeService.convertEmployeeToEmployeeDTO(employee);
        return employeeDTO;
    }

    @GetMapping("/customer/{customerId}")
    public CustomerDTO getCustomer(@PathVariable long customerId) {
        Customer customer = userService.getCustomer(customerId);
        CustomerDTO customerDTO = customerService.convertCustomerToCustomerDTO(customer);
        return customerDTO;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Customer customer = petService.getPet(petId).getCustomer();
        CustomerDTO customerDTO = customerService.convertCustomerToCustomerDTO(customer);
        return customerDTO;
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        userService.setAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        DayOfWeek day = employeeDTO.getDate().getDayOfWeek();
        Set<EmployeeSkill> skills = employeeDTO.getSkills();

        List<Employee> employees = userService.getEmployeesByDaysAvailable(day);
        List<Employee> removeList = new ArrayList<>();
        for(EmployeeSkill skill : skills)
        {
            removeList.addAll(userService.getEmployeesWithoutSkill(skill));
            employees.removeAll(removeList);
            removeList.clear();
        }

        List<EmployeeDTO> employeeDTOs = employeeService.convertEmployeesToEmployeesDTO(employees);

        return employeeDTOs;
    }



}
