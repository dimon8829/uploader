package com.dihri.uploader.service;

import com.dihri.uploader.model.Image;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class ImageResizeServiceTest {
    private ImageResizeService imageResizeService = new ImageResizeServiceImpl();

    @Test
    public void resize() throws Exception {
        Image image = new Image(IOUtils.toByteArray(new ClassPathResource("/img/Google-Wallet-Logo.png").getInputStream()));
        Image resized = imageResizeService.resize(image,100,100);
        byte[] resizedExpected = IOUtils.toByteArray(new ClassPathResource("/img/Google-Wallet-Logo_100.png").getInputStream());
        boolean result = Arrays.equals(resizedExpected,resized.getBytes());
        assertTrue(result);
    }
}
