package by.kotik.userservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import controller.GlobalExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import service.FileStorageService;
import service.LocalFileStorageService;


@Configuration
public class BeanConfiguration {
    @Value("${file-storage.local.service.path}")
    private String storagePath;

    @Bean
    GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler(objectMapper(), "User Service");
    }

    @Bean
    ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    @Bean(name = "localFileStorageService")
    FileStorageService localFileStorageService() {
        return new LocalFileStorageService(storagePath);
    }
}
