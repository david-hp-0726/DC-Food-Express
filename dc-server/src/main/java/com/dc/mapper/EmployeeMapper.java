package com.dc.mapper;

import com.dc.dto.EmployeeDTO;
import com.dc.dto.EmployeePageQueryDTO;
import com.dc.entity.Employee;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    void add(Employee employee);

    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    void activateOrDeactivate(Short status, Long id);

    Employee getById(Long id);

    void update(Employee employee);
}
