package com.example.noidea2;

//import com.example.noidea2.model.admin.Admin;
//import com.example.noidea2.repo.admin.AdminRepo;
import com.example.noidea2.model.auth.Creds;
import com.example.noidea2.model.task.AssignedTask;
import com.example.noidea2.model.task.Task;
import com.example.noidea2.repo.auth.CredsRepo;
import com.example.noidea2.repo.task.AssignedTaskRepo;
import com.example.noidea2.repo.task.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class Noidea2Application {
	@Autowired
	private TaskRepo taskRepo;
	@Autowired
	private CredsRepo credsRepo;

	@Autowired
	private AssignedTaskRepo assignedTaskRepo;

//	@Autowired
//	private AdminRepo adminRepo;
//
	@PostConstruct
	public void initAdmin(){
		Creds a=new Creds(1,"admin","admin@admin.com","admin",0);
		credsRepo.save(a);
	}

	@PostConstruct
	public void initTask(){
//		Creds a=new Creds(1,"admin","admin@admin.com","admin",0);
		List<Task> tasks= Stream.of(
				new Task(1,1,null,"TYPE 1 TASK 1"),
				new Task(2,1,null,"TYPE 1 TASK 2"),
				new Task(3,1,null,"TYPE 1 TASK 3"),
				new Task(4,1,null,"TYPE 1 TASK 4"),
				new Task(5,2,"https://www.youtube.com/shorts/2Qd5aj5efrQ","TYPE 2 TASK 1"),
				new Task(6,2,"https://www.youtube.com/shorts/2Qd5aj5efrQ","TYPE 2 TASK 2"),
				new Task(7,2,"https://www.youtube.com/shorts/2Qd5aj5efrQ","TYPE 2 TASK 3"),
				new Task(8,2,"https://www.youtube.com/shorts/2Qd5aj5efrQ","TYPE 2 TASK 4"),
				new Task(9,3,"https://www.youtube.com/shorts/2Qd5aj5efrQ","TYPE 3 TASK 1"),
				new Task(10,3,"https://www.youtube.com/shorts/2Qd5aj5efrQ","TYPE 3 TASK 2"),
				new Task(11,3,"https://www.youtube.com/shorts/2Qd5aj5efrQ","TYPE 3 TASK 3"),
				new Task(12,3,"https://www.youtube.com/shorts/2Qd5aj5efrQ","TYPE 3 TASK 4")
		).collect(Collectors.toList());
		taskRepo.saveAll(tasks);
	}

	@PostConstruct
	public void setfalseintask(){
		List<AssignedTask> t=assignedTaskRepo.findAll();
		for (AssignedTask a : t) {
			Date current= new Date();
			if(a.getAssigneddate().before(current)) {
				AssignedTask te=a;
				te.setComplete(false);
				assignedTaskRepo.save(te);
			}
		}
	}
//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**").allowedOrigins("*");
//			}
//		};
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
