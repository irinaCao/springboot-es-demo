package com.example.es;

import com.example.es.domain.Product;
import com.example.es.repository.ProductRepository;
import org.apache.commons.codec.binary.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class testQuery {
    //编写全文搜索方法
    @Autowired
    private  ProductRepository repository;

    @Autowired
    private ElasticsearchRestTemplate template;

    // 需求:查询商品标题中符合"游戏 手机"的字样的商品
    @Test
    public void testQuery4(){
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        /**
         * {
         *     query:{
         *         match:{title:"游戏 手机"}
         *     }
         * }
         */
        builder.withQuery(
                QueryBuilders.matchQuery("title", "游戏 手机")
        );
        builder.withPageable(PageRequest.of(0, 100));
        Page<Product> search = repository.search(builder.build());
        search.getContent().forEach(System.err::println);
    }

    // 需求:查询商品标题或简介中符合"蓝牙 指纹 双卡"的字样的商品
    @Test
    public void testQuery7(){
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        /**
         * {
         *     query:{
         *         multi_match:{
         *             "query":"蓝牙 指纹 双卡",
         *             "fields":["title", "intro"]
         *         }
         *     }
         * }
         */
        builder.withQuery(
                QueryBuilders.multiMatchQuery("蓝牙 指纹 双卡", "title", "intro")
        );
        builder.withPageable(PageRequest.of(0, 100, Sort.Direction.DESC, "price"));
        Page<Product> search = repository.search(builder.build());
        search.getContent().forEach(System.err::println);
    }

    //
    @Test
    public void testHighlight() throws Exception {
        //定义索引库
        SearchRequest searchRequest = new SearchRequest("product");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //定义query查询
        MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery("蓝牙 指纹 双卡","title", "intro");
        HighlightBuilder highlightBuilder = new HighlightBuilder(); // 生成高亮查询器
        highlightBuilder.field("title");// 高亮查询字段
        highlightBuilder.field("intro");// 高亮查询字段
        highlightBuilder.requireFieldMatch(false); // 如果要多个字段高亮,这项要为false
        highlightBuilder.preTags("<span style='color:red'>"); // 高亮设置
        highlightBuilder.postTags("</span>");
        highlightBuilder.fragmentSize(800000); // 最大高亮分片数
        highlightBuilder.numOfFragments(0); // 从第一个分片获取高亮片段
        Pageable pageable = PageRequest.of(1, 2); // 设置分页参数
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder) // match查询
                .withPageable(pageable).withHighlightBuilder(highlightBuilder) // 设置高亮
                .build();
        SearchHits<Product> searchHits = template.search(searchQuery, Product.class);
        List<Product> list = new ArrayList();
        for (SearchHit<Product> searchHit : searchHits) { // 获取搜索到的数据
            Product content = searchHit.getContent();
            // 处理高亮
            Map<String, List<String>> highlightFields = searchHit.getHighlightFields();
            for (Map.Entry<String, List<String>> stringHighlightFieldEntry : highlightFields.entrySet()) {
                String key = stringHighlightFieldEntry.getKey();
                if (StringUtils.equals(key, "title")) {
                    List<String> fragments = stringHighlightFieldEntry.getValue();
                    StringBuilder sb = new StringBuilder();
                    for (String fragment : fragments) {
                        sb.append(fragment.toString());
                    }
                    content.setTitle(sb.toString());
                }
                if (StringUtils.equals(key, "intro")) {
                    List<String> fragments = stringHighlightFieldEntry.getValue();
                    StringBuilder sb = new StringBuilder();
                    for (String fragment : fragments) {
                        sb.append(fragment.toString());
                    }
                    content.setIntro(sb.toString());
                }
            }
            list.add(content);
        }
        Page page = new PageImpl(list, pageable, searchHits.getTotalHits());
        list.forEach(System.out::println);
    }
}
