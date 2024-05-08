package com.order.rest;

import java.util.HashMap;
import java.util.Map;

public class OrderRepository {
  private static Map<String, Order> map = new HashMap<String, Order>();

  public OrderRepository() { }

  public Order getOrderById(String id) {
    return map.get(id);
  }

  public Map<String, Order> findAllOrder() {
    return map;
  }

  public void addOrder(Order order) {
    map.put(String.valueOf(order.getId()), order);
  }

  public void updateOrder(Order e) {
    map.put(String.valueOf(e.getId()), e);
    System.out.println(map);
  }

  public void deleteOrder(String id) {
    map.remove(id);
  }
}