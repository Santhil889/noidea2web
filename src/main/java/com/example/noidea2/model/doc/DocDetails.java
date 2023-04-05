package com.example.noidea2.model.doc;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "docDetails")
public class DocDetails {
    @Id
    private Integer did;
    private String name;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date bDate;
    private String email;
    @Column(unique = true)
    private String lic;
    private String qual;
    private String specs;
}
