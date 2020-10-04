package com.udacity.jdnd.course3.critter.domain.entity;

import com.udacity.jdnd.course3.critter.domain.enums.EmployeeSkill;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "schedule_employee", joinColumns = {@JoinColumn(name ="schedule_id")}, inverseJoinColumns = {@JoinColumn(name = "employee_id")})
    private List<Employee> employees;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "schedule_pet", joinColumns = {@JoinColumn(name ="schedule_id")}, inverseJoinColumns = {@JoinColumn(name = "pet_id")})
    private List<Pet> pets;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "schedule_customer", joinColumns = {@JoinColumn(name ="schedule_id")}, inverseJoinColumns = {@JoinColumn(name = "customer_id")})
    private List<Customer> customers;

    private LocalDate date;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> activities;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }
}
