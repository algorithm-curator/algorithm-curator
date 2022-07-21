package com.ac.moduleapi.eventlistener;

import com.ac.modulecommon.event.QuizSolvedStateChangeEvent;
import com.ac.modulecommon.message.QuizSolvedStateChangeMessage;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
public class QuizSolvedStateChangeEventListener implements AutoCloseable {

    @Value("${spring.kafka.topic.solved-state-change}")
    private String solvedStateChangeTopic;

    private final EventBus eventBus;

    private final KafkaTemplate<String, QuizSolvedStateChangeMessage> kafkaTemplate;

    public QuizSolvedStateChangeEventListener(EventBus eventBus,
                                              KafkaTemplate<String, QuizSolvedStateChangeMessage> kafkaTemplate) {

        this.eventBus = eventBus;
        this.kafkaTemplate = kafkaTemplate;

        eventBus.register(this);
    }

    @Subscribe
    public void handleQuizSolvedStateChangeEvent(QuizSolvedStateChangeEvent event) {

        ListenableFuture<SendResult<String, QuizSolvedStateChangeMessage>> future =
        kafkaTemplate.send(solvedStateChangeTopic, QuizSolvedStateChangeMessage.from(event));

        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                log.warn("SolvedStateChangeEvent exception occurred with userId: {}, quizId: {}, solvedState: {}: {}",
                        event.getUser().getId(), event.getQuiz().getId(), event.getSolvedState(), ex.getMessage(), ex);
            }

            @Override
            public void onSuccess(SendResult<String, QuizSolvedStateChangeMessage> result) {
            }
        });
    }

    @Override
    public void close() throws Exception {
        eventBus.unregister(this);
    }
}
