package com.dihri.uploader.json;

import java.util.List;

public class FileResponse {
    private List<FileItemResponse> files;

    public FileResponse() {
    }

    public FileResponse(List<FileItemResponse> files) {
        this.files = files;
    }

    public List<FileItemResponse> getFiles() {
        return files;
    }

    public void setFiles(List<FileItemResponse> files) {
        this.files = files;
    }
}
