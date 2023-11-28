package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.result.Result;
import com.sky.vo.EmployeeLoginVO;

/**
* @author kevonlin
* @description 针对表【employee(员工信息)】的数据库操作Service
* @createDate 2023-11-28 11:25:27
*/
public interface EmployeeService extends IService<Employee> {

    /*
     * @param employeeLoginDTO
     * @return com.sky.result.Result<com.sky.vo.EmployeeLoginVO>
     * @author kevonlin
     * @create 2023/11/28 14:36
     * @description 登录功能
     **/
    Result<EmployeeLoginVO> login(EmployeeLoginDTO employeeLoginDTO);

    /*
     * @param employeeDTO
     * @return com.sky.result.Result<java.lang.String>
     * @author kevonlin
     * @create 2023/11/28 14:36
     * @description 添加员工
     **/
    Result<String> addEmployee(EmployeeDTO employeeDTO);
}
