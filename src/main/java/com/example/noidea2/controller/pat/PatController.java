package com.example.noidea2.controller.pat;

import com.example.noidea2.model.auth.AuthRequest;
import com.example.noidea2.model.auth.Creds;
import com.example.noidea2.model.pat.PatDetails;
import com.example.noidea2.repo.auth.CredsRepo;
import com.example.noidea2.repo.pat.PatRepo;
import com.example.noidea2.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public String addCreds(@RequestBody Creds pd) throws Exception{
        if(pd==null) throw new Exception("kya be");
        try{
            credsRepo.save(pd);
        }catch (Exception e){
            throw e;
        }
        return "Added Patient";
    }

    @PostMapping("/pat/authenticate")
    public String login(@RequestBody AuthRequest pd) throws Exception{
        if(pd==null) throw new Exception("kya be");
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
        patRepo.save(pd);
        return "Patient details added";
    }
}
