package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sky.result.PageResult;
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
    EmployeeLoginVO login(EmployeeLoginDTO employeeLoginDTO);

    /*
     * @param employeeDTO
     * @return com.sky.result.Result<java.lang.String>
     * @author kevonlin
     * @create 2023/11/28 14:36
     * @description 添加员工
     **/
    void addEmployee(EmployeeDTO employeeDTO);

    /*
     * @param employeePageQueryDTO
     * @return com.sky.result.Result<java.lang.String>
     * @author kevonlin
     * @create 2023/11/28 16:43
     * @description 分页查询员工
     **/
    PageResult getPage(EmployeePageQueryDTO employeePageQueryDTO);

    /*
     * @param status
     * @return com.sky.result.Result<java.lang.String>
     * @author kevonlin
     * @create 2023/11/28 18:12
     * @description 修改员工账号状态
     **/
    void toggleStatus(Integer status, Integer id);
}
