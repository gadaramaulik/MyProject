package com.tik.tiktok.Model;

public class UserVideoList {

    Usershare_info share_info;
    Userstatistics statistics;
    Uservideo video;
    long create_time;
    String desc = "";
    String duration = "";


    public Usershare_info getShare_info() {
        return share_info;
    }

    public void setShare_info(Usershare_info share_info) {
        this.share_info = share_info;
    }

    public Userstatistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Userstatistics statistics) {
        this.statistics = statistics;
    }

    public Uservideo getVideo() {
        return video;
    }

    public void setVideo(Uservideo video) {
        this.video = video;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
