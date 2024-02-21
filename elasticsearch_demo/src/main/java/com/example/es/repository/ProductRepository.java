package com.example.es.repository;

import com.example.es.domain.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

//类比mybatisplus中的mapper接口
//<Product,String> 指定泛型,第一个是表对应的domain类,第二个是id参数类型
public interface ProductRepository extends ElasticsearchRepository<Product,String> {
}
