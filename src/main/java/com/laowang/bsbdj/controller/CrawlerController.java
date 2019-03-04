package com.laowang.bsbdj.controller;

import com.github.pagehelper.Page;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.laowang.bsbdj.Service.CrawlerService;
import com.laowang.bsbdj.entity.Content;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    @RequestMapping("/preview/{cid}.html")
    public ModelAndView preview(@PathVariable("cid") Long contentId){
         Map ret = crawlerService.previewDate(contentId);
        Content content = (Content)ret.get("content");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fmtPasstime = null;
        try {
            fmtPasstime = sdf.parse(content.getPasstime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ModelAndView mav = new ModelAndView("preview");
        mav.addAllObjects(ret);
        mav.addObject("fmtPasstime" , this.convertTimeToFormat(fmtPasstime));
        return mav;
    }

    /**
     * 将一个时间戳转换成提示性时间字符串，如刚刚，1秒前
     *
     */
    public String convertTimeToFormat(Date d) {
        long curTime = System.currentTimeMillis() / (long) 1000;
        long timeStamp = d.getTime()/1000l;
        long time = curTime - timeStamp;

        if (time < 60 && time >= 0) {
            return "刚刚";
        } else if (time >= 60 && time < 3600) {
            return time / 60 + "分钟前";
        } else if (time >= 3600 && time < 3600 * 24) {
            return time / 3600 + "小时前";
        } else if (time >= 3600 * 24 && time < 3600 * 24 * 30) {
            return time / 3600 / 24 + "天前";
        } else if (time >= 3600 * 24 * 30 && time < 3600 * 24 * 30 * 12) {
            return time / 3600 / 24 / 30 + "个月前";
        } else if (time >= 3600 * 24 * 30 * 12) {
            return time / 3600 / 24 / 30 / 12 + "年前";
        } else {
            return "刚刚";
        }
    }
}
