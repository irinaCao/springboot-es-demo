package com.example.es.service;


import com.example.es.domain.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ProductServiceImplTest {
    @Autowired
    private IProductService productService;

    @Test
    public void save() {
        Product product = new Product();
        product.setId("1");
        product.setIntro("狼码手机介绍");
        product.setBrand("叩丁狼");
        product.setTitle("狼码手机标题");
        product.setPrice(1000);
        productService.save(product);
    }

    @Test
    public void delete() {

    }

    @Test
    public void update() {
        Product product = new Product();
        product.setId("1");
        product.setIntro("狼码手机介绍");
        product.setBrand("叩丁狼");
        product.setTitle("狼码手机标题");
        product.setPrice(2000);
        productService.update(product);
    }

    @Test
    public void getById() {
        Product product = productService.get("1");
        System.out.println(product.toString());
    }

    @Test
    public void listAll() {
        List<Product> list = productService.list();
        System.out.println(list.toString());
    }
}