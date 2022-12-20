package com.springboottest.demo.controller;

import cn.hutool.http.HttpRequest;
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

@CrossOrigin
public class AppHandler {

  private String Template = "https://api.github.com/users/{0}";
  private String Data_Crawl_URL = "";
  //    private String token = "CS209A_Proj1 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";
  private String token = "token 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";
//    private String token = "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";

  @Autowired
  private repo_contents_Repo repo_contents_repo;

  @GetMapping("/GetAllRepo")
  public List<repo_contents> GetAllRepo() {
    return repo_contents_repo.findAll();
  }

  @GetMapping("/Crawler_RepoOwner_Info")
  public String Crawler_All_Info(String owner, String repo) {
    String pri_key = owner + "_" + repo;
    List<repo_contents> list = new ArrayList<>();
    Data_Crawl_URL = MessageFormat.format(Template, owner);
    HttpRequest httpRequest = HttpUtil.createGet(Data_Crawl_URL).header("Authorization", token);
    String json_String_Content = HttpUtil.createGet(Data_Crawl_URL).header("Authorization", token).execute().body();
    json_String_Content = "[" + json_String_Content + "]";
    JsonParser parser = new JsonParser();
    JsonElement element = parser.parse(json_String_Content);
    JsonArray jsonArray = element.getAsJsonArray();
    for (JsonElement jsonElement : jsonArray) {
      JsonObject jsonObject1 = jsonElement.getAsJsonObject();
      JsonElement following = jsonObject1.get("following");
      JsonElement followers = jsonObject1.get("followers");
      JsonElement public_repos = jsonObject1.get("public_repos");
      list.add(new repo_contents(owner, repo,
          following.isJsonNull() ? 0 : following.getAsInt(),
          followers.isJsonNull() ? 0 : followers.getAsInt(),
          public_repos.isJsonNull() ? 0 : public_repos.getAsInt()
      ));
    }

    repo_contents_repo.saveAll(list);
    return "success";
  }


}
