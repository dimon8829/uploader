package com.dihri.uploader.service;

import com.dihri.uploader.model.Image;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageResizeServiceImpl implements ImageResizeService {

    @Override
    public Image resize(Image original, int width, int height) throws IOException {
        InputStream in = new ByteArrayInputStream(original.getBytes());
        BufferedImage bufferedImage = ImageIO.read(in);
        java.awt.Image tmp = bufferedImage.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(resized, "png", baos );
        baos.flush();
        byte[] resizedInBytes = baos.toByteArray();
        baos.close();
        return new Image(resizedInBytes);
    }
}
