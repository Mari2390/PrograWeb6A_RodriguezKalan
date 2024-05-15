package com.employee.rest;

import java.util.Map;

import org.apache.struts2.dispatcher.Parameter;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class EmployeeController implements ModelDriven<Object> {
  private static final long serialVersionUID = 1L;
  private String id;

  private Object model;
  private EmployeeRepository employeeRepository = new EmployeeRepository();
  private Map<String, Employee> map;
  {
    map = employeeRepository.findAllEmployee();
  }

  public HttpHeaders index() {
    model = map;
    return new DefaultHttpHeaders("index");
  }

  public HttpHeaders show() {
    model = employeeRepository.getEmployeeById(id);
    return new DefaultHttpHeaders("show");
  }

  public HttpHeaders create() {
    Map parameters = ActionContext.getContext().getParameters();

    int id = Integer.parseInt(parameters.get("id").toString());
    String name = parameters.get("name").toString();
    String company = parameters.get("company").toString();

    Employee employee = new Employee(id, name, company);

    employeeRepository.addEmployee(employee);
    
    model = employee;

    return new DefaultHttpHeaders("create").disableCaching();
  }

  public HttpHeaders update() {
    Map<String, Parameter> parameters = ActionContext.getContext().getParameters();

    String name = parameters.get("name").getValue(); // Get the parameter value
    String company = parameters.get("company").getValue(); // Get the parameter value

    // Obtener el objeto Employee actual y actualizar sus datos
    Employee employee = (Employee) getModel();
    employee.setName(name);
    employee.setCompany(company);

    // Actualizar el empleado en el repositorio
    employeeRepository.updateEmployee(employee);

    model = employee;

    System.out.println(model);

    return new DefaultHttpHeaders("update").disableCaching();
  }

  public HttpHeaders destroy() {
    Employee employee = employeeRepository.getEmployeeById(id);
    System.out.println(id);
    employeeRepository.deleteEmployee(employee);
    return new DefaultHttpHeaders("destroy").disableCaching();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    model = employeeRepository.getEmployeeById(id);
    this.id = id;
  }

  @Override
  public Object getModel() {
    return model;
  }
}
