package com.github.zxbu.webdavteambition.config;

import com.github.zxbu.webdavteambition.model.result.TFile;
import com.github.zxbu.webdavteambition.store.BaiduDriverClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BaiduDriverCronTask {

    @Autowired
    private BaiduDriverClientService baiduDriverClientService;

    /**
     * 每隔5分钟请求一下接口，保证token不过期
     */
    @Scheduled(initialDelay = 30 * 1000, fixedDelay = 15 * 60 * 1000)
    public void refreshToken() {
        try {
            //TFile root = aliYunDriverClientService.getTFileByPath("/");
            //aliYunDriverClientService.getTFiles(root.getId());

        } catch (Exception e) {
            // nothing
        }

    }
}
