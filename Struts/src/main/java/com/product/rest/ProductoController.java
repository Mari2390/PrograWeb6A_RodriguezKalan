package com.product.rest;

import java.util.Map;

import org.apache.struts2.dispatcher.Parameter;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;


public class ProductoController implements ModelDriven<Object> {
    private static final long serialVersionUID = 1L;
    private String id;

    private Object model;
    private ProductoRepository productoRepository = new ProductoRepository();
    private Map<String, Producto> map;
    {
        map = productoRepository.findAllProducto();
    }

    public HttpHeaders index() {
        model = productoRepository.findAllProducto();
        return new DefaultHttpHeaders("index");
    }

    public HttpHeaders show() {
        model = productoRepository.getProductoById(id);
        return new DefaultHttpHeaders("show");
    }

    public HttpHeaders create() {
        Map parameters = ActionContext.getContext().getParameters();

        int id = Integer.parseInt(parameters.get("id").toString());
        String name = parameters.get("name").toString();
        String category = parameters.get("category").toString();
        String price = parameters.get("price").toString();

        Producto producto = new Producto(id, name, category, price);
        productoRepository.addProducto(producto);
        
        model = producto;;

        return new DefaultHttpHeaders("create").disableCaching();
    }

    public HttpHeaders update() {
        Map<String, Parameter> parameters = ActionContext.getContext().getParameters();

        String newname = parameters.get("name").getValue();
        String newCategory = parameters.get("category").getValue();
        String newprice = parameters.get("price").getValue();

        // Obtener el objeto Product actual y actualizar sus datos
        Producto producto = (Producto) getModel();
        producto.setName(newname);
        producto.setCategory(newCategory);
        producto.setPrice(newprice);

        productoRepository.updateProducto(producto);

        model = producto;

        return new DefaultHttpHeaders("update").disableCaching();
    }

    public HttpHeaders destroy() {
        productoRepository.deleteProducto(id);
        return new DefaultHttpHeaders("destroy").disableCaching();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        model = productoRepository.getProductoById(id);
    }

    @Override
    public Object getModel() {
        return model;
    }
}