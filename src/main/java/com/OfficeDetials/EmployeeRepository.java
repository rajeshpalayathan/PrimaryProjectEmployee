package com.OfficeDetials;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {

}
