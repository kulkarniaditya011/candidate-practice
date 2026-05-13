package com.employee.app.enums;

public enum JoiningType {

EARLY_JOINING("Early Joining"),
LATE_JOINING("Late Joining");

private final String displayName;

JoiningType(String displayName) {
    this.displayName = displayName;
}

public static boolean isValid(String value) {
    for (JoiningType type : values()) {
        if (type.name().equalsIgnoreCase(value)) {
            return true;
        }
    }
    return false;
}
}
