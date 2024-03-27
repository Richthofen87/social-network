package ru.skillbox.diplom.group46.social.network.impl.config.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import ru.skillbox.diplom.group46.social.network.api.dto.account.AccountStatusMessage;
import ru.skillbox.diplom.group46.social.network.api.dto.dialog.MessageDto;
import ru.skillbox.diplom.group46.social.network.api.dto.notifications.NotificationDto;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    private Map<String, Object> configProps;

    @PostConstruct
    private void initConfigMap() {
        configProps = new HashMap<>() {{
            put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
            put(ConsumerConfig.GROUP_ID_CONFIG, "notesGroup-1");
            put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
            put(JsonDeserializer.TYPE_MAPPINGS,
                    "notificationDto: ru.skillbox.diplom.group46.social.network.api.dto.notifications.NotificationDto, " +
                            "messageDto: ru.skillbox.diplom.group46.social.network.api.dto.dialog.MessageDto, " +
                            "accountStatusMessage: ru.skillbox.diplom.group46.social.network.api.dto.account.AccountStatusMessage");
        }};
    }

    @Bean
    public ConsumerFactory<String, NotificationDto> consumerNotificationFactory() {
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean
    public ConsumerFactory<String, MessageDto> consumerMessageFactory() {
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean
    public ConsumerFactory<String, AccountStatusMessage> consumerAccountStatusMessageFactory() {
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, NotificationDto>
    kafkaNotificationDtoListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, NotificationDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerNotificationFactory());
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MessageDto>
    kafkaMessageListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, MessageDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerMessageFactory());
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AccountStatusMessage>
    kafkaAccountStatusMessageListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AccountStatusMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerAccountStatusMessageFactory());
        return factory;
    }
}
