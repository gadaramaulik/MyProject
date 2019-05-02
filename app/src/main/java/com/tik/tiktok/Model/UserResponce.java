package com.tik.tiktok.Model;

import java.util.ArrayList;

public class UserResponce {

    private String has_next_page="";
    private String page="";
    private String type="";
    private ArrayList<UserDetail> lists;

    public String getHas_next_page() {
        return has_next_page;
    }

    public void setHas_next_page(String has_next_page) {
        this.has_next_page = has_next_page;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<UserDetail> getLists() {
        return lists;
    }

    public void setLists(ArrayList<UserDetail> lists) {
        this.lists = lists;
    }
}
