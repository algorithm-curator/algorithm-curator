package com.ac.modulelog.subscriber;

import com.ac.modulecommon.message.QuizSolvedStateChangeMessage;
import com.ac.modulelog.service.QuizLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * module-api 로부터 들어오는 event를 받는 eventListener
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class QuizLogSubscriber {

    private final QuizLogService quizLogService;

    @KafkaListener(topics = "${spring.kafka.topic.solved-state-change}",
            containerFactory = "kafkaListenerContainerFactory")
    public void handleQuizSolvedStateChangeMessage(QuizSolvedStateChangeMessage message) {
        quizLogService.createQuizLog(message.getUser(), message.getQuiz(), message.getSolvedState());
    }
}
