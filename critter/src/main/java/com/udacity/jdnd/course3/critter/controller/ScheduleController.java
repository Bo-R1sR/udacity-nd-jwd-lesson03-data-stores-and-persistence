package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.domain.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.domain.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = scheduleService.convertScheduleDTOtoSchedule(scheduleDTO);
        Schedule savedSchedule = scheduleService.createSchedule(schedule);
        ScheduleDTO returnScheduleDTO = scheduleService.convertScheduleToScheduleDTO(savedSchedule);
        return returnScheduleDTO;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        List<ScheduleDTO> schedulesDTO = scheduleService.convertSchedulesToSchedulesDTO(schedules);
        return schedulesDTO;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schedules = scheduleService.getScheduleForPet(petId);
        List<ScheduleDTO> schedulesDTO = scheduleService.convertSchedulesToSchedulesDTO(schedules);
        return schedulesDTO;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> schedules = scheduleService.getScheduleForEmployee(employeeId);
        List<ScheduleDTO> schedulesDTO = scheduleService.convertSchedulesToSchedulesDTO(schedules);
        return schedulesDTO;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> schedules = scheduleService.getScheduleForCustomer(customerId);
        List<ScheduleDTO> schedulesDTO = scheduleService.convertSchedulesToSchedulesDTO(schedules);
        return schedulesDTO;
    }
}
