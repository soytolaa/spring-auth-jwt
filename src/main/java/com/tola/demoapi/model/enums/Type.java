package com.tola.demoapi.model.enums;

public enum Type {
    CREDENTIALS("credentials"), // index 0 credentials login (default)
    GOOGLE("google"), // index 1 google login
    GITHUB("github"); // index 2 github login

    private String value;

    Type(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
