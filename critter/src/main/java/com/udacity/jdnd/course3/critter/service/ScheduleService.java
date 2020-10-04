package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.domain.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.domain.entity.Customer;
import com.udacity.jdnd.course3.critter.domain.entity.Employee;
import com.udacity.jdnd.course3.critter.domain.entity.Pet;
import com.udacity.jdnd.course3.critter.domain.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ScheduleService {

    private final PetService petService;
    private final UserService userService;
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(PetService petService, UserService userService, ScheduleRepository scheduleRepository) {
        this.petService = petService;
        this.userService = userService;
        this.scheduleRepository = scheduleRepository;
    }

    public Schedule convertScheduleDTOtoSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        List<Employee> employees = new ArrayList<>();
        List<Pet> pets = new ArrayList<>();
        List<Customer> customers = new ArrayList<>();

        for (Long employeeId : scheduleDTO.getEmployeeIds()) {
            employees.add(userService.getEmployee(employeeId));
        }

        Pet petToAdd;
        for (Long petId : scheduleDTO.getPetIds()) {
            petToAdd = petService.getPet(petId);
            pets.add(petToAdd);
            customers.add(petToAdd.getCustomer());
        }

        schedule.setEmployees(employees);
        schedule.setPets(pets);
        schedule.setCustomers(customers);

        return schedule;
    }


    public Schedule createSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);
        List<Long> employeeIds = new ArrayList<>();
        List<Long> petIds = new ArrayList<>();

        for(Employee employee : schedule.getEmployees())
            employeeIds.add(employee.getId());
        for(Pet pet : schedule.getPets())
            petIds.add(pet.getId());

        scheduleDTO.setEmployeeIds(employeeIds);
        scheduleDTO.setPetIds(petIds);
        return scheduleDTO;
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<ScheduleDTO> convertSchedulesToSchedulesDTO(List<Schedule> schedules) {
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        for (Schedule schedule : schedules) {
            scheduleDTOs.add(convertScheduleToScheduleDTO(schedule));
        }
        return scheduleDTOs;
    }

    public List<Schedule> getScheduleForPet(long petId) {
        Pet pet = petService.getPet(petId);
        return scheduleRepository.findAllByPetsContaining(pet);
    }

    public List<Schedule> getScheduleForEmployee(long employeeId) {
        Employee employee = userService.getEmployee(employeeId);
        return scheduleRepository.findAllByEmployeesContaining(employee);
    }

    public List<Schedule> getScheduleForCustomer(long customerId) {
        Customer customer = userService.getCustomer(customerId);
        return scheduleRepository.findAllByCustomersContaining(customer);
    }
}
