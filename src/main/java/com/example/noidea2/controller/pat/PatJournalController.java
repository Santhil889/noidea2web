package com.example.noidea2.controller.pat;

import com.example.noidea2.model.auth.Creds;
import com.example.noidea2.model.pat.PatJournal;
import com.example.noidea2.repo.auth.CredsRepo;
import com.example.noidea2.repo.pat.PatJournalRepo;
import com.example.noidea2.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
public class PatJournalController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CredsRepo credsRepo;
    @Autowired
    private PatJournalRepo patJournalRepo;

    @PostMapping("/journal/save")
    public PatJournal savejournal(@RequestHeader("Authorization") String token, @RequestBody PatJournal patJournal) throws Exception{
        try{
            token=token.substring(7);
            String uname=jwtUtil.extractUsername(token);
            Creds c=credsRepo.findByUsername(uname);
            if(c.getRole()!=2) throw new Exception("Not Patient");
            patJournal.setFilledwhen(new Date());
            return patJournalRepo.save(patJournal);
        }catch (Exception e){
            throw e;
        }
    }

    @PostMapping("/get/alljournal")
    public List<PatJournal> listofjournal(@RequestHeader("Authorization") String token) throws Exception{
        try{
            token=token.substring(7);
            String uname=jwtUtil.extractUsername(token);
            Creds c=credsRepo.findByUsername(uname);
            if(c.getRole()!=2) throw new Exception("Not Patient");
            return patJournalRepo.findAllByPidOrderByFilledwhenAsc(c.getId());
        }catch (Exception e){
            throw e;
        }
    }
}
