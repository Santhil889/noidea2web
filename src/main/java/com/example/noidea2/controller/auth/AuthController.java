package com.example.noidea2.controller.auth;

import com.example.noidea2.model.auth.AuthRequest;
import com.example.noidea2.model.auth.Creds;
import com.example.noidea2.repo.auth.CredsRepo;
import com.example.noidea2.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.net.http.HttpHeaders;
import java.util.Map;

@RestController
@CrossOrigin
public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CredsRepo credsRepo;
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/try/something")
    public ResponseEntity<String> welcome(@RequestHeader("Authorization") String token) throws Exception{
        if(token==null) System.out.println("Hello this is not working");;
        try{
        token= token.substring(7);
        String uname=jwtUtil.extractUsername(token);
        System.out.println("Kall aya tha yaha par");
        return ResponseEntity.ok("hello "+uname);
        }catch (Exception e){
            throw e;
        }

    }

    @GetMapping("/only/admin")
    public String onlyAdmin() {
        return "hello";
    }

    @PostMapping("/doc/authenticate")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception{
        if(authRequest.getRole()!=credsRepo.findByUsername(authRequest.getUsername()).getRole()) throw new Exception("Not Doctor");
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
    public Integer newReg(@RequestBody Creds registerRequest,@RequestHeader("Authorization") String token) throws Exception{
        token=token.substring(7);
        String uname=jwtUtil.extractUsername(token);
        Creds c=credsRepo.findByUsername(uname);
        if(c.getRole()!=0) throw new Exception("Chal na admin ko bhej");
        try{
            Creds ct = credsRepo.save(registerRequest);
            return ct.getId();
        }catch (Exception ex){
//            System.out.println(ex);
            throw new Exception(ex);
        }

    }

    @PostMapping("/admin/authenticate")
    public ResponseEntity<String> generateTokenforAdmin(@RequestBody AuthRequest authRequest) throws Exception{
        System.out.println(authRequest.getRole()+" sakfanlskfnalk");
        if(authRequest.getRole() != credsRepo.findByUsername(authRequest.getUsername()).getRole() ) {
//            throw new Exception("Not Admin");
            return new ResponseEntity<>(
                    "Bad Credentials",
                    HttpStatus.BAD_REQUEST);
        }
        System.out.println(authRequest.getUsername());
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword())
            );
        }catch (Exception ex){
            throw new Exception("Invalid Username/Password");
        }
        String xyz=jwtUtil.generateToken(authRequest.getUsername());
        return new ResponseEntity<>(
                xyz,
                HttpStatus.OK);
    }
}
