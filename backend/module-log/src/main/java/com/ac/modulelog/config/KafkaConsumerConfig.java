package com.ac.modulelog.config;

import com.ac.modulecommon.message.QuizSolvedStateChangeMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String offsetReset;

    @Value("${spring.kafka.consumer.user-name}")
    private String userName;

    @Value("${spring.kafka.consumer.password}")
    private String password;

    @Bean
    public ConsumerFactory<String, QuizSolvedStateChangeMessage> quizSolvedStateChangeMessageConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                consumerConfigs(),
                new StringDeserializer(),
                new JsonDeserializer<>(QuizSolvedStateChangeMessage.class)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, QuizSolvedStateChangeMessage> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, QuizSolvedStateChangeMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(quizSolvedStateChangeMessageConsumerFactory());
        factory.setCommonErrorHandler(defaultErrorHandler());
        return factory;
    }

    private Map<String, Object> consumerConfigs() {
        log.warn("SASL_JAAS_CONFIG: userName: {}, password: {}", userName, password);
        Map<String, Object> configs = new HashMap<>();
        configs.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configs.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_PLAINTEXT");
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, offsetReset);
        configs.put(SaslConfigs.SASL_MECHANISM, "PLAIN");
        configs.put(SaslConfigs.SASL_JAAS_CONFIG, "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"" + userName + "\" password=\"" + password + "\";");
        return configs;
    }

    @Bean
    public DefaultErrorHandler defaultErrorHandler() {
        return new DefaultErrorHandler(((consumerRecord, e) ->
            log.warn("defaultErrorHandler invoked with consumerRecord: {}, message: {}",
            consumerRecord, e.getMessage(), e)), new FixedBackOff(1000L,3L));
    }
}
