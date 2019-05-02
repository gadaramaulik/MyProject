package com.tik.tiktok.Model;

public class UserVideoDetailResponce {

    private UserVideoTikTokData tiktokdata;
    private String code;
    private String url;





    public UserVideoTikTokData getTiktokdata() {
        return tiktokdata;
    }

    public void setTiktokdata(UserVideoTikTokData tiktokdata) {
        this.tiktokdata = tiktokdata;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
