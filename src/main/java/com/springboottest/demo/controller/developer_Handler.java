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
//token: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
//token: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

@RestController
@RequestMapping("/repo_Info/developer")
@CrossOrigin
public class developer_Handler {

    @Autowired
    private developer_info_Repo developer_info_repo;


    private String token = "CS209A_Proj1 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";

    @SuppressWarnings("all")
    private final String topic_name = "developer";
    @SuppressWarnings("all")
    private final String Data_Crawl_URL = "https://api.github.com/repos/Fndroid/clash_for_windows_pkg/contributors?anon=0&per_page=100&page=";

    @GetMapping("/Get_developer")
    public List<developer> Get_developer(String owner_repo) {
        return developer_info_repo.findByOwner_repo(owner_repo);
    }

    @GetMapping("/Crawler_Insert")
    public String Crawl_Insert(String owner_repo) throws IOException {
//        issue_info_repo.deleteAll();
        List<developer> list = new ArrayList<>();
        int cnt = 1;
        String url = Data_Crawl_URL+cnt;

        String json_String_Content = HttpUtil.createGet(url).header("Authorization",token).execute().body();
//        String json_String_Content = Jsoup.connect(Data_Crawl_URL).ignoreContentType(true).get().text();
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
            url = Data_Crawl_URL+cnt;
            json_String_Content = HttpUtil.createGet(url).header("Authorization",token).execute().body();
        }while (!json_String_Content.equals("[\n\n]\n"));


        developer_info_repo.saveAll(list);
        return "success";
    }
}
