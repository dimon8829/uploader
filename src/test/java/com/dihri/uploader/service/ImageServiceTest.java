package com.dihri.uploader.service;

import com.dihri.uploader.model.Image;
import com.dihri.uploader.storage.FileIdStrategy;
import com.dihri.uploader.storage.FileStorage;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ImageServiceTest {

    private ImageService imageService;
    private FileStorage fileStorage;
    private FileIdStrategy fileIdStrategy;
    private ImageResizeService imageResizeService;

    @Before
    public void before() {
        this.fileStorage = mock(FileStorage.class);
        this.fileIdStrategy = mock(FileIdStrategy.class);
        this.imageResizeService = mock(ImageResizeService.class);
        this.imageService = new ImageServiceImpl(fileStorage,fileIdStrategy,imageResizeService);
    }

    @Test
    public void save() throws Exception {
        Image image = mock(Image.class);
        String fileIdExpected = UUID.randomUUID().toString();
        when(fileIdStrategy.generateFileId()).thenReturn(fileIdExpected);
        String fileIdPreviewExpected = fileIdExpected+"_"+100+"_"+100;
        when(fileIdStrategy.generateFileId(fileIdExpected,"100","100")).thenReturn(fileIdPreviewExpected);
        Image resized = mock(Image.class);
        when(imageResizeService.resize(image,100,100)).thenReturn(resized);
        String fileId = imageService.save(image);
        assertEquals(fileIdExpected,fileId);
        verify(fileStorage).save(fileIdExpected,image);
        verify(fileStorage).save(fileIdPreviewExpected,resized);

    }
}
