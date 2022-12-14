package com.springboottest.demo.entity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
@Data
@Entity
public class commit {

    @Id
    private String pri_key;

    private String owner_repo;
    private String sha;
    private String author_name;
    private Timestamp date;
    private String message;

    public commit(String owner_repo, String sha, String author_name, String date, java.lang.String message) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.pri_key = owner_repo+sha;
        this.owner_repo = owner_repo;
        this.sha = sha;
        this.author_name = author_name;
        Timestamp create = null;
        try {
            create = new Timestamp(sdf.parse(date.replace("T"," ").replace("Z","")).getTime());

        } catch (Exception e) {
        }
        this.date = create;
        this.message = message;
    }

    public commit(){}
}
