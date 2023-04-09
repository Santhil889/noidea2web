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
import java.util.Optional;

@RestController
@CrossOrigin()
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
        if(dd== null || !credsRepo.findById(dd.getDid()).isPresent()) throw new Exception("nahi hoga add");
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


    @GetMapping("/doc/get/{id}")
    public Optional<DocDetails> getone(@RequestHeader("Authorization") String token,@PathVariable Integer id) throws Exception{
        token=token.substring(7);
        String uname=jwtUtil.extractUsername(token);
        Creds c=credsRepo.findByUsername(uname);
        if(c.getRole()!=0) throw new Exception("Chal na admin ko bhej");

        return docRepo.findById(id);
    }

    @PutMapping("/doc/change/{id}")
    public String changeDocDetails(@PathVariable Integer id,@RequestBody DocDetails dd,@RequestHeader("Authorization") String token) throws Exception{
        token=token.substring(7);
        String uname=jwtUtil.extractUsername(token);
        Creds c=credsRepo.findByUsername(uname);
        if(c.getRole()!=0) throw new Exception("Chal na admin ko bhej");
        DocDetails d=docRepo.findById(id).get();
        if(d==null) throw new Exception("no id found");
        docRepo.delete(d);
        docRepo.save(dd);
        return "Ho gya update";
    }

}
