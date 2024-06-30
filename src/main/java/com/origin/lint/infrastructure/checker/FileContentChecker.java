package com.origin.lint.infrastructure.checker;

public interface FileContentChecker {
  void check(String path, String content) throws Exception;
}
