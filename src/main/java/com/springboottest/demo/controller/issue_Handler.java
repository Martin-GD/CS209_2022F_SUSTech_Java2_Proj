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
@RequestMapping("/repo_Info/issue")
@CrossOrigin
public class issue_Handler {

    @Autowired
    private issue_info_Repo issue_info_repo;


    private String token = "CS209A_Proj1 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";

    @SuppressWarnings("all")
    private final String topic_name = "issue";
    @SuppressWarnings("all")
    private final String Data_Crawl_URL = "https://api.github.com/repos/Fndroid/clash_for_windows_pkg/issues?state=all&per_page=100&page=";

    @GetMapping("/Get_issue")
    public List<issue> Get_issue() {
        return issue_info_repo.findAll();
    }

    @GetMapping("/Crawler_Insert")
    public String Crawl_Insert(String owner_repo) throws IOException {
//        issue_info_repo.deleteAll();
        List<issue> list = new ArrayList<>();
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
                list.add(new issue(jsonObject1.get("number").getAsInt(), owner_repo,
                        jsonObject1.get("id").getAsString(),
                        ((JsonObject)jsonObject1.get("user")).get("login").getAsString(),
                        jsonObject1.get("state").getAsString(),
                        jsonObject1.get("created_at").getAsString(),
                        jsonObject1.get("updated_at").getAsString(),
                        closed.isJsonNull() ? " ":closed.getAsString(),
//                    jsonObject1.get("closed_at") == null ? " ":jsonObject1.get("closed_at").getAsString(),
//                    jsonObject1.get("closed_at").getAsString(),
                        jsonObject1.get("title").getAsString(),
                        jsonObject1.get("comments").getAsString()));
            }
            cnt++;
            url = Data_Crawl_URL+cnt;
            json_String_Content = HttpUtil.createGet(url).header("Authorization",token).execute().body();
        }while (!json_String_Content.equals("[\n\n]\n"));


        issue_info_repo.saveAll(list);
        return "success";
    }
}
