package com.dihri.uploader.service;

import com.dihri.uploader.model.Image;

import java.io.IOException;

/**
 * Сервис работы с изображениями
 */
public interface ImageService {

    /**
     * Сохранение ихображения
     * @param image данные изображения
     * @return идентификатор изображения
     * @throws IOException
     */
    String save(Image image) throws IOException;

    /**
     * Получение изображения по идентификатору
     * @param fileId идентификатор изображения
     * @param isPreview true - если нужно получить превью изображения
     * @return данные изображения
     */
    Image get(String fileId, boolean isPreview);
}
