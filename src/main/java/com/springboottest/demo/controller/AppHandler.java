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

@CrossOrigin
public class AppHandler {

    private String Template = "https://api.github.com/users/{0}";
    private String Data_Crawl_URL = "";
    private String token = "CS209A_Proj1 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";
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
        String json_String_Content = HttpUtil.createGet(Data_Crawl_URL).header("Authorization", token).execute().body();

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(json_String_Content);
        JsonArray jsonArray = element.getAsJsonArray();

        for (JsonElement jsonElement : jsonArray) {
            JsonObject jsonObject1 = jsonElement.getAsJsonObject();

            list.add(new repo_contents(owner,repo,
                    jsonObject1.get("following").getAsInt(),
                    jsonObject1.get("followers").getAsInt(),
                    jsonObject1.get("public_repos").getAsInt()
                    ));
        }

        repo_contents_repo.saveAll(list);
        return "success";
    }


}
