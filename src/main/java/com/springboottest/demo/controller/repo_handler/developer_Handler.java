//package com.springboottest.demo.controller.repo_handler;
//
//import com.google.gson.JsonArray;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import com.springboottest.demo.controller.*;
//import com.springboottest.demo.entity.*;
//import com.springboottest.demo.entity.Topic_Info.*;
//import com.springboottest.demo.repository.*;
//import com.springboottest.demo.repository.repo_Repo.*;
//import org.jsoup.Jsoup;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@RequestMapping("/repo_info/developer")
//@CrossOrigin
//public class developer_Handler {
//    @Autowired
//    private developer_info_repo developer_info_repo_ins;
//
//    @Autowired
//    private User_Account_Repo user_account_repo;
//
//    @Autowired
//    private Browse_History_Repo browse_history_repo;
//
//    @SuppressWarnings("all")
//    private final String topic_name = "developer";
//    @SuppressWarnings("all")
//    private final String Data_Crawl_URL = "https://api.stackexchange.com/2.3/collectives?order=desc&sort=name&site=stackoverflow";
//
//    @GetMapping("/Get_Popular_Collectives")
//    public List<Collectives_Info> Get_Popular_Collectives() {
//        user_account_repo.update_search_cnt(User_Account_Handler.Current_User_Account.getUser_name());
//        browse_history_repo.save(new Browse_History(
//                User_Account_Handler.Current_User_Account.getUser_name(),
//                topic_name,
//                Browse_History.df.format(System.currentTimeMillis())));
//
//        return collectives_info_repo.findAll();
//    }
//
//    @GetMapping("/Crawler_Insert")
//    public void Crawl_Insert() throws IOException {
//        collectives_info_repo.deleteAll();
//        String json_String_Content = Jsoup.connect(Data_Crawl_URL).ignoreContentType(true).get().text();
//
//        JsonObject jsonObject = JsonParser.parseString(json_String_Content).getAsJsonObject();
//        JsonArray jsonArray = jsonObject.get("items").getAsJsonArray();
//
//        List<Collectives_Info> list = new ArrayList<>();
//        for (JsonElement jsonElement : jsonArray) {
//            JsonObject jsonObject1 = jsonElement.getAsJsonObject();
//            list.add(new Collectives_Info(jsonObject1.get("name").getAsString(), jsonObject1.get("description").getAsString()));
//        }
//        collectives_info_repo.saveAll(list);
//    }
//}
