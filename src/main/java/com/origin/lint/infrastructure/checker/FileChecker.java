package com.origin.lint.infrastructure.checker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;

import com.origin.lint.infrastructure.util.FileScanner;

public class FileChecker {
  private final String path;
  private final List<FileContentChecker> contentCheckers;
  private boolean hasError = false;

  public FileChecker(String path) {
    this.path = path;
    this.contentCheckers = new ArrayList<>();
    this.contentCheckers.add(new QueryAnnotationChecker());
  }

  public boolean hasError() {
    return hasError;
  }

  public void run() throws FileNotFoundException, Exception {
    new FileScanner(new FileScanner.Filter() {
      @Override
      public boolean accept(int level, String path, File file) throws Exception {
        return path.endsWith(".java");
      }
    }, new FileScanner.FileHandler() {
      @Override
      public void handle(int level, String path, File file) throws Exception {
        String fileContent = new String(Files.readAllBytes(file.toPath()));
        checkContent(path, fileContent);
      }
    }).scan(new File(path));
  }

  private void checkContent(String path, String fileContent) {
    try {
      for (FileContentChecker checker : contentCheckers) {
        checker.check(path, fileContent);
      }
    } catch (Exception e) {
      System.err.println(e.getMessage());
      hasError = true;
    }
  }
}
