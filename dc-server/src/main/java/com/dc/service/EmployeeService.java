package com.dc.service;

import com.dc.dto.EmployeeDTO;
import com.dc.dto.EmployeeLoginDTO;
import com.dc.dto.EmployeePageQueryDTO;
import com.dc.entity.Employee;
import com.dc.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    void insert(EmployeeDTO employeeDTO);

    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    void activateOrDeactivate(Short status, Long id);

    Employee getById(Long id);

    void update(EmployeeDTO employeeDTO);
}
