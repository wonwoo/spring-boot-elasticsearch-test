package com.example.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Created by wonwoo on 2017. 3. 9..
 */
@Document(indexName = "sample", type = "history", shards = 1, replicas = 0, refreshInterval = "-1")
public class ExampleDocument {
  @Id
  private String id;
  private String name;

  ExampleDocument() {

  }

  public ExampleDocument(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ExampleDocument)) return false;

    ExampleDocument that = (ExampleDocument) o;

    if (id != null ? !id.equals(that.id) : that.id != null) return false;
    return name != null ? name.equals(that.name) : that.name == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "ExampleDocument{" + "id='" + id + '\'' +
      ", name='" + name + '\'' +
      '}';
  }
}
