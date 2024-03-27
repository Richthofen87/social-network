package ru.skillbox.diplom.group46.social.network.impl.config.kafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.skillbox.diplom.group46.social.network.api.dto.account.AccountStatusMessage;
import ru.skillbox.diplom.group46.social.network.api.dto.dialog.MessageDto;
import ru.skillbox.diplom.group46.social.network.api.dto.notifications.NotificationDto;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    private Map<String, Object> configProps;

    @PostConstruct
    private void initConfigMap() {
        configProps = new HashMap<>() {{
            put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
            put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
            put(JsonSerializer.TYPE_MAPPINGS,
                    "notificationDto: ru.skillbox.diplom.group46.social.network.api.dto.notifications.NotificationDto, " +
                    "messageDto: ru.skillbox.diplom.group46.social.network.api.dto.dialog.MessageDto, " +
                    "accountStatusMessage: ru.skillbox.diplom.group46.social.network.api.dto.account.AccountStatusMessage");
        }};
    }

    @Bean
    public ProducerFactory<String, NotificationDto> producerNotificationFactory() {
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public ProducerFactory<String, MessageDto> producerMessageFactory() {
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public ProducerFactory<String, AccountStatusMessage> producerAccountStatusMessageFactory() {
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, NotificationDto> kafkaNotificationTemplate() {
        return new KafkaTemplate<>(producerNotificationFactory());
    }

    @Bean
    public KafkaTemplate<String, MessageDto> kafkaMessageTemplate() {
        return new KafkaTemplate<>(producerMessageFactory());
    }

    @Bean
    public KafkaTemplate<String, AccountStatusMessage> kafkaAccountStatusMessageTemplate() {
        return new KafkaTemplate<>(producerAccountStatusMessageFactory());
    }
}
