package com.dihri.uploader.service;

import com.dihri.uploader.exception.ConvertException;
import com.dihri.uploader.model.File;
import com.dihri.uploader.model.Image;
import org.springframework.http.*;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;

/**
 * Конвертер файлов (из формы, из Base64, из ссылки)
 * @param <T> тип файла
 */
public class FileConverter<T extends File> {
    private FileFactory<T> fileFactory;
    private RestTemplate restTemplate;

    public FileConverter(FileFactory<T> fileFactory) {
        this.fileFactory=fileFactory;
        this.restTemplate = new RestTemplate();
        this.restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
    }

    public T convertViaMultipart(MultipartFile multipartFile) throws ConvertException {
        try {
            return fileFactory.build(multipartFile.getBytes());
        } catch (IOException e) {
            return null;
        }
    }
    public T convertViaBase64(String base64) throws ConvertException {
        byte[] bytes = Base64.getDecoder().decode(base64);
        return fileFactory.build(bytes);
    }
    public T convertViaUrl(String url) throws ConvertException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<byte[]> response = restTemplate.exchange(
                url,
                HttpMethod.GET, entity, byte[].class);

        if (response.getStatusCode() != HttpStatus.OK) throw new ConvertException();
        return fileFactory.build(response.getBody());


    }

}
