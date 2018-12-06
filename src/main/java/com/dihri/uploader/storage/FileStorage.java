package com.dihri.uploader.storage;

import com.dihri.uploader.model.File;

/**
 * Сервис хранения файлов
 */
public interface FileStorage {
    /**
     * Сохранение файла
     * @param fileId уникальный идентификатор
     * @param file файл
     */
    void save(String fileId, File file);

    /**
     * Получение файла по идентификатору
     * @param fileId идентификатор файла
     * @return файл
     */
    File get(String fileId);

}
