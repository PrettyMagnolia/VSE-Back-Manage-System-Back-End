package com.backend.vse.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 赵帅涛
 * @date 2023/04/13
 */
@Service
public interface OssService {
    // 上传文件，返回访问URL
    String uploadFile(MultipartFile file);
    String uploadImg(MultipartFile file,String dir);

    String uploadLongText(String content,String key);
}
