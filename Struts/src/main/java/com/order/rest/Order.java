package com.order.rest;

public class Order {
    private Integer id;
    private String listPro;
    private String idOrden;

    public Order(Integer id, String listPro, String idOrden) {
        this.id = id;
        this.listPro = listPro;
        this.idOrden = idOrden;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getListPro() {
        return listPro;
    }

    public void setListPro(String listPro) {
        this.listPro = listPro;
    }

    public String getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(String idOrden) {
        this.idOrden = idOrden;
    }

    @Override
    public String toString() {
        return "ser [id=" + id + ", Lista de productos incluidos=" + listPro + ", ID del usuario que hizo la orden=" + idOrden +"]";
    }
}
