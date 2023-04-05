package com.example.noidea2.controller.auth;

import com.example.noidea2.model.auth.AuthRequest;
import com.example.noidea2.model.auth.Creds;
import com.example.noidea2.repo.auth.CredsRepo;
import com.example.noidea2.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpHeaders;

@RestController
@CrossOrigin
public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CredsRepo credsRepo;
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/")
    public String welcome(@RequestHeader("Authorization") String token){
        token= token.substring(7);
        String uname=jwtUtil.extractUsername(token);
        return "hello "+uname;
    }

    @GetMapping("/only/admin")
    public String onlyAdmin(@RequestHeader("Authorization") String token) throws Exception{
        token= token.substring(7);
        String uname=jwtUtil.extractUsername(token);
        Creds c=credsRepo.findByUsername(uname);
        if(c.getRole()==0)
        return "hello";
        else throw new Exception("Not Admin");
    }

    @PostMapping("/doc/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception{
        try{
            if(authRequest.getRole()!=1) throw new Exception("Not Doctor");
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword())
            );
        }catch (Exception ex){
            throw new Exception("Invalid Username/Password");
        }
        return jwtUtil.generateToken(authRequest.getUsername());
    }

    @PostMapping("/doc/register")
    public String newReg(@RequestBody Creds registerRequest) throws Exception{
        if(registerRequest.getRole() != 1) throw new Exception("Not doctor added");
        try{
            credsRepo.save(registerRequest);
        }catch (Exception ex){
//            System.out.println(ex);
            throw new Exception(ex);
        }
        return jwtUtil.generateToken(registerRequest.getUsername());
    }

    @PostMapping("/admin/authenticate")
    public String generateTokenforAdmin(@RequestBody AuthRequest authRequest) throws Exception{
//        Creds t=new Creds(1,"hello","hellotry","123",0);
//        credsRepo.save(t);
//        return "hello added";
        if(authRequest.getRole() != 0 ) throw new Exception("Not Admin");
        System.out.println(authRequest.getUsername());
        try{
//            String email=authRequest.get("email");
//            String password= authRequest.get("password");
//            if(email.equals("admin@admin.com") && password.equals("admin")){
//                System.out.println( jwtUtil.generateToken(authRequest.get("email")));
//            }
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword())
            );
        }catch (Exception ex){
            throw new Exception("Invalid Username/Password");
        }
        String xyz=jwtUtil.generateToken(authRequest.getUsername());
        System.out.println(xyz);
        return xyz;
    }

}
