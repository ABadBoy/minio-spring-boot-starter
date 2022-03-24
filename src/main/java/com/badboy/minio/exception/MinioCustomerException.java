package com.badboy.minio.exception;

public class MinioCustomerException extends Exception{

  public MinioCustomerException(String message) {
    super(message);
  }
  public MinioCustomerException(String message, Throwable cause) {
    super(message, cause);
  }
}
