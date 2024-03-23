package ru.skillbox.diplom.group46.social.network.impl.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.ArrayList;
import java.util.List;

/**
 * KafkaConfig
 *
 * @author vladimir.sazonov
 */

@Configuration
public class KafkaConfig {

    @Bean
    public KafkaAdmin.NewTopics topic() {
        List<NewTopic> topics = new ArrayList<>();
        topics.add(TopicBuilder.name("notifications").partitions(1).replicas(1).build());
        topics.add(TopicBuilder.name("messages").partitions(1).replicas(1).build());
        topics.add(TopicBuilder.name("isOnline").partitions(1).replicas(1).build());
        return new KafkaAdmin.NewTopics(topics.toArray(NewTopic[]::new));
    }
}