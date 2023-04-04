package com.example.noidea2.service.doc;

import com.example.noidea2.model.doc.DocCreds;
import com.example.noidea2.repo.doc.DocCredsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
@Service
public class DocUserDetailService implements UserDetailsService {

    @Autowired
    private DocCredsRepo docCredsRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DocCreds dc =docCredsRepo.findByUsername(username);
        String dBuserName=dc.getUsername();
        System.out.println(dBuserName);
//        if(dBuserName == null){
//            throw new UsernameNotFoundException("User not authorized.");
//        }
//        return null;
        return new org.springframework.security.core.userdetails.User(dc.getUsername(),dc.getPassword(), new ArrayList<>());
    }
}
