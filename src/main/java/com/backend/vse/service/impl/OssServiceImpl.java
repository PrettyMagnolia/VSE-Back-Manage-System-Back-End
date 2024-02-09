package com.backend.vse.service.impl;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.backend.vse.service.OssService;
import com.backend.vse.tools.ConstantPropertiesTools;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * @author 赵帅涛
 * @date 2023/04/13
 */
@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFile(MultipartFile file) {
        String endPoint = ConstantPropertiesTools.END_POINT;
        String accessKeyId = ConstantPropertiesTools.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesTools.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesTools.BUCKET_NAME;
        if (file != null) {
            OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
            try {
                // 获取输入流
                InputStream inputStream = file.getInputStream();
                // 0 获取文件名称
                String fileName = file.getOriginalFilename();
                // 1 在文件名称里面添加随机唯一的值
                String uuid = UUID.randomUUID().toString().replace("-", "");
                fileName = uuid + "/" + fileName;
//                // 2 获取当前日期
//                LocalDate date = LocalDate.now();
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//                String datePath = date.format(formatter);
//                fileName = datePath + "/" + fileName;

                // 设置OSS请求头
                ObjectMetadata metadata = new ObjectMetadata();
                String originalFilename = file.getOriginalFilename();
                String type = originalFilename.substring(originalFilename.lastIndexOf("."));
                metadata.setContentType(getcontentType(type));

                // 调用OSS方法上传
                ossClient.putObject(bucketName, fileName, inputStream, metadata);

                String url = "https://" + bucketName + "." + endPoint + '/' + fileName;
                return url;
            } catch (Exception e) {
                return null;
            } finally {
                if (ossClient != null) {
                    ossClient.shutdown();
                }
            }
        }
        return null;
    }
    public String uploadImg(MultipartFile file,String dir) {
        String endPoint = ConstantPropertiesTools.END_POINT;
        String accessKeyId = ConstantPropertiesTools.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesTools.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesTools.BUCKET_NAME;
        if (file != null) {
            OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
            try {
                // 获取输入流
                InputStream inputStream = file.getInputStream();
                // 0 获取文件名称
                String fileName = file.getOriginalFilename();
                // 1 在文件名称里面添加随机唯一的值
                String uuid = UUID.randomUUID().toString().replace("-", "");
                fileName = dir + uuid + "/" + fileName;

                // 设置OSS请求头
                ObjectMetadata metadata = new ObjectMetadata();
                String originalFilename = file.getOriginalFilename();
                String type = originalFilename.substring(originalFilename.lastIndexOf("."));
                metadata.setContentType(getcontentType(type));

                // 调用OSS方法上传
                ossClient.putObject(bucketName, fileName, inputStream, metadata);

                String url = "https://" + bucketName + "." + endPoint + '/' + fileName;
                return url;
            } catch (Exception e) {
                return null;
            } finally {
                if (ossClient != null) {
                    ossClient.shutdown();
                }
            }
        }
        return null;
    }

    @Override
    public String uploadLongText(String content, String key) {
        String endPoint = ConstantPropertiesTools.END_POINT;
        String accessKeyId = ConstantPropertiesTools.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesTools.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesTools.BUCKET_NAME;
        if (!content.isEmpty()) {
            OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
            if (key.isEmpty()) {
                key = "text.txt";
            }
            try {
                String uuid = UUID.randomUUID().toString().replace("-", "");
                key = uuid + "/" + key;
                PutObjectRequest putRequest = new PutObjectRequest(bucketName, key, new ByteArrayInputStream(content.getBytes()));
                putRequest.setProcess("true");
                PutObjectResult result = ossClient.putObject(putRequest);
                return result.getResponse().getUri();
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public static String getcontentType(String filenameExtension) {
        if (".pdf".equalsIgnoreCase(filenameExtension)) {
            return "application/pdf";
        }
        if (".bmp".equalsIgnoreCase(filenameExtension)) {
            return "image/bmp";
        }
        if (".gif".equalsIgnoreCase(filenameExtension)) {
            return "image/gif";
        }
        if (".jpeg".equalsIgnoreCase(filenameExtension) ||
                ".jpg".equalsIgnoreCase(filenameExtension) ||
                ".png".equalsIgnoreCase(filenameExtension)) {
            return "image/jpg";
        }
        if (".html".equalsIgnoreCase(filenameExtension)) {
            return "text/html";
        }
        if (".txt".equalsIgnoreCase(filenameExtension)) {
            return "text/plain";
        }
        if (".vsd".equalsIgnoreCase(filenameExtension)) {
            return "application/vnd.visio";
        }
        if (".pptx".equalsIgnoreCase(filenameExtension) ||
                ".ppt".equalsIgnoreCase(filenameExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        if (".docx".equalsIgnoreCase(filenameExtension)) {
            return "application/msword";
        }
        if (".xml".equalsIgnoreCase(filenameExtension)) {
            return "text/xml";
        }
        return "image/jpg";
    }
}
