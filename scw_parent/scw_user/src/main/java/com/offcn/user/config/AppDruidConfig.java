package com.offcn.user.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class AppDruidConfig {
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource getDataSource() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setFilters("stat");
        return dataSource;
    }

    @Bean
    public ServletRegistrationBean getServletRegistrationBean() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map map =  new HashMap();
        map.put("loginUsername","admin");
        map.put("loginPassword","123456");
        map.put("allow","");
        bean.setInitParameters(map);
        return bean;
    }

    @Bean
    public FilterRegistrationBean getFilterRegistrationBean(){
        FilterRegistrationBean filterBean = new FilterRegistrationBean();
        filterBean.setFilter(new WebStatFilter());
        Map map = new HashMap();
        map.put("exclusions","*.js,*.css,/druid/*");
        filterBean.setInitParameters(map);
        filterBean.setUrlPatterns(Arrays.asList("/*"));
        return filterBean;
    }

}
