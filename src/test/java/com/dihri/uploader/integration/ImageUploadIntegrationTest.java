package com.dihri.uploader.integration;

import com.dihri.uploader.json.FileResponse;
import com.dihri.uploader.json.FileUploadCode;
import com.dihri.uploader.util.JsonUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ImageUploadIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void multipartUpload() throws Exception {
        //загружаем три правильных картинки и две нет
        MockMultipartFile img1 = new MockMultipartFile("file","img1.jpg","multipart/form-data",IOUtils.toByteArray(new ClassPathResource("/img/img1.jpg").getInputStream()));
        MockMultipartFile img2 = new MockMultipartFile("file","img2.jpg","multipart/form-data",IOUtils.toByteArray(new ClassPathResource("/img/img2.jpg").getInputStream()));
        MockMultipartFile img3 = new MockMultipartFile("file","test.txt","multipart/form-data",IOUtils.toByteArray(new ClassPathResource("/img/test.txt").getInputStream()));
        MockMultipartFile img4 = new MockMultipartFile("file","wrong_img.jpg","",IOUtils.toByteArray(new ClassPathResource("/img/wrong_img.jpg").getInputStream()));
        MvcResult mvcResult = mockMvc.perform(fileUpload("/image").file(img1).file(img2).file(img3).file(img4))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.files",hasSize(4)))
                .andExpect(jsonPath("$.files[*].originalName",contains("img1.jpg","img2.jpg","test.txt","wrong_img.jpg")))
                .andExpect(jsonPath("$.files[*].code",contains(FileUploadCode.SUCCESS.name(),FileUploadCode.SUCCESS.name(),FileUploadCode.WRONG_FORMAT.name(),FileUploadCode.WRONG_FORMAT.name())))
                .andExpect(jsonPath("$.files[*].fileId").exists())
                .andReturn();
        //проверяем загруженные изображения
        FileResponse response = JsonUtils.convertJsonToObject(mvcResult.getResponse().getContentAsString(), FileResponse.class);
        mvcResult = mockMvc.perform(get("/image/"+response.getFiles().get(0).getFileId()))
                .andExpect(status().isOk())
                .andReturn();
        boolean result=Arrays.equals(IOUtils.toByteArray(new ClassPathResource("/img/img1.jpg").getInputStream()),mvcResult.getResponse().getContentAsByteArray());
        assertTrue(result);

        mvcResult = mockMvc.perform(get("/image/"+response.getFiles().get(1).getFileId()))
                .andExpect(status().isOk())
                .andReturn();
        result=Arrays.equals(IOUtils.toByteArray(new ClassPathResource("/img/img2.jpg").getInputStream()),mvcResult.getResponse().getContentAsByteArray());
        assertTrue(result);

        //Проверяем созданные превью изображений
        mvcResult = mockMvc.perform(get("/image/"+response.getFiles().get(0).getFileId()).param("is_preview","true"))
                .andExpect(status().isOk())
                .andReturn();
        result=Arrays.equals(IOUtils.toByteArray(new ClassPathResource("/img/img1_100.jpg").getInputStream()),mvcResult.getResponse().getContentAsByteArray());
        assertTrue(result);

        mvcResult = mockMvc.perform(get("/image/"+response.getFiles().get(1).getFileId()).param("is_preview","true"))
                .andExpect(status().isOk())
                .andReturn();
        result=Arrays.equals(IOUtils.toByteArray(new ClassPathResource("/img/img2_100.jpg").getInputStream()),mvcResult.getResponse().getContentAsByteArray());
        assertTrue(result);
    }

    @Test
    public void uploadViaUrl() throws Exception {
        //загружаем одно правильное изображение и одно неправильное

        MvcResult mvcResult = mockMvc.perform(post("/image").contentType(MediaType.APPLICATION_JSON).content(IOUtils.toByteArray(new ClassPathResource("/json/url_request.json").getInputStream())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.files",hasSize(2)))
                .andExpect(jsonPath("$.files[*].originalName",contains("https://upload.wikimedia.org/wikipedia/commons/b/bc/Google-Wallet-Logo.png","https://upload.wikimedia.org/wikipedia/commons/0/0c/Wikimedia_Commons_web_en.pdf")))
                .andExpect(jsonPath("$.files[*].code",contains(FileUploadCode.SUCCESS.name(),FileUploadCode.WRONG_FORMAT.name())))
                .andExpect(jsonPath("$.files[*].fileId").exists())
                .andReturn();
        //проверяем загруженные изображения
        FileResponse response = JsonUtils.convertJsonToObject(mvcResult.getResponse().getContentAsString(), FileResponse.class);
        mvcResult = mockMvc.perform(get("/image/"+response.getFiles().get(0).getFileId()))
                .andExpect(status().isOk())
                .andReturn();
        boolean result=Arrays.equals(IOUtils.toByteArray(new ClassPathResource("/img/Google-Wallet-Logo.png").getInputStream()),mvcResult.getResponse().getContentAsByteArray());
        assertTrue(result);

        //Проверяем созданные превью изображений
        mvcResult = mockMvc.perform(get("/image/"+response.getFiles().get(0).getFileId()).param("is_preview","true"))
                .andExpect(status().isOk())
                .andReturn();
        result=Arrays.equals(IOUtils.toByteArray(new ClassPathResource("/img/Google-Wallet-Logo_100.png").getInputStream()),mvcResult.getResponse().getContentAsByteArray());
        assertTrue(result);
    }

    @Test
    public void uploadViaBase64() throws Exception {
        //загружаем одно правильное изображение и одно неправильное
        MvcResult mvcResult = mockMvc.perform(post("/image").contentType(MediaType.APPLICATION_JSON).content(IOUtils.toByteArray(new ClassPathResource("/json/base64_request.json").getInputStream())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.files",hasSize(2)))
                .andExpect(jsonPath("$.files[*].originalName",contains("1984283096","1152865685")))
                .andExpect(jsonPath("$.files[*].code",contains(FileUploadCode.SUCCESS.name(),FileUploadCode.WRONG_FORMAT.name())))
                .andExpect(jsonPath("$.files[*].fileId").exists())
                .andReturn();
        //проверяем загруженные изображения
        FileResponse response = JsonUtils.convertJsonToObject(mvcResult.getResponse().getContentAsString(), FileResponse.class);
        mvcResult = mockMvc.perform(get("/image/"+response.getFiles().get(0).getFileId()))
                .andExpect(status().isOk())
                .andReturn();
        boolean result=Arrays.equals(IOUtils.toByteArray(new ClassPathResource("/img/Google-Wallet-Logo.png").getInputStream()),mvcResult.getResponse().getContentAsByteArray());
        assertTrue(result);

        //Проверяем созданные превью изображений
        mvcResult = mockMvc.perform(get("/image/"+response.getFiles().get(0).getFileId()).param("is_preview","true"))
                .andExpect(status().isOk())
                .andReturn();
        result=Arrays.equals(IOUtils.toByteArray(new ClassPathResource("/img/Google-Wallet-Logo_100.png").getInputStream()),mvcResult.getResponse().getContentAsByteArray());
        assertTrue(result);
    }

}
