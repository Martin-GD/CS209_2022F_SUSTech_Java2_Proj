package com.springboottest.demo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class repo_contents {
    @Id
    private String pri_key;

    private String owner;
    private String repo;

    public repo_contents(String pri_key, String owner, String repo) {
        this.pri_key = pri_key;
        this.owner = owner;
        this.repo = repo;
    }
    public repo_contents(){}
}
