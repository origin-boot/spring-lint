package com.origin.lint.infrastructure.checker;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileNameChecker implements FileChecker {

  private Map<String, String> checks = new HashMap<>();

  public FileNameChecker() {
    checks.put(".*VO$", "file name cannot end with VO");
    checks.put(".*DTO$", "file name cannot end with DTO");
    checks.put(".*DAO$", "file name cannot end with DAO");
    checks.put(".*PO$", "file name cannot end with PO");
  }

  @Override
  public void check(String path, String content) throws Exception {
    String name = getName(path);

    List<String> errors = new ArrayList<>();
    checks.forEach((regex, description) -> {
      if (isMatch(regex, name)) {
        String error = "[ERROR] " + description + " : " + path;
        errors.add(error);
      }
    });
    if (!errors.isEmpty()) {
      throw new RuntimeException(String.join("\n", errors.toArray(new String[0])));
    }
  }

  private String getName(String path) {
    Path filePath = Paths.get(path);

    Path fileNameWithExtension = filePath.getFileName();

    String fileName = fileNameWithExtension.toString();
    if (fileName.contains(".")) {
      fileName = fileName.substring(0, fileName.indexOf("."));
    }
    return fileName;
  }

  private boolean isMatch(String regex, String s) {
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(s);
    boolean isMatch = matcher.matches();
    return isMatch;
  }
}
