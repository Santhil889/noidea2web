package com.example.noidea2.controller.pat;

import com.example.noidea2.model.auth.AuthRequest;
import com.example.noidea2.model.auth.Creds;
import com.example.noidea2.model.pat.PatDetails;
import com.example.noidea2.repo.auth.CredsRepo;
import com.example.noidea2.repo.pat.PatRepo;
import com.example.noidea2.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        javaMailSender.send(message);
    }

    @PostMapping("/pat/register")
    public Integer addCreds(@RequestBody Creds pd) throws Exception{
        if(pd==null) throw new Exception("kya be");
        try{
            String p= pd.getPassword();
            pd.setPassword(passwordEncoder.encode(pd.getPassword()));
            sendEmail(pd.getEmail(),
                    "Your Credentials for Accessing the Medical Records System",
                    "Dear Doctor,\n" +
                            "\n" +
                            "I am writing to provide you with your login credentials for accessing our medical records system. As an authorized user, you will be able to access patient medical records and update them as needed.\n" +
                            "\n" +
                            "Your login credentials are as follows:\n" +
                            "\n" +
                            "Username: "+pd.getUsername()+"\n" +
                            "Password: "+ p +"\n" +
                            "\n" +
                            "Please note that the password provided is case sensitive and must be kept confidential to ensure the security of patient data.\n" +
                            "\n" +
                            "To access the system, please visit [Insert Website URL] and enter your login credentials. If you experience any difficulties or have questions, please do not hesitate to contact our IT support team at [Insert Contact Information].\n" +
                            "\n" +
                            "Thank you for joining our team, and we look forward to working with you.\n" +
                            "\n" +
                            "Best regards,\n" +
                            "\n" +
                            "Admin\n" +
                            "admin@admin.com\n" +
                            "+91-10110101\n" +
                            "\n" +
                            "\n" +
                            "\n" +
                            "\n");
            Creds c=credsRepo.save(pd);
            return c.getId();
        }catch (Exception e){
            throw e;
        }
    }

    @PostMapping("/pat/authenticate")
    public PatReruenObj login(@RequestBody AuthRequest pd) throws Exception{
        if(pd==null) throw new Exception("kya be");
//        System.out.println(pd.getRole()+" sakfanlskfnalk");
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
        Creds tt= credsRepo.findByUsername(pd.getUsername());

        return new PatReruenObj(xyz,tt.getId());
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

    @PostMapping("/pat/savepatdetailbytoken")
    public String savedetailsbytoken(@RequestHeader("Authorization") String token,@RequestBody PatDetails pd) throws Exception{
        if(pd==null) throw new Exception("null valaue");
        token=token.substring(7);
        String uname=jwtUtil.extractUsername(token);
        Creds c=credsRepo.findByUsername(uname);
        pd.setPid(c.getId());
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

    @PostMapping("/pat/getall")
    public Integer getpatcount(@RequestHeader("Authorization") String token) throws Exception{
        try{
            token=token.substring(7);
            String uname=jwtUtil.extractUsername(token);
            Creds c=credsRepo.findByUsername(uname);
            if(c.getRole()!=0) throw new Exception("Not Admin");
            return patRepo.findAll().size();
        }catch (Exception e){
            throw e;
        }
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class PatReruenObj{
    private String token;
    private int id;
}
