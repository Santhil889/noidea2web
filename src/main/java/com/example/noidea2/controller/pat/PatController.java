package com.example.noidea2.controller.pat;

import com.example.noidea2.model.auth.AuthRequest;
import com.example.noidea2.model.auth.Creds;
import com.example.noidea2.model.pat.PatDetails;
import com.example.noidea2.repo.auth.CredsRepo;
import com.example.noidea2.repo.pat.PatRepo;
import com.example.noidea2.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class PatController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CredsRepo credsRepo;

    @Autowired
    private PatRepo patRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/pat/register")
    public Integer addCreds(@RequestBody Creds pd) throws Exception{
        if(pd==null) throw new Exception("kya be");
        try{
            Creds c=credsRepo.save(pd);
            return c.getId();
        }catch (Exception e){
            throw e;
        }
    }

    @PostMapping("/pat/authenticate")
    public String login(@RequestBody AuthRequest pd) throws Exception{
        if(pd==null) throw new Exception("kya be");
        System.out.println(pd.getRole()+" sakfanlskfnalk");
        if(pd.getRole().intValue()  !=  credsRepo.findByUsername(pd.getUsername()).getRole() ) {
//            throw new Exception("Not Admin");
            throw new Exception("Ja na Gandu");
        }
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(pd.getUsername(),pd.getPassword())
            );
        }catch (Exception ex){
            throw new Exception("Invalid Username/Password");
        }
        String xyz=jwtUtil.generateToken(pd.getUsername());
        System.out.println(xyz);
        return xyz;
    }

    @PostMapping("/pat/savedetail")
    public String savedetails(@RequestBody PatDetails pd) throws Exception{
        if(pd==null) throw new Exception("null valaue");
        if(credsRepo.findById(pd.getPid()) == null) throw new Exception("Could not add random");
        try{
            patRepo.save(pd);
            return "Patient details added";
        }catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/patget/detail")
    public PatDetails getdetailsofpat(@RequestHeader("Authorization") String token) throws Exception{
        try{
            token=token.substring(7);
            String uname=jwtUtil.extractUsername(token);
            Creds c=credsRepo.findByUsername(uname);
            if(c.getRole()!=2) throw new Exception("Not Patient");
            return patRepo.findByPid(c.getId());
        }catch (Exception e){
            throw e;
        }
    }
}
