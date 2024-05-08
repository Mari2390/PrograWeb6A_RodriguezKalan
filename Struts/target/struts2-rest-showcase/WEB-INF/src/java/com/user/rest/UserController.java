package com.user.rest;

import java.util.Map;

import org.apache.struts2.dispatcher.Parameter;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.employee.rest.Employee;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class UserController {
    private static final long serialVersionUID = 1L;
    private String id;

    private Object model;
    private UserRepository UserRepository = new UserRepository();
    private Map<String, User> map;
    {
        map = UserRepository.findAllUser();
    }

    public HttpHeaders index() {
        model = map;
        return new DefaultHttpHeaders("index");
    }

    public HttpHeaders show() {
        model = UserRepository.getUserById(id);
        return new DefaultHttpHeaders("show");
    }

    public HttpHeaders create() {
        Map parameters = ActionContext.getContext().getParameters();

        int id = Integer.parseInt(parameters.get("id").toString());
        String firstname = parameters.get("firstname").toString();
        String lastname = parameters.get("lastname").toString();
        String email = parameters.get("email").toString();

        Employee employee = new Employee(id, name, user);

        UserRepository.addUser(employee);
        
        model = employee;

        return new DefaultHttpHeaders("create").disableCaching();
    }

    public HttpHeaders update() {
        Map<String, Parameter> parameters = ActionContext.getContext().getParameters();

        String name = parameters.get("name").getValue(); // Get the parameter value
        String company = parameters.get("company").getValue(); // Get the parameter value

        // Obtener el objeto Employee actual y actualizar sus datos
        User user = (User) getModel();
        user.setName(name);
        user.setCompany(company);

        // Actualizar el empleado en el repositorio
        UserRepository.updateUser(employee);

        model = employee;

        System.out.println(model);

        return new DefaultHttpHeaders("update").disableCaching();
    }

    public HttpHeaders destroy() {
        UserRepository.deleteUser(id);
        return new DefaultHttpHeaders("destroy").disableCaching();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        model = UserRepository.getUserById(id);
        this.id = id;
    }

    @Override
    public Object getModel() {
        return model;
    }
}