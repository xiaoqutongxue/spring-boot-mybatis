package com.atguigu.springboot.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfig {

    // 把Druid配置放入到环境中，根据application.yml,因为Druid数据源不属于spring.datasource，所以需要配置进去
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druid(){
        return new DruidDataSource();
    }

    // 配置Druid的监控
    // 1.配置一个管理后台的Servlet
    @Bean
    public ServletRegistrationBean StatViewServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");//druid 下的所有请求
        Map<String,String> initparms = new HashMap<>();

        initparms.put("loginUsername","admin");
        initparms.put("loginPassword","admin");
        // allow 这个key对应的允许谁访问的意思，不写就是代表所有
        initparms.put("allow","");
        bean.setInitParameters(initparms);
        return bean;
    }

    // 配置一个web监控的filter
    @Bean
    public FilterRegistrationBean WebStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        Map<String,String> initparms = new HashMap<>();

        // exclusions这个key是指明那些不进行拦截，代表排除那些不去拦截
        initparms.put("exclusions","*.js,*.css,/druid/*");


        // setInitParameters 初始化一些参数
        bean.setInitParameters(initparms);
        // 拦截所有请求
        bean.setUrlPatterns(Arrays.asList("/*"));
        return bean;

    }
}
