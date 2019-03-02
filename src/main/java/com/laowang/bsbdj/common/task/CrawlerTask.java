package com.laowang.bsbdj.common.task;

import com.laowang.bsbdj.Service.CrawlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

//组件 @Controller @Service  @Reposetory @Component
@Component  //会被IOC容器加载
public class CrawlerTask {
    Logger logger = LoggerFactory.getLogger(CrawlerTask.class);
    @Resource
    private CrawlerService crawlerService;
   // 任务执行方法
    //@Scheduled 注解用于指定当前方法是一个任务调度 cron表达式指定执行的间隔
    // * * * * * ？ 每秒执行一次
    //秒     分      时      日     月     星期
    //*代表任意时间    具体的数字代表精确值      ,用于多个值     /用于固定间隔    - 范围  ?忽略
    //0  * 23 * * ?     每天23点~24点之间每分钟执行一次
    //0  0 8-18  * ?    每天早上8点到下午六点，0份0秒准时执行一次
    //0  0  0  */2 * ?  0  2  4  6 ... 22  0点0分执行

    @Scheduled(cron = "0 0 * * * ? ")
    public  void CrawlerRunner(){

        //1. 抓取原始数据
        // URL模板
        String[] templates = new String[]{
                "http://c.api.budejie.com/topic/list/jingxuan/1/budejie-android-6.9.4/{np}-20.json?market=xiaomi&ver=6.9.4&visiting=&os=6.0&appname=baisibudejie&client=android&udid=864141036474044&mac=02%3A00%3A00%3A00%3A00%3A00"
                ,"http://c.api.budejie.com/topic/list/jingxuan/41/budejie-android-6.9.4/{np}-20.json?market=xiaomi&ver=6.9.4&visiting=&os=6.0&appname=baisibudejie&client=android&udid=864141036474044&mac=02%3A00%3A00%3A00%3A00%3A00\n"
                ,"http://c.api.budejie.com/topic/list/jingxuan/10/budejie-android-6.9.4/{np}-20.json?market=xiaomi&ver=6.9.4&visiting=&os=6.0&appname=baisibudejie&client=android&udid=864141036474044&mac=02%3A00%3A00%3A00%3A00%3A00\n"
                ,"http://c.api.budejie.com/topic/list/jingxuan/29/budejie-android-6.9.4/{np}-20.json?market=xiaomi&ver=6.9.4&visiting=&os=6.0&appname=baisibudejie&client=android&udid=864141036474044&mac=02%3A00%3A00%3A00%3A00%3A00\n"
                ,"http://s.budejie.com/topic/list/remen/1/budejie-android-6.9.4/{np}-20.json?market=xiaomi&ver=6.9.4&visiting=&os=6.0&appname=baisibudejie&client=android&udid=864141036474044&mac=02%3A00%3A00%3A00%3A00%3A00"};
        for (int i = 0;i<templates.length;i++){
            logger.info("正在抓取第{}个模块的数据",(i+1));
            String template = templates[i];
            Map conText = new HashMap();
            conText.put("count",0);
            crawlerService.crawler(conText,template,"0",(i+1));
        }
        //对Source表数据进行处理
        crawlerService.etl();
    }
}
