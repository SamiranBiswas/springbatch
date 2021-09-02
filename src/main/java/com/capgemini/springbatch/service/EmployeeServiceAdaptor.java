package com.capgemini.springbatch.service;

import com.capgemini.springbatch.model.Employee;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ComponentScan
public class EmployeeServiceAdaptor implements InitializingBean {

    public List<Employee> employeeList;

    public EmployeeServiceAdaptor() {
    }

    public Employee nextEmployee() {
        if (employeeList.size() > 0) {
            return employeeList.remove(0);
        } else return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
      //  employeeList = employeeService.getEmployeeList();
    }

}
