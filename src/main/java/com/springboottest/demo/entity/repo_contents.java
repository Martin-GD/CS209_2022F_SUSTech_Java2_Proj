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
    private int owner_following;
    private int owner_followers;
    private int public_repos;

    public repo_contents(String owner, String repo, int owner_following, int owner_followers, int public_repos) {
        this.pri_key = owner+"_"+repo;
        this.owner = owner;
        this.repo = repo;
        this.owner_following = owner_following;
        this.owner_followers = owner_followers;
        this.public_repos = public_repos;
    }

    public repo_contents(){}
}
