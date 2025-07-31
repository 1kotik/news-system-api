package by.kotik.newsservice.listener;

import by.kotik.newsservice.service.NewsService;
import event.CommentCreatedOrDeletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "${kafka.topic.comment-created-or-deleted-topic-name}",
        groupId = "${spring.kafka.consumer.group-id}")
@RequiredArgsConstructor
public class CommentCreatedOrDeletedEventListener {
    private final NewsService newsService;

    @KafkaHandler
    public void handleCommentCreatedOrDeletedEvent(CommentCreatedOrDeletedEvent event) {
        newsService.changeCommentCount(event.getNewsId(), event.isCreated());
    }
}
