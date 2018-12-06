package com.dihri.uploader.service;

import com.dihri.uploader.model.Image;

import java.io.IOException;

/**
 * Сервис изменения размера изображения
 */
public interface ImageResizeService {
    /**
     * Изменение размера изображения
     * @param original оригинальное изображение
     * @param width желаемая ширина
     * @param height желаемая высота
     * @return изменённое изображение
     * @throws IOException
     */
    Image resize(Image original, int width, int height) throws IOException;
}
