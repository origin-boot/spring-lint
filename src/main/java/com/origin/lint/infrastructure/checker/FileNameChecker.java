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
    checks.put(".*V[Oo]$", "file name cannot end with VO/Vo");
    checks.put(".*D[Tt][Oo]$", "file name cannot end with DTO/Dto");
    checks.put(".*D[Aa][Oo]$", "file name cannot end with DAO/Dao");
    checks.put(".*P[Oo]$", "file name cannot end with PO/Po");
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
