package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.domain.entity.Employee;
import com.udacity.jdnd.course3.critter.domain.enums.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findAllByDaysAvailableContaining(DayOfWeek day);

    @Query("select e from Employee e where :skill not member of e.skills")
    List<Employee> findEmployeesWithoutSkill(EmployeeSkill skill);

}
