package com.capgemini.springbatch.service;

import com.capgemini.springbatch.model.Employee;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService  {
    public List<Employee> getEmployeeList(){
        List list = new ArrayList<Employee>();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity httpEntity = new HttpEntity(headers);
        ResponseEntity<Employee[]> forObject = restTemplate
                .exchange("http://localhost:8081/list",HttpMethod.GET,httpEntity,Employee[].class);
        for (Employee employee : forObject.getBody()) {
            list.add(employee);
        }
        return list;
    }

}
