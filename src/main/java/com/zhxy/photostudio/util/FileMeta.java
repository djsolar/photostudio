package com.zhxy.photostudio.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties({"bytes"})
public class FileMeta {
 
    private String fileName;
    private String fileSize;
    private String fileType;
    private byte[] bytes;
 
}