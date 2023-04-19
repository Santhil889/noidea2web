package com.example.noidea2.model.pat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "patDetails")
public class PatDetails {
    @Id
    @NotNull
    private int pid;

    private String name,phone,address,email,photolink;

    @Nullable
    private Integer score;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date bDate;

    private int age;
    private String bloodgroup;

    private int gender,height,weight;

    private String journal;
}
