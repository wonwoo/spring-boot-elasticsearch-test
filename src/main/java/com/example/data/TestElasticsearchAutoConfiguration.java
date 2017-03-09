package com.example.data;

import java.io.File;
import java.io.IOException;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.NodeClientFactoryBean;
import org.springframework.data.elasticsearch.client.TransportClientFactoryBean;
import org.springframework.util.FileSystemUtils;

/**
 * Created by wonwoo on 2017. 3. 9..
 */
@Configuration
@ConditionalOnClass({ Client.class, TransportClientFactoryBean.class,
  NodeClientFactoryBean.class })
public class TestElasticsearchAutoConfiguration extends ElasticsearchAutoConfiguration {

  private final ElasticsearchProperties properties;
  private final File tempDirectory;

  public TestElasticsearchAutoConfiguration(ElasticsearchProperties properties) {
    super(properties);
    this.properties = properties;
    this.tempDirectory = createTempDirectory();
  }

  @Override
  public Client elasticsearchClient() {
    try {
      return createNodeClient();
    } catch (Exception ex) {
      throw new IllegalStateException(ex);
    }
  }

  private File createTempDirectory() {
    File tempFile;
    try {
      tempFile = File.createTempFile("temp-elastic", Long.toString(System.nanoTime()));
      tempFile.delete();
      tempFile.mkdir();
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
    return tempFile;
  }

  private Client createNodeClient() throws Exception {
    Settings.Builder settings = Settings.settingsBuilder()
      .put("http.enabled", String.valueOf(false))
      .put("node.local", String.valueOf(true))
      .put("path.home", this.tempDirectory);
    settings.put(this.properties.getProperties());
    Node node = new NodeBuilder().settings(settings)
      .clusterName(this.properties.getClusterName()).node();
    return node.client();
  }

  @Override
  public void destroy() throws Exception {
    FileSystemUtils.deleteRecursively(tempDirectory);
  }
}
