package com.iase24.crazy_task_tracker_api.businessapi.controller.graph;

import com.iase24.crazy_task_tracker_api.businessapi.dto.graph.EmployeeInput;
import com.iase24.crazy_task_tracker_api.businessapi.repository.graph.DepartmentRepository;
import com.iase24.crazy_task_tracker_api.businessapi.repository.graph.EmployeeRepository;
import com.iase24.crazy_task_tracker_api.businessapi.repository.graph.OrganizationRepository;
import com.iase24.crazy_task_tracker_api.entity.Department;
import com.iase24.crazy_task_tracker_api.entity.Employee;
import com.iase24.crazy_task_tracker_api.entity.Organization;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final OrganizationRepository organizationRepository;
    private final DepartmentRepository departmentRepository;

    @QueryMapping
    public Iterable<Employee> employees() {
        return employeeRepository.findAll();
    }

    @QueryMapping
    public Employee employee(@Argument Integer id) {
        return employeeRepository.findById(id).orElseThrow();
    }

    @MutationMapping
    public Employee newEmployee(@Argument EmployeeInput employee) {
        Department department = departmentRepository.findById(employee.getDepartmentId()).get();
        Organization organization = organizationRepository.findById(employee.getOrganizationId()).get();

        return employeeRepository.save(new Employee(
                null, employee.getFirstName(), employee.getLastName(), employee.getPosition(), employee.getAge(),
                employee.getSalary(), department, organization
        ));
    }
}
