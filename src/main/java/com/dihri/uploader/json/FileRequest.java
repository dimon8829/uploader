package com.dihri.uploader.json;

import java.util.Base64;
import java.util.List;

public class FileRequest {
    private List<String> urls;
    private List<String> base64s;

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public List<String> getBase64s() {
        return base64s;
    }

    public void setBase64s(List<String> base64s) {
        this.base64s = base64s;
    }
}
