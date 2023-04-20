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

import java.util.*;

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
    public ConsultId assign(@RequestHeader("Authorization") String token) throws Exception{
        try{
            token= token.substring(7);
            String uname=jwtUtil.extractUsername(token);
            Creds c=credsRepo.findByUsername(uname);
            if(c.getRole()==2){
                ArrayList<Integer[]> values= new ArrayList<>();
                List<DocDetails> td= docRepo.findAll();
                for(DocDetails a:td){
                    values.add(new Integer[] {consultRepo.getAllByConsultIdDid(a.getDid()).size(),a.getDid()});
                }
                Collections.sort(values, new Comparator<Integer[]>() {
                    public int compare(Integer[] array1, Integer[] array2) {
                        return array1[0].compareTo(array2[0]);
                    }
                });
                int assdid= values.get(0)[1];
                Consult ct= consultRepo.save(new Consult(new ConsultId(assdid,c.getId()),34,42,"New Patient Assigned",null));
                return ct.getConsultId();
            }
            else throw new Exception("Not Patient");
        }catch (Exception e){
            throw new Exception("not right object");
        }
    }

    @PostMapping("/consult/{did}")
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

    @PostMapping("/consult/activ/{did}")
    public List<Creds> getallactivpat(@RequestHeader("Authorization") String token,@PathVariable Integer did) throws Exception{
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
                    if(a.getIslastconsulted()!=null) {
                        int x = a.getConsultId().getPid();
                        System.out.println(x);
                        temp.add(credsRepo.findById(x));
                    }
                }
                return  temp;
            }
            else throw new Exception("not authorised");
        }catch (Exception e){
            throw e;
        }
    }

    @PostMapping("/consult/blocked/{did}")
    public List<Creds> getallblockedpat(@RequestHeader("Authorization") String token,@PathVariable Integer did) throws Exception{
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
                    if(a.getIslastconsulted() == null) {
                        int x = a.getConsultId().getPid();
                        System.out.println(x);
                        temp.add(credsRepo.findById(x));
                    }
                }
                return  temp;
            }
            else throw new Exception("not authorised");
        }catch (Exception e){
            throw e;
        }
    }

    @PostMapping("/assdoctor")
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
