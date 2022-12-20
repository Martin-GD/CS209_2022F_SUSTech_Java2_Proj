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
//@IdClass(issue.class)
public class issue {

  @Id
  private String pri_key;

  private int number;

  private String owner_repo;
  private String id;
  private String user_login;
  private String state;
  private Timestamp created_at;
  private Timestamp updated_at;
  private Timestamp closed_at;
  private String title;
  private String comment;
  private String body;

  public issue(int number, String owner_repo, String id,
               String user_login, String state,
               String created_at, String updated_at, String closed_at,
               String title, String comment, String body) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    this.pri_key = owner_repo + number;
    this.number = number;
    this.owner_repo = owner_repo;
    this.id = id;
    this.user_login = user_login;
    this.state = state;
    Timestamp create = null;
    Timestamp update = null;
    Timestamp close = null;
    try {
      create = new Timestamp(sdf.parse(created_at.replace("T", " ").replace("Z", "")).getTime());
      update = new Timestamp(sdf.parse(updated_at.replace("T", " ").replace("Z", "")).getTime());
      close = new Timestamp(sdf.parse(closed_at.replace("T", " ").replace("Z", "")).getTime());

    } catch (Exception e) {
//            throw new RuntimeException(e);
    }
    this.created_at = create;
    this.updated_at = update;
    this.closed_at = close;
    this.title = title;
    this.comment = comment;
    this.body = body;
  }

  public issue() {
  }
}
