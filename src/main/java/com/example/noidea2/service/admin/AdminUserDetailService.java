package com.example.noidea2.service.admin;

import com.example.noidea2.model.admin.Admin;
import com.example.noidea2.repo.admin.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AdminUserDetailService implements UserDetailsService {
    @Autowired
    private AdminRepo adminRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin ad= adminRepo.findByUsername(username);
        String dBuserName=ad.getUsername();
        System.out.println(dBuserName);

        return new org.springframework.security.core.userdetails.User(ad.getUsername(),ad.getPassword(),new ArrayList<>());
    }
}
