package com.dihri.uploader.json;

public class FileItemResponse {
    private String originalName;
    private String fileId;
    private FileUploadCode code;

    public static final FileItemResponse success(String originalName, String fileId) {
        FileItemResponse fileItemResponse = new FileItemResponse();
        fileItemResponse.setCode(FileUploadCode.SUCCESS);
        fileItemResponse.setOriginalName(originalName);
        fileItemResponse.setFileId(fileId);
        return fileItemResponse;
    }

    public static final FileItemResponse wrongFormat(String originalName) {
        FileItemResponse fileItemResponse = new FileItemResponse();
        fileItemResponse.setCode(FileUploadCode.WRONG_FORMAT);
        fileItemResponse.setOriginalName(originalName);
        return fileItemResponse;
    }

    public static final FileItemResponse error(String originalName) {
        FileItemResponse fileItemResponse = new FileItemResponse();
        fileItemResponse.setCode(FileUploadCode.ERROR);
        fileItemResponse.setOriginalName(originalName);
        return fileItemResponse;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public FileUploadCode getCode() {
        return code;
    }

    public void setCode(FileUploadCode code) {
        this.code = code;
    }
}
