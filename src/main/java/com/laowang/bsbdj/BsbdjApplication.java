package com.laowang.bsbdj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//扫描这个mapper包，自动生成mapper对应的实现类
@MapperScan("com.laowang.bsbdj.mapper")
@EnableScheduling //@EnableScheduling 启动调度任务
public class BsbdjApplication {

	public static void main(String[] args) {
		SpringApplication.run(BsbdjApplication.class, args);
	}

}
