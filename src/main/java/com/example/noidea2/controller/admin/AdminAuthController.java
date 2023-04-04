package com.example.noidea2.controller.admin;

import com.example.noidea2.model.admin.AdminAuthRequest;
import com.example.noidea2.model.doc.AuthRequest;
import com.example.noidea2.repo.admin.AdminRepo;
import com.example.noidea2.repo.doc.DocCredsRepo;
import com.example.noidea2.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class AdminAuthController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AdminRepo adminRepo;
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/admin")
    public String welcome(){
        return "Hello from admin side";
    }

//    @PostMapping("/admin/authenticate")
//    public String generateToken(@RequestBody AdminAuthRequest authRequest) throws Exception{
//        try{
////            authenticationManager.authenticate(
////                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword())
////            );
//            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
//            authenticationManager.authenticate(authenticationToken);
////            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }catch (Exception ex){
//            throw new Exception("Invalid Username/Password");
//        }
//        return jwtUtil.generateToken(authRequest.getUsername());
//    }
}
