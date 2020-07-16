package com.fakebank.assignment.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "available")
public class FileTypeConfig {
    private Map<String, String> filetype = new LinkedHashMap<>();

    public Map<String, String> getFiletype() {
        return filetype;
    }

    public void setFiletype(Map<String, String> filetype) {
        this.filetype = filetype;
    }
}
