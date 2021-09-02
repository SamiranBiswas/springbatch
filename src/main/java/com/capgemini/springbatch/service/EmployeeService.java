package com.capgemini.springbatch.service;

import com.capgemini.springbatch.model.Employee;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService  {
    public List<Employee> getEmployeeList(){
        List list = new ArrayList<Employee>();
        RestTemplate restTemplate = new RestTemplate();
        Employee[] forObject = restTemplate.getForObject("http://localhost:8080/list", Employee[].class);
        for (Employee employee : forObject) {
            list.add(employee);
        }
        return list;
    }

}
