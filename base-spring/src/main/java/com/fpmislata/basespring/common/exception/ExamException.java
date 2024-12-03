package com.fpmislata.basespring.common.exception;

public class ExamException extends RuntimeException {
  public ExamException(String message, Throwable cause) {
    super(message, cause);
  }

  public ExamException(String message) {
    super(message);
  }
}
