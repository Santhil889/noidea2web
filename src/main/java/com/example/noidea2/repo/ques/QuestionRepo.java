package com.example.noidea2.repo.ques;

import com.example.noidea2.model.ques.Question;
import com.example.noidea2.model.ques.QuestionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepo extends JpaRepository<Question, QuestionId> {

}
