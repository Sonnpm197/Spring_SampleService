package com.son.learningenglish.stream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class QuizletChangeListener {
//    @StreamListener(Sink.INPUT)
    public void loggerSink(QuizletChangeModel changeModel) {
        log.info("Received an event for quizlet id {}" , changeModel.getQuizletId());
    }
}
