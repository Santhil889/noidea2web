package com.example.noidea2;

//import com.example.noidea2.model.admin.Admin;
import com.example.noidea2.model.doc.DocCreds;
//import com.example.noidea2.repo.admin.AdminRepo;
import com.example.noidea2.repo.doc.DocCredsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class Noidea2Application {

//	@Autowired
//	private DocCredsRepo docCredsRepo;

//	@Autowired
//	private AdminRepo adminRepo;
//
//	@PostConstruct
//	public void initAdmin(){
//		Admin a=new Admin(1,"admin","admin");
//		adminRepo.save(a);
//	}
//	@PostConstruct
//	public void initUser(){
//		List<DocCreds> users= Stream.of(
//				new DocCreds(101,"santhil","santhil@gmail.com","123"),
//				new DocCreds(102,"doctor1","doctor1@gmail.com","123")
//		).collect(Collectors.toList());
//		docCredsRepo.saveAll(users);
//	}
	public static void main(String[] args) {
		SpringApplication.run(Noidea2Application.class, args);
	}

}
