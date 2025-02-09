package com.store.storehouse.configuration.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.util.Properties;

@Configuration
@Slf4j
public class PropertiesConfiguration {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public Properties producerHistoryProperties() {
        Properties properties = loadProperties("classpath:kafka/history_event.producer.properties");
        log.info("Конфигурация history_event.producer.properties загружена: \t", properties);
        return properties;
    }

    private Properties loadProperties(String location) {
        try (var inputStrea = new PathMatchingResourcePatternResolver().getResource(location).getInputStream()){
            var properties = new Properties();
            properties.load(inputStrea);
            properties.put("bootstrap.servers", bootstrapServers);
            return properties;
        }
        catch (Exception ex) {
            log.error("Не удалось загрузить конфиг из {}", location);
            throw new RuntimeException();
        }
    }
}
