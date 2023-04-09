package com.example.noidea2.controller.consult;

import com.example.noidea2.model.auth.Creds;
import com.example.noidea2.model.consult.Consult;
import com.example.noidea2.model.consult.ConsultId;
import com.example.noidea2.model.doc.DocDetails;
import com.example.noidea2.repo.auth.CredsRepo;
import com.example.noidea2.repo.consult.ConsultRepo;
import com.example.noidea2.repo.doc.DocRepo;
import com.example.noidea2.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class ConsultController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private ConsultRepo consultRepo;
    @Autowired
    private DocRepo docRepo;
    @Autowired
    private CredsRepo credsRepo;
    @Autowired
    private AuthenticationManager authenticationManager;
    //filhaal manual
    @PostMapping("/consult/assign")
    public ConsultId assign(@RequestHeader("Authorization") String token,@RequestBody Consult consult) throws Exception{
        try{
            token= token.substring(7);
            String uname=jwtUtil.extractUsername(token);
            Creds c=credsRepo.findByUsername(uname);
            if(c.getRole()!=0) throw new Exception("not admin");
            Consult ct=consultRepo.save(consult);
            return ct.getConsultId();
        }catch (Exception e){
            throw new Exception("not right object");
        }
    }

    @GetMapping("/consult/{did}")
    public List<Creds> getallpat(@RequestHeader("Authorization") String token,@PathVariable Integer did) throws Exception{
        try{
//            List<Creds> temp =null;
            ArrayList<Creds> temp = new ArrayList<Creds>();
            List<Consult> tempids;
            token= token.substring(7);
            String uname=jwtUtil.extractUsername(token);
            Creds c=credsRepo.findByUsername(uname);
            if(c.getRole()==0 || (did == c.getId())){
                tempids=consultRepo.getAllByConsultIdDid(did);
                for (Consult a: tempids) {
                    int x=a.getConsultId().getPid();
                    System.out.println(x);
                    temp.add(credsRepo.findById(x));
                }

                return  temp;
            }
            else throw new Exception("not authorised");
        }catch (Exception e){
            throw e;
        }
    }

    @GetMapping("/assdoctor")
    public Optional<DocDetails> getassdoc(@RequestHeader("Authorization") String token) throws Exception{
        try{
            token = token.substring(7);
            String uname = jwtUtil.extractUsername(token);
            Creds c = credsRepo.findByUsername(uname);
            int x = c.getId();
            Consult doc = consultRepo.getByConsultId_Pid(x);

            int did = doc.getConsultId().getDid();
            System.out.println(did);
            Optional<DocDetails> assdoc = docRepo.findById(did);
            return assdoc;
        }catch (Exception E){
            throw E;
        }
    }

}
