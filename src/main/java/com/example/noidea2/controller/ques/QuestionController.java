package com.example.noidea2.controller.ques;

import com.example.noidea2.model.auth.Creds;
import com.example.noidea2.model.pat.PatDetails;
import com.example.noidea2.model.ques.Question;
import com.example.noidea2.model.ques.QuestionId;
import com.example.noidea2.repo.auth.CredsRepo;
import com.example.noidea2.repo.consult.ConsultRepo;
import com.example.noidea2.repo.doc.DocRepo;
import com.example.noidea2.repo.pat.PatRepo;
import com.example.noidea2.repo.ques.QuestionRepo;
import com.example.noidea2.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class QuestionController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private ConsultRepo consultRepo;
    @Autowired
    private DocRepo docRepo;
    @Autowired
    private PatRepo patRepo;
    @Autowired
    private CredsRepo credsRepo;
    @Autowired
    private QuestionRepo questionRepo;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/save/question")
    public QuestionId savequestion(@RequestBody Question question,@RequestHeader("Authorization") String token) throws Exception {
        try{
            token = token.substring(7);
            String uname = jwtUtil.extractUsername(token);
            Creds c = credsRepo.findByUsername(uname);
            if (c.getRole() != 2 || question.getQuestionId().getPid() != c.getId()) throw new Exception("error");
            int score= calcScore(question);
            PatDetails pd= patRepo.findByPid(question.getQuestionId().getPid());
            pd.setScore(score);
            Question q = questionRepo.save(question);
            return q.getQuestionId();
        }catch (Exception e){
            throw e;
        }
    }

    private int calcScore(Question question) {
        int q1,q2,q3,q4,q5,q6,q7,q8,q9,q10;
        q1= question.getQ1();
        q2= question.getQ2();
        q3= question.getQ3();
        q4= question.getQ4();
        q5= question.getQ5();
        q6= question.getQ6();
        q7= question.getQ7();
        q8= question.getQ8();
        q9= question.getQ9();
        q10= question.getQ10();
        return (q1+q2+q3+q4+q5+q6+q7+q8+q9+q10)/10;
    }
}
