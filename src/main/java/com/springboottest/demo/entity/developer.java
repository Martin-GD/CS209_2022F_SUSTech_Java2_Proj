package com.springboottest.demo.entity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.text.SimpleDateFormat;

@Entity
@Data
public class developer {
    @Id
    private String pri_key;
    private String owner_repo;
    private String login;
    private String id;
    private int contributions;
    private String type;

    public developer(String owner_repo, String login, String id, int contributions, String type) {
        this.pri_key = owner_repo+login;
        this.owner_repo = owner_repo;
        this.login = login;
        this.id = id;
        this.contributions = contributions;
        this.type = type;
    }

    public developer(){}
}
