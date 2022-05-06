package com.github.zxbu.webdavteambition.client;

import com.github.zxbu.webdavteambition.config.DriveProperties;
import okhttp3.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface DriverClient {

    void DriverClient(DriveProperties driveProperties);

    String post(String url, RequestBody body);


    String get(String url, Map<String, String> params);

    Response download(String url, HttpServletRequest httpServletRequest, long size);

    String upload(String url, byte[] bytes, final int offset, final int byteCount);

    public String getToken();

    void setToken(String token);

    String getTotalUrl(String url);
}