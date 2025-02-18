package ru.skillbox.diplom.group46.social.network.impl.config.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * KafkaConfig
 *
 * @author vladimir.sazonov
 */

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public KafkaAdmin.NewTopics topic() {
        List<NewTopic> topics = new ArrayList<>();
        topics.add(TopicBuilder.name("notifications-1").partitions(1).replicas(1).build());
        topics.add(TopicBuilder.name("notifications-2").partitions(1).replicas(1).build());
        topics.add(TopicBuilder.name("messages").partitions(1).replicas(1).build());
        topics.add(TopicBuilder.name("isOnline").partitions(1).replicas(1).build());
        return new KafkaAdmin.NewTopics(topics.toArray(NewTopic[]::new));
    }
}