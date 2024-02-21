package com.example.es.service;

import com.example.es.domain.Product;
import com.example.es.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService{
    @Autowired
    private ProductRepository productRepository;

    @Override
    public void save(Product p) {
        productRepository.save(p);
    }

    @Override
    public void delete(String id) {
        productRepository.deleteById(id);
    }

    @Override
    public void update(Product p) {
        productRepository.save(p);
    }

    @Override
    public Product get(String id) {
        Optional<Product> optional = productRepository.findById(id);
        Product product = optional.get();
        return product;
    }

    @Override
    public List<Product> list() {
        Iterable<Product> it = productRepository.findAll();
        List<Product> list = new ArrayList<>();
        it.forEach(item->list.add(item));
        return list;
    }
}
