package com.example.data;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.lease.Releasable;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.NodeClientFactoryBean;
import org.springframework.data.elasticsearch.client.TransportClientFactoryBean;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.ReflectionUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by wonwoo on 2017. 3. 9..
 */
@Configuration
@ConditionalOnClass({Client.class, TransportClientFactoryBean.class,
        NodeClientFactoryBean.class})
@EnableConfigurationProperties(ElasticsearchProperties.class)
public class TestElasticsearchAutoConfiguration implements DisposableBean {

    private final ElasticsearchProperties properties;
    private final File tempDirectory;
    private Releasable releasable;

    public TestElasticsearchAutoConfiguration(ElasticsearchProperties properties) {
        this.properties = properties;
        this.tempDirectory = createTempDirectory();
    }

    @Bean
    @ConditionalOnMissingBean
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
        this.releasable = node;
        return node.client();
    }

    @Override
    public void destroy() throws Exception {
        if (this.releasable != null) {
            try {
                this.releasable.close();
            } catch (NoSuchMethodError ex) {
                ReflectionUtils.invokeMethod(
                        ReflectionUtils.findMethod(Releasable.class, "release"),
                        this.releasable);
            }
        }
        FileSystemUtils.deleteRecursively(tempDirectory);
    }
}
