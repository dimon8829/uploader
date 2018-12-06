package com.dihri.uploader.storage;

import com.dihri.uploader.model.File;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class FileStorageTest {
    private FileStorage fileStorage = new InMemoryFileStorage();

    @Test
    public void saveAndGet() throws Exception {
        File file = mock(File.class);
        String fileId = UUID.randomUUID().toString();
        fileStorage.save(fileId,file);
        File file2=fileStorage.get(fileId);
        assertEquals(file,file2);
    }
}
