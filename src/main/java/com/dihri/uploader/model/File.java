package com.dihri.uploader.model;

/**
 * Базовый класс файла
 */
public class File {
    private byte[] bytes;

    public File(byte[] bytes) {
        this.bytes=bytes;
    }

    public byte[] getBytes() {
        return bytes;
    }
}
