package by.kotik.commentservice.listener;

import by.kotik.commentservice.service.CommentService;
import event.NewsDeletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@KafkaListener(topics = "${kafka.topic.news-deleted-topic-name}",
        groupId = "${spring.kafka.consumer.group-id}")
@RequiredArgsConstructor
public class NewsDeletedEventListener {
    private final CommentService commentService;

    @KafkaHandler
    @Transactional
    public void handleNewsDeleted(NewsDeletedEvent event) {
        commentService.deleteByNewsId(event.getNewsId());
    }
}
