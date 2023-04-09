package com.example.noidea2.model.ques;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "question")
public class Question {
    @EmbeddedId
    private QuestionId questionId;
    @NotNull
    private Integer q1,q2,q3,q4,q5,q6,q7,q8,q9,q10;
    @NotNull
    private String a1,a2,a3;
}
