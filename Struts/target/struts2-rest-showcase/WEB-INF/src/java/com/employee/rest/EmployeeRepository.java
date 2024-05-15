package com.employee.rest;

import java.util.HashMap;
import java.util.Map;

public class EmployeeRepository {
  private static Map<String, Employee> map = new HashMap<String, Employee>();

  public EmployeeRepository() { }

  public Employee getEmployeeById(String id) {
    return map.get(id);
  }

  public Map<String, Employee> findAllEmployee() {
    return map;
  }

  public void addEmployee(Employee employee) {
    map.put(String.valueOf(employee.getId()), employee);
  }

  public void updateEmployee(Employee e) {
    map.put(String.valueOf(e.getId()), e);
    System.out.println(map);
  }

  public void deleteEmployee(Employee e) {
    System.out.println(e);
    // map.remove(String.valueOf(e.getId()));
  }
}