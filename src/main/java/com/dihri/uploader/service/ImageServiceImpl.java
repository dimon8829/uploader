package com.dihri.uploader.service;

import com.dihri.uploader.model.Image;
import com.dihri.uploader.storage.FileIdStrategy;
import com.dihri.uploader.storage.FileStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

@Service
public class ImageServiceImpl implements ImageService {


    private FileStorage fileStorage;
    private FileIdStrategy fileIdStrategy;
    private ImageResizeService imageResizeService;
    private static final int PREVIEW_WIDTH=100;
    private static final int PREVIEW_HEIGHT=100;

    public ImageServiceImpl(FileStorage fileStorage, FileIdStrategy fileIdStrategy, ImageResizeService imageResizeService) {
        this.fileStorage=fileStorage;
        this.fileIdStrategy=fileIdStrategy;
        this.imageResizeService=imageResizeService;
    }

    @Override
    public String save(Image image) throws IOException {
        //генерируем идентификатор и сохраняем оригинал в сторадж
        String originalFileId = fileIdStrategy.generateFileId();
        fileStorage.save(originalFileId,image);
        //генерируем идентификатор и сохраняем превью изображеня в сторадж
        String previewFileId = fileIdStrategy.generateFileId(originalFileId,String.valueOf(PREVIEW_WIDTH),String.valueOf(PREVIEW_HEIGHT));
        Image resized = imageResizeService.resize(image,PREVIEW_WIDTH,PREVIEW_HEIGHT);
        fileStorage.save(previewFileId,resized);
        return originalFileId;



    }

    @Override
    public Image get(String fileId, boolean isPreview) {
        if(isPreview) fileId=fileIdStrategy.generateFileId(fileId,String.valueOf(PREVIEW_WIDTH),String.valueOf(PREVIEW_HEIGHT));
        return (Image) fileStorage.get(fileId);
    }



}
