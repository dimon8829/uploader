package com.dihri.uploader.storage;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FileIdStrategy {

    private static final String SEPARATOR="_";

    public String generateFileId() {
        return UUID.randomUUID().toString();
    }

    public String generateFileId(String fileId, String...args) {
        return fileId+String.join(SEPARATOR,args);
    }
}
