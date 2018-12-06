package com.dihri.uploader;

import com.dihri.uploader.model.Image;
import com.dihri.uploader.service.FileConverter;
import com.dihri.uploader.service.ImageFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UploaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(UploaderApplication.class, args);
	}

	@Bean
	public FileConverter<Image> fileConverter() {
		return new FileConverter<>(new ImageFactory());
	}
}
