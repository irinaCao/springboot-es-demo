package com.example.es;

import com.example.es.domain.Product;
import com.example.es.service.IProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ElasticsearchDemoApplicationTests {
	@Autowired
	private IProductService productService;


	@Test
	public void testSave() {
		Product p = new Product();
      //  p.setId("123");
		p.setBrand("iphone");
		p.setIntro("iphone introduction");
		p.setPrice(1000);
		p.setTitle("iphone title");
		productService.save(p);
	}

	@Test
	public void testUpate() {
		Product p = new Product();
		p.setId("yhB-34kBUwfT6ZqdHes9");
		p.setBrand("xiaomi");
		p.setIntro("xiaomi introduction");
		p.setPrice(1000);
		p.setTitle("xiaomi title");
		productService.update(p);
		/*报错 如果添加时没有设置id,后续无法再设置id  解决方法: 设计domain类时设计一个_id字段,一个id字段
		ElasticsearchStatusException[Elasticsearch exception [type=mapper_parsing_exception,
		reason=failed to parse field [id] of type [long] in document with id 'yhB-34kBUwfT6ZqdHes9'. Preview of field's value: 'yhB-34kBUwfT6ZqdHes9']];
		nested: ElasticsearchException[Elasticsearch exception [type=illegal_argument_exception, reason=For input string: "yhB-34kBUwfT6ZqdHes9"]];
		 */
	}
	@Test
	public void testDelete() {
		productService.delete("123");
	}
	@Test
	public void testGet() {
		System.out.println(productService.get("1").toString());
	}
	@Test
	public void testList() {
		System.err.println(productService.list().toString());
	}


}