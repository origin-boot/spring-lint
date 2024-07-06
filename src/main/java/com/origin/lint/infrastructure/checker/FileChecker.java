package com.origin.lint.infrastructure.checker;

public interface FileChecker {
  void check(String path, String content) throws Exception;
}
