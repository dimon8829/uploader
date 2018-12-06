package com.dihri.uploader.storage;

import com.dihri.uploader.model.File;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class InMemoryFileStorage implements FileStorage{

    private ConcurrentMap<String,File> storage = new ConcurrentHashMap<>();

    @Override
    public void save(String fileId, File file) {
        storage.put(fileId, file);
    }

    @Override
    public File get(String fileId) {
        return storage.get(fileId);
    }
}
