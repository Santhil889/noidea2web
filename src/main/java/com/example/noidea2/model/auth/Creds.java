package com.example.noidea2.model.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.management.relation.Role;
import javax.persistence.*;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "docCreds")
public class Creds {
    @Id
    private Integer id;
    private String username;
    private String email;
    private String password;
    private Integer role;
}
