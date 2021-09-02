package com.capgemini.springbatch.service;

import com.capgemini.springbatch.model.Employee;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService  {
    private int nextStudentIndex=0;
    public Employee getEmployeeList(){
        Employee emp;
        List<Employee> list = new ArrayList();
        RestTemplate restTemplate = new RestTemplate();
        Employee[] forObject = restTemplate
                .getForObject("http://localhost:8081/list",Employee[].class);
        for (Employee employee : forObject) {
            list.add(employee);
        }
        if (nextStudentIndex<list.size()){
            emp = list.get(nextStudentIndex);
            nextStudentIndex++;
        }else {
            nextStudentIndex = 0;
            emp = null;
        } return emp;
    }

}
