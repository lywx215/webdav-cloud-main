package com.github.zxbu.webdavteambition.client;

import com.github.zxbu.webdavteambition.config.DriveProperties;

public class DriverClientFactroy {
    publc IDriverClient CreateDriverClient(Class clazz, DriveProperties driveProperties){
        if (clazz instanceof AliYunDriverClient.Class)
            return new AliYunDriverClient(driveProperties);
    }
}
