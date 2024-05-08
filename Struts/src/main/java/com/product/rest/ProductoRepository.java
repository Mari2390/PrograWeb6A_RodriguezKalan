package com.product.rest;

import java.util.HashMap;
import java.util.Map;

public class ProductoRepository {
  private static Map<String, Producto> map = new HashMap<String, Producto>();

  public ProductoRepository() { }

  public Producto getProductoById(String id) {
    return map.get(id);
  }

  public Map<String, Producto> findAllProducto() {
    return map;
  }

  public void addProducto(Producto producto) {
    map.put(String.valueOf(producto.getId()), producto);
  }

  public void updateProducto(Producto e) {
    map.put(String.valueOf(e.getId()), e);
    System.out.println(map);
  }

  public void deleteProducto(String id) {
    map.remove(id);
  }
}