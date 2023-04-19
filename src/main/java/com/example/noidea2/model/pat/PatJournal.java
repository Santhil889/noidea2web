package com.example.noidea2.model.pat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@IdClass(PatJournalId.class)
@Entity(name = "journal")
public class PatJournal {
    @Id
    private  int pid;
    @Id
    private Date filledwhen;

    private String journaltext;
}
