package com.son.learningenglish.repository;

import com.son.learningenglish.model.Quizlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
public class QuizletRedisRepositoryImpl implements QuizletRedisRepository {
    private static final String HASH_NAME = "quizlet";
    private RedisTemplate<String, Quizlet> redisTemplate;
    private HashOperations hashOperations;

    public QuizletRedisRepositoryImpl() {
        super();
    }

    @Autowired
    private QuizletRedisRepositoryImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void saveQuizlet(Quizlet quizlet) {
        hashOperations.put(HASH_NAME, quizlet.getId(), quizlet);
    }

    @Override
    public Quizlet findQuizlet(String id) {
        return (Quizlet) hashOperations.get(HASH_NAME, id);
//        return null;
    }
}
