package com.son.learningenglish.redis;

import com.son.learningenglish.model.Quizlet;
import com.son.learningenglish.repository.QuizletRedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class QuizletRestTemplateClient {
    @Autowired
    @Qualifier("getRestTemplate")
    RestTemplate restTemplate;

    @Autowired
    private QuizletRedisRepository quizletRedisRepository;

    private Quizlet checkRedisCache(String id) {
        try {
            return quizletRedisRepository.findQuizlet(id);
        } catch (Exception ex) {
            log.error("Error encountered while trying to retrieve quizlet {} check Redis Cache. Exception {}", id, ex);
            return null;
        }
    }

    private void cacheObject(Quizlet quizlet) {
        try {
            quizletRedisRepository.saveQuizlet(quizlet);
        } catch (Exception ex) {
            log.error("Unable to cache organization {} in Redis. Exception {}", quizlet.getId(), ex);
        }
    }

    public Quizlet getQuizlet(String id) {
        Quizlet quizlet = checkRedisCache(id);

        if (quizlet != null) {
            log.debug("Retrieved an quizlet {} from the redis cache: {}", id, quizlet);
            return quizlet;
        }

        log.debug("Unable to locate quizlet from the redis cache: {}.", id);

        ResponseEntity<Quizlet> restExchange =
                restTemplate.exchange(
                        "http://legateway/api/quiz/sampleQuizletById/" + id,
                        HttpMethod.GET,
                        null, Quizlet.class);

        /*Save the record from cache*/
        quizlet = restExchange.getBody();

        if (quizlet != null) {
            cacheObject(quizlet);
        }

        return quizlet;
    }


}
