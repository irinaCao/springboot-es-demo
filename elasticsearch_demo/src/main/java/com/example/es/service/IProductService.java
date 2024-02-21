package com.example.es.service;


import com.example.es.domain.Product;

import java.util.List;

public interface IProductService {
    //定义增删改查方法
    void save(Product p);
    void delete(String id);
    void update(Product p);
    Product get(String id);
    List<Product> list();
}
