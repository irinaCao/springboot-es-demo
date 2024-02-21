package com.example.es.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.transaction.annotation.Transactional;

@Getter
@Setter
@Transactional
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(indexName="product")//指定数据库名称
public class Product {
    @Id
    private String id;

    @Field(analyzer="ik_smart",searchAnalyzer = "ik_smart", type = FieldType.Text)
    private String title;

    private Integer price;

    @Field(analyzer="ik_smart",searchAnalyzer = "ik_smart",type = FieldType.Text)
    private String intro;

    @Field(type=FieldType.Keyword)
    private String brand;
}
