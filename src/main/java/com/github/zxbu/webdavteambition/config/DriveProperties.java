package com.github.zxbu.webdavteambition.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "webdav", ignoreUnknownFields = true)
public class DriveProperties {
    private String url = "https://cloud.189.cn/api";
    private String authorization = "apm_ua=8FFA059128610815594F4E7917B1A89C; apm_uid=D4F2CAB743A62948DDC2E6EE2D511D48; apm_ct=20220430163359000; apm_ip=114.223.60.198; apm_sid=AD2F97C1A088650A76DEC358F778F8A8; JSESSIONID=51DD5DBD38DA0D39D2A2AFF2DEE3C570; COOKIE_LOGIN_USER=B966C96BDD580FB28FBC37285574A544B72133E920B5B991D322FE8317B2976E1CB93F72F3AD40E7EA1FB19D45D4839D885E4E383700034A65B1D62A7CDB6B163F57BB05";
    private String refreshToken;
    private String workDir = "/etc/aliyun-driver/";
    private String agent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 11_0_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36";
    private String driveId;
    private Auth auth;
    private Map<String, String> cookies;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getDriveId() {
        return driveId;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getWorkDir() {
        return workDir;
    }

    public void setWorkDir(String workDir) {
        this.workDir = workDir;
    }

    public void setDriveId(String driveId) {
        this.driveId = driveId;
    }

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

}
