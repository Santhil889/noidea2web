package com.example.noidea2.controller.auth;

import com.example.noidea2.model.auth.AuthRequest;
import com.example.noidea2.model.auth.Creds;
import com.example.noidea2.repo.auth.CredsRepo;
import com.example.noidea2.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.net.http.HttpHeaders;
import java.util.Map;

@RestController
@CrossOrigin
public class AuthController {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CredsRepo credsRepo;
    @Autowired
    private AuthenticationManager authenticationManager;

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        javaMailSender.send(message);
    }

    @PostMapping("/try/something")
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

    @PostMapping("/only/admin")
    public String onlyAdmin() {
        return "hello";
    }

    @PostMapping("/doc/authenticate")
    public DocReturnObject generateToken(@RequestBody AuthRequest authRequest) throws Exception{
        if(authRequest.getRole()!=credsRepo.findByUsername(authRequest.getUsername()).getRole()) throw new Exception("Not Doctor");
        try{

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword())
            );
        }catch (Exception ex){
            throw new Exception("Invalid Username/Password");
        }
        String token=jwtUtil.generateToken(authRequest.getUsername());
        String uname=jwtUtil.extractUsername(token);
        Creds c=credsRepo.findByUsername(uname);
        return new DocReturnObject(token , c.getId());


    }

    @PostMapping("/doc/register")
    public Integer newReg(@RequestBody Creds registerRequest,@RequestHeader("Authorization") String token) throws Exception{
        token=token.substring(7);
        String uname=jwtUtil.extractUsername(token);
        Creds c=credsRepo.findByUsername(uname);
        if(c.getRole()!=0) throw new Exception("Chal na admin ko bhej");
        try{
            Creds ct = credsRepo.save(registerRequest);
//            sendEmail("santhilkalantre@gmail.com",
//                    "Your Credentials for Accessing the Medical Records System",
//                    "Dear Doctor,\n" +
//                            "\n" +
//                            "I am writing to provide you with your login credentials for accessing our medical records system. As an authorized user, you will be able to access patient medical records and update them as needed.\n" +
//                            "\n" +
//                            "Your login credentials are as follows:\n" +
//                            "\n" +
//                            "Username: "+registerRequest.getUsername()+"\n" +
//                            "Password: "+ registerRequest.getPassword() +"\n" +
//                            "\n" +
//                            "Please note that the password provided is case sensitive and must be kept confidential to ensure the security of patient data.\n" +
//                            "\n" +
//                            "To access the system, please visit [Insert Website URL] and enter your login credentials. If you experience any difficulties or have questions, please do not hesitate to contact our IT support team at [Insert Contact Information].\n" +
//                            "\n" +
//                            "Thank you for joining our team, and we look forward to working with you.\n" +
//                            "\n" +
//                            "Best regards,\n" +
//                            "\n" +
//                            "Admin\n" +
//                            "admin@admin.com\n" +
//                            "+91-10110101\n" +
//                            "\n" +
//                            "\n" +
//                            "\n" +
//                            "\n");
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

@AllArgsConstructor
@NoArgsConstructor
@Data
class DocReturnObject{
    private String jwttoken;
    private Integer did;
}


