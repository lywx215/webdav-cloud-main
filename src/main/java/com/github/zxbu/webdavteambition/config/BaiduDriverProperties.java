package com.github.zxbu.webdavteambition.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "baidudrive", ignoreUnknownFields = true)
public class BaiduDriverProperties {
    private String AppID = "250528";
    private String AppKey = "5EsMBVgTDVriRGzFQGhztwXTeVZqRA4q";
    private String SecretKey = "E0MXF7PL61YWxg1CClK3PK6RbPGdXdDA";
    private String SignKey = "#G0Co~KoW2T9Gp^+OoD7qmNkp%8@R2EH";

    private String access_token = "121.71085c317b69284884844e184339ebac.YDuf3CwmGTKXU8l6zJ1jUqh_FlgGUmsmjGvzHzA.BFHM8g";

    private String token = "5be28613db1c989b602f21ce3c5d401b";

    private String agent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 11_0_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36";

    private String cookie ="";

    private String url = "https://pan.baidu.com";

    private Auth auth;

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAppID() {
        return AppID;
    }

    public void setAppID(String appID) {
        AppID = appID;
    }

    public String getAppKey() {
        return AppKey;
    }

    public void setAppKey(String appKey) {
        AppKey = appKey;
    }

    public String getSecretKey() {
        return SecretKey;
    }

    public void setSecretKey(String secretKey) {
        SecretKey = secretKey;
    }

    public String getSignKey() {
        return SignKey;
    }

    public void setSignKey(String signKey) {
        SignKey = signKey;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }
}
