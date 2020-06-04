package com.atguigu.springboot.mapper;

import com.atguigu.springboot.bean.Employee;

// @Mapper或者@MapperScan将这个接口扫描到容器中
public interface EmployeeMapper {

    public Employee getEmp(Integer id);

    public void insertEmp(Employee employee);

}
