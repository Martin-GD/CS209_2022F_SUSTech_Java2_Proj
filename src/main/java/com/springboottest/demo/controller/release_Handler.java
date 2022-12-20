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
//token: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
//token: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

@RestController
@RequestMapping("/repo_Info/release")
@CrossOrigin
public class release_Handler {

    @Autowired
    private release_info_Repo release_info_repo;

    private String token = "token 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";

//    private String token = "CS209A_Proj1 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";

    @SuppressWarnings("all")
    private final String topic_name = "release";
    @SuppressWarnings("all")
    private String Template = "https://api.github.com/repos/{0}/{1}/releases?per_page=100&page=";
    private String Data_Crawl_URL = "";

    @GetMapping("/setData_Crawl_URL")
    public void setData_Crawl_URL(String owner, String repo){
        Data_Crawl_URL = MessageFormat.format(Template,owner,repo);
    }

    @GetMapping("/Get_release")
    public List<release> Get_release(String owner_repo) {
        return release_info_repo.findByOwner_repo(owner_repo);
    }

    @GetMapping("/Get_release_number")
    public int Get_release_number(String owner_repo) {
        return release_info_repo.findNumByOwner_repo(owner_repo);
    }

    @GetMapping("/Get_commit_number_in_release")
    public List<commit_number_in_release> Get_commit_number_in_release(String owner_repo) {
        List<commit_number_in_release> res = new ArrayList<>();
        List<release> list = release_info_repo.findByOwner_repo(owner_repo);
        for (int i = 0; i < list.size() - 1; i++) {
            res.add(new commit_number_in_release(list.get(i).getName(),
                    list.get(i + 1).getName(), release_info_repo
                    .findCommitNumInReleases(owner_repo, list.get(i).getPublished_at(), list.get(i + 1).getPublished_at())));
        }
        return res;
    }

    @GetMapping("/Crawler_Insert")
    public String Crawl_Insert(String owner_repo) throws IOException {
//        issue_info_repo.deleteAll();
        List<release> list = new ArrayList<>();
        int cnt = 1;
        String url = Data_Crawl_URL + cnt;

        String json_String_Content = HttpUtil.createGet(url).header("Authorization", token).execute().body();
//        String json_String_Content = Jsoup.connect(Data_Crawl_URL).ignoreContentType(true).get().text();
        do {
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(json_String_Content);
            JsonArray jsonArray = element.getAsJsonArray();

            for (JsonElement jsonElement : jsonArray) {
                JsonObject jsonObject1 = jsonElement.getAsJsonObject();
                list.add(new release(owner_repo, jsonObject1.get("id").getAsString(),
                        ((JsonObject) jsonObject1.get("author")).get("login").getAsString(),
                        jsonObject1.get("name").getAsString(),
                        jsonObject1.get("created_at").getAsString(),
                        jsonObject1.get("published_at").getAsString()
                ));
            }
            cnt++;
            url = Data_Crawl_URL + cnt;
            json_String_Content = HttpUtil.createGet(url).header("Authorization", token).execute().body();
        } while (!json_String_Content.equals("[\n\n]\n"));


        release_info_repo.saveAll(list);
        return "success";
    }
}
