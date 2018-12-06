package com.dihri.uploader.controller;

import com.dihri.uploader.exception.ConvertException;
import com.dihri.uploader.json.FileItemResponse;
import com.dihri.uploader.json.FileRequest;
import com.dihri.uploader.json.FileResponse;
import com.dihri.uploader.model.Image;
import com.dihri.uploader.service.FileConverter;
import com.dihri.uploader.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.Function;

@RestController
public class ImageController {

    @Autowired
    private FileConverter<Image> fileConverter;
    @Autowired
    private ImageService imageService;

    @PostMapping(value = "/image", consumes = "multipart/form-data")
    public FileResponse upload(@RequestParam("file") List<MultipartFile> multipartFiles) {
        List<FileItemResponse> result = upload(multipartFiles,item->fileConverter.convertViaMultipart(item),item->item.getOriginalFilename());
        return new FileResponse(result);
    }

    private <T> List<FileItemResponse> upload(List<T> data, ImageConvertFunction<T> converter, Function<T,String> filenameGenerator) {
        List<FileItemResponse> result = new ArrayList<>();
        for(T item:data) {
            try {
                Image image = converter.convert(item);
                String fileId = imageService.save(image);
                result.add(FileItemResponse.success(filenameGenerator.apply(item),fileId));
            } catch (ConvertException e) {
                result.add(FileItemResponse.wrongFormat(filenameGenerator.apply(item)));
            } catch (IOException e) {
                result.add(FileItemResponse.error(filenameGenerator.apply(item)));
            }
        }
        return result;
    }

    @PostMapping(value = "/image",consumes = "application/json")
    public FileResponse upload(@RequestBody FileRequest fileRequest) {
        List<FileItemResponse> result = new ArrayList<>();
        if(Objects.nonNull(fileRequest.getBase64s())) {
            result.addAll(upload(fileRequest.getBase64s(),item->fileConverter.convertViaBase64(item),item->String.valueOf(item.hashCode())));
        }
        if(Objects.nonNull(fileRequest.getUrls())) {
            result.addAll(upload(fileRequest.getUrls(),item->fileConverter.convertViaUrl(item),item->item));
        }
        return new FileResponse(result);
    }

    @GetMapping("/image/{fileId}")
    public void download(@PathVariable String fileId,
                         @RequestParam(required = false, defaultValue = "false") boolean is_preview,
                         HttpServletResponse response) throws IOException {
        // get your file as InputStream
        Image image = imageService.get(fileId,is_preview);
        InputStream is = new ByteArrayInputStream(image.getBytes());
        // copy it to response's OutputStream
        org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
        response.flushBuffer();

    }

    @FunctionalInterface
    interface ImageConvertFunction<T> {
        Image convert(T data) throws ConvertException;
    }
}
