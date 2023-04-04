package com.example.noidea2.controller.doc;

import com.example.noidea2.model.doc.AuthRequest;
import com.example.noidea2.model.doc.DocCreds;
import com.example.noidea2.repo.doc.DocCredsRepo;
import com.example.noidea2.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class DocAuthController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private DocCredsRepo docCredsRepo;
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/")
    public String welcome(){
        return "Hello from the other side";
    }

    @PostMapping("/doc/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception{
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword())
            );
        }catch (Exception ex){
            throw new Exception("Invalid Username/Password");
        }
        return jwtUtil.generateToken(authRequest.getUsername());
    }

    @PostMapping("/doc/register")
    public String newReg(@RequestBody DocCreds registerRequest) throws Exception{
        try{
            docCredsRepo.save(registerRequest);
        }catch (Exception ex){
//            System.out.println(ex);
            throw new Exception(ex);
        }
        return jwtUtil.generateToken(registerRequest.getUsername());
    }

}
