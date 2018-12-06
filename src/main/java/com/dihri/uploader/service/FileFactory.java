package com.dihri.uploader.service;

import com.dihri.uploader.exception.ConvertException;
import com.dihri.uploader.model.File;

public interface FileFactory<T extends File> {
    T build(byte[] bytes) throws ConvertException;
}
