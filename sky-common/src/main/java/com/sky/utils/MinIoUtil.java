package com.sky.utils;

import com.sky.constant.MessageConstant;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @author kevonlin
 * @version 1.0.0
 * @date 2023/12/8 19:16
 * @description MinIO工具类
 **/
@Data
@AllArgsConstructor
@Slf4j
public class MinIoUtil {
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucketName;

    /*
     * @param bytes
     * @param objectName
     * @return java.lang.String
     * @author kevonlin
     * @create 2023/12/8 19:15
     * @description 文件上传
     **/
    public String upload(MultipartFile file) {
        try {
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint(endpoint)
                            .credentials(accessKey, secretKey)
                            .build();

            // Make 'minio-demo' bucket if not exist.
            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                // Make a new bucket called 'minio-demo'.
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            } else {
                log.info("Bucket '" + bucketName + "' already exists.");
            }

            String originalFilename = file.getOriginalFilename();
            //文件访问路径规则 http://endpoint/bucketName/objectName
            StringBuilder stringBuilder = new StringBuilder();
            if (originalFilename != null) {
                String objectName = UUID.randomUUID().toString();
                String extendName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

                minioClient.putObject(PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName + extendName)
                        .contentType(file.getContentType())
                        .stream(file.getInputStream(), file.getInputStream().available(), -1)
                        .build());

                stringBuilder
                        .append(endpoint)
                        .append("/")
                        .append(bucketName)
                        .append("/")
                        .append(objectName)
                        .append(".")
                        .append(extendName);
            }

            log.info("文件上传到:{}", stringBuilder);

            return stringBuilder.toString();
        } catch (Exception e) {
            log.error("文件上传失败: {}", e.toString());
        }
        return MessageConstant.UPLOAD_FAILED;
    }
}
