package br.com.fco_romario.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "file") //prefixo que esta do application.yml
public class FileStorageConfig {

    private String uploadDir; //reconhece pelo nome que é o upload-dir

    public FileStorageConfig() {}

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
