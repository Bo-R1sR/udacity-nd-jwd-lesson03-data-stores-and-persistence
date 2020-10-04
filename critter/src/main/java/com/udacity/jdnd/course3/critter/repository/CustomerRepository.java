package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    //List<Customer> findByPetId(Long petId);
}
