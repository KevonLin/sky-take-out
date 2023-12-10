package com.sky.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
* @author kevonlin
* @description 针对表【employee(员工信息)】的数据库操作Mapper
* @createDate 2023-11-28 11:25:27
* @Entity com.sky.entity.Employee
*/
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

    /*
     * @param employeeIPage
     * @param employeePageQueryDTO
     * @return com.baomidou.mybatisplus.core.metadata.IPage<java.util.Map>
     * @author kevonlin
     * @create 2023/11/28 17:31
     * @description 分页查询员工信息
     **/
//    IPage<Map> selectPageMap(IPage<Employee> employeeIPage, @Param("employeePageQueryDTO") EmployeePageQueryDTO employeePageQueryDTO);
}




