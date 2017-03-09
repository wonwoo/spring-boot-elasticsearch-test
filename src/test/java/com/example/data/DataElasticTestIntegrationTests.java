package com.example.data;

import java.util.List;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

/**
 * Created by wonwoo on 2017. 3. 9..
 */
@RunWith(SpringRunner.class)
@DataElasticTest
public class DataElasticTestIntegrationTests {

  @Autowired
  private ElasticsearchTemplate elasticsearchTemplate;

  @Autowired
  private ExampleElasticsearchRepository sampleElasticsearchRepository;

  @Test
  public void elasticsearchTemplateIsNotNullTest() {
    assertThat(elasticsearchTemplate).isNotNull();
  }

  @Test
  public void elasticsearchTemplateTest() {
    final String id = UUID.randomUUID().toString();
    final ExampleDocument exampleDocument = sampleElasticsearchRepository.save(new ExampleDocument(id, "wonwoo"));
    assertThat(exampleDocument.getId()).isEqualTo(id);
    assertThat(exampleDocument.getName()).isEqualTo("wonwoo");
    SearchQuery searchQuery = new NativeSearchQueryBuilder()
      .withQuery(matchAllQuery())
      .withIndices("sample")
      .withTypes("history")
      .build();
    final List<ExampleDocument> exampleDocuments = elasticsearchTemplate.queryForList(searchQuery, ExampleDocument.class);
    assertThat(exampleDocuments).isNotEmpty();
  }

  @Test
  public void sampleElasticsearchRepositoryTest() {
    final String id = UUID.randomUUID().toString();
    final ExampleDocument exampleDocument = sampleElasticsearchRepository.save(new ExampleDocument(id, "wonwoo"));
    assertThat(exampleDocument.getId()).isEqualTo(id);
    assertThat(exampleDocument.getName()).isEqualTo("wonwoo");

  }
}
