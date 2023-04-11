package com.example.noidea2.repo.task;

import com.example.noidea2.model.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepo extends JpaRepository<Task,Integer> {

}
