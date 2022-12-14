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
public class release {

    @Id
    private String pri_key;
    private String owner_repo;
    private String id;
    private String author_login;
    private String name;
    private Timestamp created_at;
    private Timestamp published_at;

    public release(String owner_repo, String id, String author_login, String name, String created_at, String published_at) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.pri_key = owner_repo+id;
        this.owner_repo = owner_repo;
        this.id = id;
        this.author_login = author_login;
        this.name = name;
        Timestamp create = null;
        Timestamp publish = null;
        try {
            create = new Timestamp(sdf.parse(created_at.replace("T"," ").replace("Z","")).getTime());
            publish = new Timestamp(sdf.parse(published_at.replace("T"," ").replace("Z","")).getTime());
        } catch (Exception e) {
        }
        this.created_at = create;
        this.published_at = publish;
    }

    public release(){}
}
