package com.tola.demoapi.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum Status {
    PENDING("pending"),
    PROGRESS("progress"),
    COMPLETED("completed"),
    CANCELLED("cancelled"),
    HOLD("hold"),
    FEEDBACK("feedback");
    private String value;
}
