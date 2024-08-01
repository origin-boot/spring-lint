package com.origin.lint.infrastructure.checker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForbiddenWordChecker implements FileChecker {

  private Map<String, String> checks = new HashMap<>();

  public ForbiddenWordChecker() {
    checks.put("@" + "Query", "Query annotation found");
    checks.put("@" + "Modifying", "Modifying annotation found");
    checks.put("select" + " *", "SQL 'SELECT" + " *' found");
    checks.put("SELECT" + " *", "SQL 'SELECT" + " *' found");
  }

  @Override
  public void check(String path, String content) throws Exception {
    List<String> errors = new ArrayList<>();
    checks.forEach((keyword, description) -> {
      if (content.contains(keyword)) {
        String error = "[ERROR] " + description + " in file: " + path;
        errors.add(error);
      }
    });
    if (!errors.isEmpty()) {
      throw new RuntimeException(String.join("\n", errors.toArray(new String[0])));
    }
  }
}
