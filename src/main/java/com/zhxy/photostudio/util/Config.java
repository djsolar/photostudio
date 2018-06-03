package com.zhxy.photostudio.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

@Component
@Data
public class Config {

    @Value("${upload.photo.path}")
    private String photoPath;

    @PostConstruct
    public void init() {
        File file = new File(this.photoPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
