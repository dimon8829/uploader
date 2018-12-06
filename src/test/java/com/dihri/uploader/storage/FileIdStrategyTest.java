package com.dihri.uploader.storage;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class FileIdStrategyTest {

    private FileIdStrategy fileIdStrategy = new FileIdStrategy();

    @Test
    public void generateFileIdWithoutParams() throws Exception {
        String fileId1 = fileIdStrategy.generateFileId();
        String fileId2 = fileIdStrategy.generateFileId();
        assertNotNull(fileId1);
        assertNotNull(fileId2);
        assertNotEquals(fileId1,fileId2);
    }

    @Test
    public void generateFileIdWithParams() throws Exception {
        String fileId = fileIdStrategy.generateFileId();
        String fileId1 = fileIdStrategy.generateFileId(fileId,"200");
        String fileId2 = fileIdStrategy.generateFileId(fileId,"200");
        assertNotNull(fileId1);
        assertNotNull(fileId2);
        assertEquals(fileId1,fileId2);
    }
}
