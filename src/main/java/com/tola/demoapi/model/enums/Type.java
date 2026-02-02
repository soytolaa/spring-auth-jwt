package com.tola.demoapi.model.enums;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum Type {
    CREDENTIALS("credentials"), // index 0 credentials login (default)
    GOOGLE("google"), // index 1 google login
    GITHUB("github"); // index 2 github login
    private String value;
}
