package com.tygps.chart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;

//@RestController
//@EnableAutoConfiguration
@SpringBootApplication
@EnableTransactionManagement
public class ChartApplication {

    @RequestMapping("/")
    public String index(){
        return "表单定义工具";
    }

    public static void main(String[] args) {
        SpringApplication.run(ChartApplication.class, args);
    }
}
