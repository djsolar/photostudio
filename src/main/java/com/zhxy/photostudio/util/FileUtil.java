package com.zhxy.photostudio.util;

import java.io.File;

public class FileUtil {
    public static String getMd5Path(String filePath, String md5) {
        File file = new File(filePath);
        String dirPath = file.getParentFile().getAbsolutePath();
        String fileName = file.getName();
        String suffix = fileName.substring(fileName.lastIndexOf('.'));
        return dirPath + File.separator + md5 + suffix;
    }
}
