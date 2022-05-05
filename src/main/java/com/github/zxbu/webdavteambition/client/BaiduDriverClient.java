package com.github.zxbu.webdavteambition.client;

import com.github.zxbu.webdavteambition.config.BaiduDriverProperties;
import com.github.zxbu.webdavteambition.util.JsonUtil;
import net.sf.webdav.exceptions.WebdavException;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class BaiduDriverClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(AliYunDriverClient.class);
    private OkHttpClient okHttpClient;
    private BaiduDriverProperties baiduProperties;

    public BaiduDriverClient(BaiduDriverProperties baiduDriverProperties) {
        this.baiduProperties = baiduDriverProperties;

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .connectTimeout(1, TimeUnit.MINUTES)
                .build();
        this.okHttpClient = okHttpClient;
    }

    public String post(String url, RequestBody body){
        Request request = new Request.Builder()
                .addHeader("User-Agent", "pan.baidu.com")
                .post(body)
                .url(getTotalUrl(url)).build();
        try (Response response = okHttpClient.newCall(request).execute()){
            String res = response.body().string();
            if (!response.isSuccessful()) {
                System.out.print("请求失败，response code:"+ response.code());
            }else{
                //System.out.print(res);
            }
            return res;
        } catch (IOException e) {
        }
        return  "";
    }


    public String get(String url, Map<String, String> params)  {
        try {
            HttpUrl.Builder urlBuilder = HttpUrl.parse(getTotalUrl(url)).newBuilder();
            params.forEach(urlBuilder::addQueryParameter);
            Request request = new Request.Builder()
                    .addHeader("User-Agent", "pan.baidu.com")
                    .get().url(urlBuilder.build())
                    .build();
            try (Response response = okHttpClient.newCall(request).execute()){
                //LOGGER.info("get {}, code {}", urlBuilder.build(), response.code());
                if (!response.isSuccessful()) {
                    //throw new WebdavException("请求失败：" + urlBuilder.build().toString());
                    return "";
                }
                return response.body().string();
            }

        } catch (Exception e) {
            //throw new WebdavException(e);
            return "";
        }
    }

    public Response download(String url, HttpServletRequest httpServletRequest, long size ) {
        Request.Builder builder = new Request.Builder().header("referer", "https://d.pcs.baidu.com");
        String range = httpServletRequest.getHeader("range");
        if (range != null) {
            // 如果range最后 >= size， 则去掉
            String[] split = range.split("-");
            if (split.length == 2) {
                String end = split[1];
                if (Long.parseLong(end) >= size) {
                    range = range.substring(0, range.lastIndexOf('-') + 1);
                }
            }
            builder.header("range", range);
        }

        String ifRange = httpServletRequest.getHeader("if-range");
        if (ifRange != null) {
            builder.header("if-range", ifRange);
        }


        Request request = builder.url(url+"&access_token="+this.baiduProperties.getAccess_token()).build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            return response;
        } catch (IOException e) {
            throw new WebdavException(e);
        }
    }

    public String upload(String url, byte[] bytes, final int offset, final int byteCount) {
        Request request = new Request.Builder()
                .put(RequestBody.create(MediaType.parse(""), bytes,offset,byteCount))
                .url(url).build();
        try (Response response = okHttpClient.newCall(request).execute()){
            LOGGER.info("upload {}, code {}", url, response.code());
            if (!response.isSuccessful()) {
                LOGGER.error("请求失败，url={}, code={}, body={}", url, response.code(), response.body().string());
                throw new WebdavException("请求失败：" + url);
            }
            return response.body().string();
        } catch (IOException e) {
            throw new WebdavException(e);
        }
    }

    public String getToken(){
        return baiduProperties.getAccess_token();
    }

    public void setToken(String token){
       baiduProperties.setAccess_token(token);
    }

    private String getTotalUrl(String url) {
        if (url.startsWith("http")) {
            return url;
        }
        return baiduProperties.getUrl() + url;
    }
}
