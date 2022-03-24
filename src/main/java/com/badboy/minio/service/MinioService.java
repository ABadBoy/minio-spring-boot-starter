package com.badboy.minio.service;

import com.badboy.minio.exception.MinioCustomerException;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import java.io.InputStream;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "spring.minio.url")
public class MinioService {

  private final MinioClient minioClient;

  @Autowired
  public MinioService(MinioClient minioClient) {
    this.minioClient = minioClient;
  }

  public void upload(String bucketName,Path path,InputStream file) throws MinioCustomerException {
    try {
      BucketExistsArgs bucketExistsArgs = BucketExistsArgs.builder().bucket(bucketName).build();
      boolean isExist = minioClient.bucketExists(bucketExistsArgs);
      if (!isExist) {
        throw new MinioCustomerException("bucket not exist");
      }
      PutObjectArgs args = PutObjectArgs.builder().bucket(bucketName).object(path.toString().replace('\\','/'))
          .stream(file, file.available(), -1).build();
      minioClient.putObject(args);
    } catch (Exception e) {
      throw new MinioCustomerException("上传文件异常", e);
    }
  }

  public InputStream download(String bucketName,Path path) throws MinioCustomerException {
    try {
      GetObjectArgs args = GetObjectArgs.builder().bucket(bucketName).object(path.toString().replace('\\','/')).build();
      return minioClient.getObject(args);
    } catch (Exception e) {
      throw new MinioCustomerException("下载文件异常", e);
    }
  }

  public void delete(String bucketName,Path path) throws MinioCustomerException {
    try {
      RemoveObjectArgs args = RemoveObjectArgs.builder()
          .bucket(bucketName)
          .object(path.toString().replace('\\','/'))
          .build();
      minioClient.removeObject(args);
    } catch (Exception e) {
      throw new MinioCustomerException("删除文件异常", e);
    }
  }
}
