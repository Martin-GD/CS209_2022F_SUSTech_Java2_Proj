package com.springboottest.demo.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.springboottest.demo.entity.*;
import com.springboottest.demo.entity.*;
import com.springboottest.demo.repository.*;
import com.springboottest.demo.repository.*;
import cn.hutool.http.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController

@CrossOrigin
public class AppHandler {

    private commit_Handler commit_handler = new commit_Handler();
    private developer_Handler developer_handler = new developer_Handler();
    private issue_Handler issue_handler = new issue_Handler();
    private release_Handler release_handler = new release_Handler();


    @Autowired
    private repo_contents_Repo repo_contents_repo;

    @GetMapping("/GetAllRepo")
    public List<repo_contents> GetAllRepo() {
        return repo_contents_repo.findAll();
    }

    @GetMapping("/Crawler_All_Info")
    public String Crawler_All_Info(String owner, String repo) {
        String pri_key = owner+"_"+repo;
//        try {
//
//            commit_handler.setData_Crawl_URL(owner,repo);
//            commit_handler.Crawl_Insert(pri_key);
//            developer_handler.setData_Crawl_URL(owner,repo);
//            developer_handler.Crawl_Insert(pri_key);
//            issue_handler.setData_Crawl_URL(owner, repo);
//            issue_handler.Crawl_Insert(pri_key);
//            release_handler.setData_Crawl_URL(owner, repo);
//            release_handler.Crawl_Insert(pri_key);
//        }catch (Exception e){
//            e.printStackTrace();
//            return "failed";
//        }
        repo_contents repo_new_con = new repo_contents(pri_key,owner,repo);
        repo_contents_repo.save(repo_new_con);
        return "success";
    }



}
