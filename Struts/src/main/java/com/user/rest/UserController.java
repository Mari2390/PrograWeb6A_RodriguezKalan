package com.user.rest;

import java.util.Map;

import org.apache.struts2.dispatcher.Parameter;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class UserController implements ModelDriven<Object> {
    private static final long serialVersionUID = 1L;
    private String id;

    private Object model;
    private UserRepository userRepository = new UserRepository();

    public HttpHeaders index() {
        model = userRepository.findAllUser();
        return new DefaultHttpHeaders("index");
    }

    public HttpHeaders show() {
        model = userRepository.getUserById(id);
        return new DefaultHttpHeaders("show");
    }

    public HttpHeaders create() {
        Map parameters = ActionContext.getContext().getParameters();

        int id = Integer.parseInt(parameters.get("id").toString());
        String fistname = parameters.get("firstname").toString();
        String lastname = parameters.get("lastname").toString();
        String email = parameters.get("email").toString();

        User user = new User(id, fistname, lastname, email);
        userRepository.addUser(user);
        
        model = user;

        return new DefaultHttpHeaders("create").disableCaching();
    }

    public HttpHeaders update() {
        Map<String, Parameter> parameters = ActionContext.getContext().getParameters();

        String newFirstname = parameters.get("firstname").getValue();
        String newLastname = parameters.get("lastname").getValue();
        String newEmail = parameters.get("email").getValue();

       
        User user = (User) getModel();
        user.setFirstname(newFirstname);
        user.setLastname(newLastname);
        user.setEmail(newEmail);

        userRepository.updateUser(user);

        model = user;

        return new DefaultHttpHeaders("update").disableCaching();
    }

    public HttpHeaders destroy() {
        userRepository.deleteUser(id);
        return new DefaultHttpHeaders("destroy").disableCaching();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        model = userRepository.getUserById(id);
    }

    @Override
    public Object getModel() {
        return model;
    }
}
