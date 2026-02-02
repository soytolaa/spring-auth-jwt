package com.tola.demoapi.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum Status {
    PENDING("PENDING"),
    PROGRESS("PROGRESS"),
    COMPLETED("COMPLETED"),
    CANCELLED("CANCELLED"),
    HOLD("HOLD"),
    FEEDBACK("FEEDBACK");
    private String value;
}
