package com.son.learningenglish.stream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuizletChangeModel {
    private String type;
    private String action;
    private String quizletId;
    private String correlationId;
}