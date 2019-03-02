package com.laowang.bsbdj.controller;

import com.github.pagehelper.Page;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.laowang.bsbdj.Service.CrawlerService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CrawlerController {

    @Resource
    private CrawlerService crawlerService;

    @RequestMapping("/c")
    @ResponseBody
    public String crawl(){
        crawlerService.crawlerRunner();
            return null;
    }
    @RequestMapping("/etl")
    @ResponseBody
    public String etl(){
        crawlerService.etl();
        return null;
    }
    @RequestMapping("/")
    public String index(){

        return "manager";
    }
    @RequestMapping("/list")
    @ResponseBody
    public Map list(Integer page,Integer limit,Integer channelId,String contentType,String keyword){
        Page<Map> p = crawlerService.findAll(page,limit,channelId,contentType,keyword);
        Map result = new HashMap();
        result.put("code",0);
        result.put("msg","");
        result.put("count",p.getTotal());
        result.put("data",p.getResult());
        return  result;

    }
    @RequestMapping("/delete")
    @ResponseBody
    public Map delete(Long contentId){
        crawlerService.delect(contentId);
        Map result = new HashMap();
        result.put("code",0);
        result.put("msg","删除成功！");
        return result;
    }
}
