package com.son.learningenglish.stream.customChannel;

import com.son.learningenglish.repository.QuizletRedisRepository;
import com.son.learningenglish.stream.QuizletChangeModel;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(CustomChannels.class)
@Slf4j
public class CustomQuizletChangeHandler {

    @Autowired
    private QuizletRedisRepository quizletRedisRepository;

    @StreamListener("customQuizletChannel")
    public void loggerSink(QuizletChangeModel model) {
        log.info("Received a message of type " + model.getType());
        switch(model.getAction()){
//            case "GET":
//                log.debug("Received a GET event from the organization service for quiz id {}", model.getQuizletId());
//                break;
            case "SAVE":
                log.debug("Received a SAVE event from the organization service for quiz id {}", model.getQuizletId());
                break;
//            case "UPDATE":
//                log.debug("Received a UPDATE event from the organization service for quiz id {}", model.getQuizletId());
//                quizletRedisRepository.delete(model.getQuizletId());
//                break;
//            case "DELETE":
//                log.debug("Received a DELETE event from the organization service for quiz id {}", model.getQuizletId());
//                organizationRedisRepository.deleteOrganization(model.getQuizletId());
//                break;
            default:
                log.error("Received an UNKNOWN event from the quiz service of type {}", model.getType());
                break;

        }
    }

}
