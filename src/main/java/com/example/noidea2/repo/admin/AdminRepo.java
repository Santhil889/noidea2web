package com.example.noidea2.repo.admin;

import com.example.noidea2.model.admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepo extends JpaRepository<Admin,Integer> {
    Admin findByUsername(String username);
}
