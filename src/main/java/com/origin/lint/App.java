package com.origin.lint;

import com.origin.lint.infrastructure.checker.FileChecker;

public class App {
  public static void main(String[] args) {
    if (args.length == 0) {
      throw new IllegalArgumentException("Please provide a path to scan.");
    }

    boolean hasError = false;

    for (String path : args) {
      FileChecker fc = new FileChecker(path);
      try {
        fc.run();
        hasError = hasError || fc.hasError();
      } catch (Exception e) {
        hasError = true;
      }
    }

    if (hasError) {
      System.exit(1);
      return;
    }

    System.out.println("No error found.");
  }
}
