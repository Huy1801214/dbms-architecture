package dbms.metadata.domain;

import java.util.List;

public class ValidationResult {
    private final Boolean isValid;
    private final List<String> violations;

    public ValidationResult(Boolean isValid, List<String> violations) {
        this.isValid = isValid;
        this.violations = violations;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public List<String> getViolations() {
        return violations;
    }

}
