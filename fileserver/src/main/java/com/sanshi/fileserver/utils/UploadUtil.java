package com.sanshi.fileserver.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public abstract class UploadUtil {
    private static final String SAVE_PATH = "C:/测试文件夹/";

    public static String initPath() {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        return SAVE_PATH + date + "/";
    }

    //获取文件类型1
    public static String parseFileType(String fileName) {
        return !fileName.contains(".") ? "" : fileName.substring(fileName.lastIndexOf("."));
    }

    public static String saveFile(MultipartFile source, Long size) throws IOException {
        if (source.getSize() != size) {
            throw new RuntimeException("上传字节数与接收字节数不符！");
        }
        File file = new File(initPath());
        if (!file.exists() && !file.mkdirs()) {
            throw new RuntimeException("创建文件夹失败！");
        }
        String fileType = parseFileType(source.getOriginalFilename());
        while (true) {
            String saveFileName = UUID.randomUUID() + fileType.toLowerCase();
            File saveFilePath = new File(file.getPath(), saveFileName);
            if (saveFilePath.exists()) {
                continue;
            }
            source.transferTo(saveFilePath);
            return saveFilePath.getAbsolutePath();
        }
    }

    /*合并文件碎片*/
    public static String mergeFile(String fileType, List<String> fileNames) throws IOException {
        File file = new File(initPath());
        if (!file.exists() && !file.mkdirs()) {
            throw new RuntimeException("创建文件夹失败！");
        }
        File saveFilePath = null;
        do {
            String saveFileName = UUID.randomUUID() + fileType.toLowerCase();
            saveFilePath = new File(file.getPath(), saveFileName);
        } while (saveFilePath.exists());
        FileChannel out = new FileOutputStream(saveFilePath).getChannel();
        //合并文件
        for (String fileName : fileNames) {
            File patch = new File(fileName);
            FileChannel in = new FileInputStream(patch).getChannel();
            in.transferTo(0, in.size(), out);
            in.close();
            patch.delete();
        }
        out.close();
        return saveFilePath.getAbsolutePath();
    }
}
