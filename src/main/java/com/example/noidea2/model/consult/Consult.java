package com.example.noidea2.model.consult;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "consult")
public class Consult {
    @EmbeddedId
    private ConsultId consultId;
    private Integer cid,tid;
    private String note;
    @Nullable
    private Date islastconsulted;
}
