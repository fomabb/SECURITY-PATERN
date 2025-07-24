package com.iase24.crazy_task_tracker_api.businessapi.dto.graph;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeInput {

    private String firstName;
    private String lastName;
    private String position;
    private int salary;
    private int age;
    private Integer departmentId;
    private Integer organizationId;
}
