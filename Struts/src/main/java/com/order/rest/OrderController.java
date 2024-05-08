package com.order.rest;

import java.util.Map;

import org.apache.struts2.dispatcher.Parameter;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.employee.rest.Employee;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class OrderController implements ModelDriven<Object> {
    private static final long serialVersionUID = 1L;
    private String id;

    private Object model;
    private OrderRepository OrderRepository = new OrderRepository();
    private Map<String, Order> map;
    {
        map = OrderRepository.findAllOrder();
    }

    public HttpHeaders index() {
        model = map;
        return new DefaultHttpHeaders("index");
    }

    public HttpHeaders show() {
        model = OrderRepository.getOrderById(id);
        return new DefaultHttpHeaders("show");
    }

    public HttpHeaders create() {
        Map parameters = ActionContext.getContext().getParameters();

        int id = Integer.parseInt(parameters.get("id").toString());
        String listPro = parameters.get("listPro").toString();
        String idOrden = parameters.get("idOrden").toString();

        Order order = new Order(id, listPro, idOrden);

       OrderRepository.addOrder(order);
        
        model = order;

        return new DefaultHttpHeaders("create").disableCaching();
    }

    public HttpHeaders update() {
        Map<String, Parameter> parameters = ActionContext.getContext().getParameters();

        String listPro = parameters.get("listPro").getValue(); // Get the parameter value
        String idOrden = parameters.get("idOrden").getValue(); // Get the parameter value

        // Obtener el objeto Employee actual y actualizar sus datos
        Order order = (Order) getModel();
        order.setListPro(listPro);
        order.setIdOrden(idOrden);

        // Actualizar el empleado en el repositorio
        OrderRepository.updateOrder(order);

        model = order;

        System.out.println(model);

        return new DefaultHttpHeaders("update").disableCaching();
    }

    public HttpHeaders destroy() {
        OrderRepository.deleteOrder(id);
        return new DefaultHttpHeaders("destroy").disableCaching();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        model = OrderRepository.getOrderById(id);
        this.id = id;
    }

    @Override
    public Object getModel() {
        return model;
    }
}
