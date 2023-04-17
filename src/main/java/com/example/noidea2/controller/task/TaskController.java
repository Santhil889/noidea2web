package com.example.noidea2.controller.task;

import com.example.noidea2.model.auth.Creds;
import com.example.noidea2.model.task.AssignedTask;
import com.example.noidea2.model.task.Task;
import com.example.noidea2.repo.auth.CredsRepo;
import com.example.noidea2.repo.consult.ConsultRepo;
import com.example.noidea2.repo.doc.DocRepo;
import com.example.noidea2.repo.pat.PatRepo;
import com.example.noidea2.repo.ques.QuestionRepo;
import com.example.noidea2.repo.task.AssignedTaskRepo;
import com.example.noidea2.repo.task.TaskRepo;
import com.example.noidea2.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class TaskController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private ConsultRepo consultRepo;
    @Autowired
    private DocRepo docRepo;
    @Autowired
    private PatRepo patRepo;
    @Autowired
    private CredsRepo credsRepo;
    @Autowired
    private QuestionRepo questionRepo;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AssignedTaskRepo assignedTaskRepo;
    @Autowired
    private TaskRepo taskRepo;

    @PostMapping("/task/add")
    public Integer addtask(@RequestHeader("Authorization") String token, @RequestBody Task task) throws Exception{
        token = token.substring(7);
        String uname = jwtUtil.extractUsername(token);
        Creds c = credsRepo.findByUsername(uname);
        if(c.getRole()!=1) throw new Exception("only doctor can add task");
        return taskRepo.save(task).getTid();
    }

    @PostMapping("/task/assign")
    public AssignedTask assigntask(@RequestHeader("Authorization") String token, @RequestBody AssignedTask assignedTask) throws Exception{
        token = token.substring(7);
        String uname = jwtUtil.extractUsername(token);
        Creds c = credsRepo.findByUsername(uname);
        if(c.getRole() != 1) throw new Exception("not doctor");
        int did=c.getId();
        if(assignedTask.getDid() != did) throw new Exception("Wrong Doctor");
        return assignedTaskRepo.save(assignedTask);
//        return "added task successfully";
    }

    @PostMapping("/remove/task")
    public String removeasstask(@RequestHeader("Authorization") String token,@RequestBody DeleteRequest deleteRequest) throws Exception{
        try{
            token = token.substring(7);
            String uname = jwtUtil.extractUsername(token);
            Creds c = credsRepo.findByUsername(uname);
            if (c.getRole() != 1) {
                    throw new Exception("Invalid Doctor");
            }
            AssignedTask t=assignedTaskRepo.getByPidAndDidAndTid(deleteRequest.getPid(),deleteRequest.getDid(),deleteRequest.getTid());
            assignedTaskRepo.delete(t);
            return "deleted";
        }catch (Exception e){
            throw e;
        }
    }

    @GetMapping("/get/tasks/{pid}")
    public List<AssignedTask> gettask(@RequestHeader("Authorization") String token,@PathVariable Integer pid ) throws Exception{
        try{
            token = token.substring(7);
            String uname = jwtUtil.extractUsername(token);
            Creds c = credsRepo.findByUsername(uname);
            ArrayList<AssignedTask> arr = new ArrayList<AssignedTask>();
            if (c.getRole() == 2) {
                if (c.getId() != pid)
                    throw new Exception("Invalid patient");
                return assignedTaskRepo.getAllByPid(pid);
            }
            else if (c.getRole() == 1) {
                if (consultRepo.getByConsultId_Pid(pid).getConsultId().getDid() != c.getId())
                    throw new Exception("Not appropriate doctor");
                int did = consultRepo.getByConsultId_Pid(pid).getConsultId().getDid();
                return assignedTaskRepo.getAllByDidAndPid(did, pid);
            }
            else throw new Exception("Not assicible by admin");
        }catch (Exception e){
            throw e;
        }
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class DeleteRequest{
    private int pid;
    private int did;
    private int tid;
}