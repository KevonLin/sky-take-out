package com.sky.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.constant.JwtClaimsConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.mapper.EmployeeMapper;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kevonlin
 * @description 针对表【employee(员工信息)】的数据库操作Service实现
 * @createDate 2023-11-28 11:25:27
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>
        implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public Result<EmployeeLoginVO> login(EmployeeLoginDTO employeeLoginDTO) {
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Employee::getUsername, employeeLoginDTO.getUsername());
        Employee employee = employeeMapper.selectOne(wrapper);
        if (DigestUtils.md5DigestAsHex(employeeLoginDTO.getPassword().getBytes()).equals(employee.getPassword())) {
            //登录成功后，生成jwt令牌
            Map<String, Object> claims = new HashMap<>();
            claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
            String token = JwtUtil.createJWT(
                    jwtProperties.getAdminSecretKey(),
                    jwtProperties.getAdminTtl(),
                    claims);

            EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                    .id(employee.getId())
                    .userName(employee.getUsername())
                    .name(employee.getName())
                    .token(token)
                    .build();

            return Result.success(employeeLoginVO);
        }
        return Result.error("登陆失败");
    }

    @Override
    public Result addEmployee(EmployeeDTO employeeDTO) {
        // 判断用户是否重复 username唯一 全局异常处理器可以处理
//        String username = employeeDTO.getUsername();
//        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(Employee::getUsername, username);
//        Long count = employeeMapper.selectCount(wrapper);
//        if (count > 0) {
//            return Result.error();
//        }
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        employee.setStatus(StatusConstant.ENABLE);
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        // 设置正确的用户 每个请求都是单独的线程 可以使用ThreadLocal存储token中的empId
        employee.setCreateUser(BaseContext.getCurrentId());
        employee.setUpdateUser(BaseContext.getCurrentId());
        employeeMapper.insert(employee);
        return Result.success();
    }

    @Override
    public Result<PageResult> getPage(EmployeePageQueryDTO employeePageQueryDTO) {
        String name = employeePageQueryDTO.getName();
        int pageNum = employeePageQueryDTO.getPage();
        int pageSize = employeePageQueryDTO.getPageSize();

        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(!StringUtils.isEmpty(name), Employee::getName, name);

        IPage<Employee> employeeIPage = new Page<>(pageNum, pageSize);
        IPage<Map> employeeIPageMap = employeeMapper.selectPageMap(employeeIPage, employeePageQueryDTO);

        PageResult pageResult = new PageResult();
        pageResult.setTotal(employeeIPageMap.getTotal());
        pageResult.setRecords(employeeIPageMap.getRecords());
        return Result.success(pageResult);
    }

    @Override
    public Result toggleStatus(Integer status, Integer id) {
        Employee employee = employeeMapper.selectById(id);
        employee.setStatus(status);
        employee.setUpdateUser(BaseContext.getCurrentId());
        employee.setUpdateTime(LocalDateTime.now());
        employeeMapper.updateById(employee);
        return Result.success();
    }

}




