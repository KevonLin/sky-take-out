package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("admin/employee")
@Slf4j
@Api(tags = "员工相关接口")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /*
     * @param employeeLoginDTO
     * @return com.sky.result.Result<com.sky.vo.EmployeeLoginVO>
     * @author kevonlin
     * @create 2023/11/28 14:31
     * @description 登录功能
     **/
    @PostMapping("login")
    @ApiOperation("员工登录")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);
        return employeeService.login(employeeLoginDTO);
    }


    /*
     * @param
     * @return com.sky.result.Result<java.lang.String>
     * @author kevonlin
     * @create 2023/11/28 14:31
     * @description 退出登录
     **/
    @PostMapping("logout")
    @ApiOperation("退出登录 ")
    public Result<String> logout() {
        return Result.success();
    }

    @PostMapping
    @ApiOperation("添加员工")
    public Result<String> addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        log.info("添加员工，参数为:{}", employeeDTO);
        return employeeService.addEmployee(employeeDTO);
    }

    @GetMapping("page")
    @ApiOperation("查询员工")
    public Result<PageResult> getPage(EmployeePageQueryDTO employeePageQueryDTO) {
        log.info("员工分页查询，参数为:{}", employeePageQueryDTO);
        return employeeService.getPage(employeePageQueryDTO);
    }

    @PostMapping("status/{status}")
    @ApiOperation("修改员工状态")
    public Result<String> toggleStatus(@PathVariable Integer status, @RequestParam Integer id) {
        log.info("修改员工账号状态，参数:{}", status);
        return employeeService.toggleStatus(status, id);
    }
}
