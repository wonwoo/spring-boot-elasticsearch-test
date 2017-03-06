package com.example.data;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by wonwoo on 2017. 3. 9..
 */
public interface SampleElasticsearchRepository extends ElasticsearchRepository<ExampleDocument, String> {
}
