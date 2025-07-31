package by.kotik.newsservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Value("${kafka.topic.news-deleted-topic-name}")
    private String newsDeletedTopicName;
    @Value("${kafka.topic.comment-created-or-deleted-topic-name}")
    private String commentCreatedOrDeletedTopicName;

    @Bean
    public NewTopic newsDeletedTopic() {
        return TopicBuilder
                .name(newsDeletedTopicName)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic commentCreatedOrDeletedTopic() {
        return TopicBuilder
                .name(commentCreatedOrDeletedTopicName)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
