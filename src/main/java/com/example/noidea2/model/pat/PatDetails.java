package com.example.noidea2.model.pat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "patDetails")
public class PatDetails {
    @Id
    private int pid;

    private String name,phone,address,email,photolink;

    @Nullable
    private Integer score;

    private int age,gender;
}
