package com.origin.lint.infrastructure.util;

import java.io.File;
import java.io.FileNotFoundException;

public class FileScanner {
  public interface FileHandler {
    void handle(int level, String path, File file) throws Exception;
  }

  public interface Filter {
    boolean accept(int level, String path, File file) throws Exception;
  }

  private final FileHandler fileHandler;
  private final Filter filter;

  public FileScanner(Filter filter, FileHandler fileHandler) {
    this.filter = filter;
    this.fileHandler = fileHandler;
  }

  public void scan(File root) throws FileNotFoundException, Exception {
    if (root == null) {
      throw new IllegalArgumentException("Root file cannot be null.");
    }
    if (!root.exists()) {
      throw new FileNotFoundException("Root file does not exist: " + root.getAbsolutePath());
    }
    scanRecursive(root, 0, "");
  }

  private void scanRecursive(File file, int level, String relativePath)
      throws FileNotFoundException, Exception {

    if (!file.exists()) {
      throw new FileNotFoundException("File does not exist: " + file.getAbsolutePath());
    }
    if (!file.canRead()) {
      throw new SecurityException("Cannot read file: " + file.getAbsolutePath());
    }
    if (file.isDirectory()) {
      File[] children = file.listFiles();
      if (children == null) {
        throw new SecurityException("Cannot list files in directory: " + file.getAbsolutePath());
      }
      for (File child : children) {
        String childPath = (relativePath.isEmpty() ? "" : relativePath + "/") + child.getName();
        scanRecursive(child, level + 1, childPath);
      }
    } else {
      if (filter.accept(level, relativePath, file)) {
        fileHandler.handle(level, relativePath, file);
      }
    }
  }
}