package com.tola.demoapi.model.enums;

public enum Role {
    USER("user"), // index 0 user
    SELLER("seller"), // index 1 seller
    ADMIN("admin"); // index 2 admin

    private String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
