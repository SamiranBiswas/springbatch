package com.capgemini.springbatch.RestController;

import com.capgemini.springbatch.model.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class EmployeeController {
    @GetMapping("/list")
    public List<Employee> employeeList(){
        return Arrays.asList(new Employee(1,"samiran","gmail"),
                new Employee(2,"sudip","hotmail"));
    }
}
