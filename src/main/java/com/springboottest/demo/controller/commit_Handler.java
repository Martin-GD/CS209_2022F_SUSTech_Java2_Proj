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
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/repo_Info/commit")
@CrossOrigin
public class commit_Handler {

  @Autowired
  private commit_info_Repo commit_info_repo;


  private String token = "token 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";


  @SuppressWarnings("all")
  private final String topic_name = "commit";
  @SuppressWarnings("all")
  private String Data_Crawl_URL = "";
  private String Template = "https://api.github.com/repos/{0}/{1}/commits?per_page=100&page=";


  @GetMapping("/setData_Crawl_URL")
  public void setData_Crawl_URL(String owner, String repo) {
    Data_Crawl_URL = MessageFormat.format(Template, owner, repo);
  }

  @GetMapping("/Get_commit")
  public List<commit> Get_commit(String owner_repo) {
    return commit_info_repo.findByOwner_repo(owner_repo);
  }

  @GetMapping("/Get_commitNum")
  public int Get_commitNum(String owner_repo) {
    return commit_info_repo.findNumByOwner_repo(owner_repo);
  }

  @GetMapping("/Get_commit_time_distribution")
  public int[] Get_commit_time_distribution(String owner_repo) {
    int[] res = new int[3];
    res[0] = commit_info_repo.findMorningNum(owner_repo);
    res[1] = commit_info_repo.findAfternoonNum(owner_repo);
    res[2] = commit_info_repo.findNightNum(owner_repo);
    return res;
  }


  @GetMapping("/Crawler_Insert")
  public String Crawl_Insert(String owner, String repo) throws IOException {
    String owner_repo = owner + "_" + repo;
    Data_Crawl_URL = MessageFormat.format(Template, owner, repo);
    List<commit> list = new ArrayList<>();
    int cnt = 1;
    String url = Data_Crawl_URL + cnt;

    String json_String_Content = HttpUtil.createGet(url).header("Authorization", token).execute().body();
    do {
      JsonParser parser = new JsonParser();
      JsonElement element = parser.parse(json_String_Content);
      JsonArray jsonArray = element.getAsJsonArray();

      for (JsonElement jsonElement : jsonArray) {
        JsonObject jsonObject1 = jsonElement.getAsJsonObject();

        JsonObject commit = ((JsonObject) jsonObject1.get("commit"));
        JsonObject author = ((JsonObject) commit.get("author"));
        JsonElement messageElement = commit.get("message");
        String message = "";
        if (messageElement.isJsonNull())
          message = "";
        else message = messageElement.getAsString();
        list.add(new commit(owner_repo,
            jsonObject1.get("sha").getAsString(),
            author.get("name").getAsString(),
            author.get("date").getAsString(),
            message));
      }
      cnt++;
      url = Data_Crawl_URL + cnt;
      json_String_Content = HttpUtil.createGet(url).header("Authorization", token).execute().body();
    } while (!json_String_Content.equals("[\n\n]\n"));


    commit_info_repo.saveAll(list);
    return "success";
  }

}
