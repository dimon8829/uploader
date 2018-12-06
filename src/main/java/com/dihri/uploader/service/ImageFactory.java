package com.dihri.uploader.service;

import com.dihri.uploader.exception.ConvertException;
import com.dihri.uploader.model.Image;
import org.apache.tika.Tika;


public class ImageFactory implements FileFactory<Image> {

    private static final String PREFIX_MIME= "image/";

    @Override
    public Image build(byte[] bytes) throws ConvertException {
        Tika tika = new Tika();
        String mime=tika.detect(bytes);
        if(!mime.startsWith(PREFIX_MIME)) throw new ConvertException();
        return new Image(bytes);
    }

}
