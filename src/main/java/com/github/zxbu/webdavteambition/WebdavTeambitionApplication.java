package com.github.zxbu.webdavteambition;

import com.github.zxbu.webdavteambition.filter.ErrorFilter;
import com.github.zxbu.webdavteambition.store.AliYunDriverFileSystemStore;
import com.github.zxbu.webdavteambition.store.BaiduDriverFileSystemStore;
import com.github.zxbu.webdavteambition.store.DriverFileSystemStore;
import net.sf.webdav.WebdavServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.LinkedHashMap;
import java.util.Map;

@SpringBootApplication
@EnableScheduling
public class WebdavTeambitionApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebdavTeambitionApplication.class, args);
    }

/*    @Bean
    public ServletRegistrationBean<WebdavServlet> myServlet(){
        ServletRegistrationBean<WebdavServlet> servletRegistrationBean = new ServletRegistrationBean<>(new WebdavServlet(), "/*");
        Map<String, String> inits = new LinkedHashMap<>();
        //inits.put("ResourceHandlerImplementation", AliYunDriverFileSystemStore.class.getName());
        inits.put("ResourceHandlerImplementation1", BaiduDriverFileSystemStore.class.getName());
//        inits.put("ResourceHandlerImplementation", LocalFileSystemStore.class.getName());
        inits.put("rootpath1", "./");
        inits.put("storeDebug1", "1");
        servletRegistrationBean.setInitParameters(inits);
        return servletRegistrationBean;
    }*/

    @Bean
    public ServletRegistrationBean<WebdavServlet> aliYunDriverServlet(){
        ServletRegistrationBean<WebdavServlet> servletRegistrationBean = new ServletRegistrationBean<>(new WebdavServlet(), "/*");
        Map<String, String> inits = new LinkedHashMap<>();
        inits.put("ResourceHandlerImplementation", DriverFileSystemStore.class.getName());
        //inits.put("ResourceHandlerImplementation", BaiduDriverFileSystemStore.class.getName());
        //inits.put("ResourceHandlerImplementation", LocalFileSystemStore.class.getName());
        inits.put("rootpath", "./");
        inits.put("storeDebug", "1");
        servletRegistrationBean.setInitParameters(inits);
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean disableSpringBootErrorFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new ErrorFilter());
        filterRegistrationBean.setEnabled(true);
        return filterRegistrationBean;
    }


}
