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
@RequestMapping("/repo_Info/issue")
@CrossOrigin
public class issue_Handler {

    @Autowired
    private issue_info_Repo issue_info_repo;


    private String token = "CS209A_Proj1 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";

    @SuppressWarnings("all")
    private final String topic_name = "issue";
    @SuppressWarnings("all")
    private String Template = "https://api.github.com/repos/{0}/{1}/issues?state=all&per_page=100&page=";
    private String Data_Crawl_URL = "";

    @GetMapping("/setData_Crawl_URL")
    public void setData_Crawl_URL(String owner, String repo){
        Data_Crawl_URL = MessageFormat.format(Template,owner,repo);
    }

    @GetMapping("/Get_issue")
    public List<issue> Get_issue(String owner_repo) {
        return issue_info_repo.findByOwner_repo(owner_repo);
    }

    @GetMapping("/Get_issue_number_open")
    public int Get_issue_number_open(String owner_repo) {
        return issue_info_repo.findNumByState(owner_repo,"open");
    }

    @GetMapping("/Get_issue_number_closed")
    public int Get_issue_number_closed(String owner_repo) {
        return issue_info_repo.findNumByState(owner_repo,"closed");
    }

    @GetMapping("/Get_issue_time_statistic")
    public double[] Get_issue_time_statistic(String owner_repo) {
        List<issue> list =  issue_info_repo.findByOwner_repoAndState(owner_repo,"closed");
        double[] time = new double[list.size()];
        double sum = 0;
        double min = Double.MAX_VALUE;
        double max = -1;
        for (int i = 0; i < list.size(); i++) {
            issue t = list.get(i);
            time[i] = (double) (t.getClosed_at().getTime()-t.getCreated_at().getTime())/60/1000;
            if (time[i]>max)
                max = time[i];
            if (time[i]<min)
                min = time[i];
            sum += time[i];
        }
        double average = sum/list.size();
        double range = max-min;
        double variance = 0;
        for (int i = 0; i < list.size(); i++) {
            variance += (time[i]-average)*(time[i]-average);
        }
        variance = variance/list.size();
        return new double[]{average, range, variance};
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
                JsonElement body = jsonObject1.get("body");
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
                        jsonObject1.get("comments").getAsString(),
                        body.isJsonNull() ? " ":body.getAsString()));
            }
            cnt++;
            url = Data_Crawl_URL+cnt;
            json_String_Content = HttpUtil.createGet(url).header("Authorization",token).execute().body();
        }while (!json_String_Content.equals("[\n\n]\n"));


        issue_info_repo.saveAll(list);
        return "success";
    }
}
