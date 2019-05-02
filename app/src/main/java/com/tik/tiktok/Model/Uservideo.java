package com.tik.tiktok.Model;

import java.util.ArrayList;

public class Uservideo {

    VideoCover cover;
    VideoDownload_addr download_addr;

    public VideoCover getCover() {
        return cover;
    }

    public void setCover(VideoCover cover) {
        this.cover = cover;
    }

    public VideoDownload_addr getDownload_addr() {
        return download_addr;
    }

    public void setDownload_addr(VideoDownload_addr download_addr) {
        this.download_addr = download_addr;
    }
}
