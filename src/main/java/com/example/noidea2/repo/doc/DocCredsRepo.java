package com.example.noidea2.repo.doc;

import com.example.noidea2.model.doc.DocCreds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

public interface DocCredsRepo extends JpaRepository<DocCreds,Integer> {

    DocCreds findByUsername(String username);

}
