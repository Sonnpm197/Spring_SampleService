package com.son.learningenglish.repository;

import com.son.learningenglish.model.Quizlet;

public interface QuizletRedisRepository {
    void saveQuizlet(Quizlet quizlet);
    Quizlet findQuizlet(String id);
}
