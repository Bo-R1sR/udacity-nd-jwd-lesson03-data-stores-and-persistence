package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.domain.entity.Customer;
import com.udacity.jdnd.course3.critter.domain.dto.CustomerDTO;
import com.udacity.jdnd.course3.critter.domain.entity.Pet;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CustomerService {
    public CustomerDTO convertCustomerToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        if(customer.getPets() != null) {
            customerDTO.setPetIds(new ArrayList<>());
            for (Pet pet : customer.getPets()) {
                customerDTO.getPetIds().add(pet.getId());
            }
        }
        return customerDTO;
    }

    public Customer convertCustomerDTOtoCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }

    public List<CustomerDTO> convertCustomersToCustomersDTO(List<Customer> customers){
        List<CustomerDTO> customersDTO = new ArrayList<>();
        CustomerDTO customerDTO;
        for(Customer customer : customers) {
            customerDTO = convertCustomerToCustomerDTO(customer);
            customersDTO.add(customerDTO);
        }
        return customersDTO;
    }

}
