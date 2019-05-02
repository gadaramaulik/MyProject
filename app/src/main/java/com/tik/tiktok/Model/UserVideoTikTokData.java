package com.tik.tiktok.Model;

import java.util.ArrayList;

public class UserVideoTikTokData {

    private String status_code;
    private String max_cursor;
    private String has_more;
    private String min_cursor;
    private ArrayList<UserVideoList> aweme_list;

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public String getMax_cursor() {
        return max_cursor;
    }

    public void setMax_cursor(String max_cursor) {
        this.max_cursor = max_cursor;
    }

    public String getHas_more() {
        return has_more;
    }

    public void setHas_more(String has_more) {
        this.has_more = has_more;
    }

    public String getMin_cursor() {
        return min_cursor;
    }

    public void setMin_cursor(String min_cursor) {
        this.min_cursor = min_cursor;
    }

    public ArrayList<UserVideoList> getAweme_list() {
        return aweme_list;
    }

    public void setAweme_list(ArrayList<UserVideoList> aweme_list) {
        this.aweme_list = aweme_list;
    }
}
