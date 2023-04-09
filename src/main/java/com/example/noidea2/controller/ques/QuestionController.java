package com.example.noidea2.controller.ques;

import com.example.noidea2.model.auth.Creds;
import com.example.noidea2.model.ques.Question;
import com.example.noidea2.model.ques.QuestionId;
import com.example.noidea2.repo.auth.CredsRepo;
import com.example.noidea2.repo.consult.ConsultRepo;
import com.example.noidea2.repo.doc.DocRepo;
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
            Question q = questionRepo.save(question);
            return q.getQuestionId();
        }catch (Exception e){
            throw e;
        }
    }
}
