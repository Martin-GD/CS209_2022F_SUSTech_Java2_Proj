package com.springboottest.demo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;


@Data
@Entity
public class commit_number_in_release {

  @Id
  private String pri_key;

  private String pre_release;
  private String now_release;
  private int cnt;

  public commit_number_in_release(String pre_release, String now_release, int cnt) {
    this.pri_key = pre_release + "_" + now_release;
    this.pre_release = pre_release;
    this.now_release = now_release;
    this.cnt = cnt;
  }

  public commit_number_in_release() {
  }
}
