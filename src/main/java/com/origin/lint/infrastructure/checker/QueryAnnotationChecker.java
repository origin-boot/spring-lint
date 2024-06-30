package com.origin.lint.infrastructure.checker;

public class QueryAnnotationChecker implements FileContentChecker {

  @Override
  public void check(String path, String content) throws Exception {
    if (content.contains("@Query")) {
      throw new RuntimeException("[ERROR] Query annotation found in file: " + path);
    }
  }
}
