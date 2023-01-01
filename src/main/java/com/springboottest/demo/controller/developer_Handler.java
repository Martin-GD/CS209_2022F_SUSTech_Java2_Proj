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
@RequestMapping("/repo_Info/developer")
@CrossOrigin
public class developer_Handler {

  @Autowired
  private developer_info_Repo developer_info_repo;


  private String token = "token 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";

  @SuppressWarnings("all")
  private final String topic_name = "developer";
  @SuppressWarnings("all")
  private String Template = "https://api.github.com/repos/{0}/{1}/contributors?anon=0&per_page=100&page=";
  private String Data_Crawl_URL = "";

  @GetMapping("/setData_Crawl_URL")
  public void setData_Crawl_URL(String owner, String repo) {
    Data_Crawl_URL = MessageFormat.format(Template, owner, repo);
  }

  @GetMapping("/Get_developer")
  public List<developer> Get_developer(String owner_repo) {
    return developer_info_repo.findByOwner_repo(owner_repo);
  }

  @GetMapping("/Get_developer_number")
  public int Get_developer_number(String owner_repo) {
    return developer_info_repo.findNumberOfDevelopers(owner_repo);
  }

  @GetMapping("/Get_developer_top5")
  public List<developer> Get_developer_top5(String owner_repo) {
    return developer_info_repo.findTop5(owner_repo);
  }

  @GetMapping("/Crawler_Insert")
  public String Crawl_Insert(String owner, String repo) throws IOException {
    String owner_repo = owner + "_" + repo;
    Data_Crawl_URL = MessageFormat.format(Template, owner, repo);
    List<developer> list = new ArrayList<>();
    int cnt = 1;
    String url = Data_Crawl_URL + cnt;

    String json_String_Content = HttpUtil.createGet(url).header("Authorization", token).execute().body();
    do {
      JsonParser parser = new JsonParser();
      JsonElement element = parser.parse(json_String_Content);
      JsonArray jsonArray = element.getAsJsonArray();

      for (JsonElement jsonElement : jsonArray) {
        JsonObject jsonObject1 = jsonElement.getAsJsonObject();

        JsonElement closed = jsonObject1.get("closed_at");
        list.add(new developer(owner_repo,
            jsonObject1.get("login").getAsString(),
            jsonObject1.get("id").getAsString(),
            jsonObject1.get("contributions").getAsInt(),
            jsonObject1.get("type").getAsString()));
      }
      cnt++;
      url = Data_Crawl_URL + cnt;
      json_String_Content = HttpUtil.createGet(url).header("Authorization", token).execute().body();
    } while (!json_String_Content.equals("[\n\n]\n"));


    developer_info_repo.saveAll(list);
    return "success";
  }
}
