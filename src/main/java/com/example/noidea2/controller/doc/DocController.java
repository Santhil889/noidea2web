package com.example.noidea2.controller.doc;

import com.example.noidea2.model.auth.Creds;
import com.example.noidea2.model.doc.DocDetails;
import com.example.noidea2.repo.auth.CredsRepo;
import com.example.noidea2.repo.doc.DocRepo;
import com.example.noidea2.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class DocController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CredsRepo credsRepo;
    @Autowired
    private DocRepo docRepo;

    @GetMapping("/doc/getcount")
    public Long countnoofdoc(@RequestHeader("Authorization") String token) throws Exception{
        token=token.substring(7);
        String uname=jwtUtil.extractUsername(token);
        Creds c=credsRepo.findByUsername(uname);
        if(c.getRole()!=0) throw new Exception("Chal na admin ko bhej");
        return docRepo.count();
    }

    @PostMapping("/doc/add")
    public String addDoc(@RequestBody DocDetails dd,@RequestHeader("Authorization") String token) throws Exception{
        token=token.substring(7);
        String uname=jwtUtil.extractUsername(token);
        Creds c=credsRepo.findByUsername(uname);
        if(c.getRole()!=0) throw new Exception("Chal na admin ko bhej");
        try {
            docRepo.save(dd);
        }catch (Exception e){
            throw new Exception("Kuch to galat ho raha");
        }
        return "added doc details";
    }

    @GetMapping("/doc/getall")
    public List<DocDetails> getall(@RequestHeader("Authorization") String token) throws Exception{
        token=token.substring(7);
        String uname=jwtUtil.extractUsername(token);
        Creds c=credsRepo.findByUsername(uname);
        if(c.getRole()!=0) throw new Exception("Chal na admin ko bhej");
        return docRepo.findAll();
    }

}
