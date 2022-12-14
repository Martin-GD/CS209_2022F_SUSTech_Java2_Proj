package com.springboottest.demo.entity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.text.SimpleDateFormat;

@Entity
@Data
public class developer {
    @Id
    private String login;
    private String id;
    private int contributions;
    private String type;

    public developer(String login, String id, int contributions, String type) {
        this.login = login;
        this.id = id;
        this.contributions = contributions;
        this.type = type;
    }
    public developer(){}
}
