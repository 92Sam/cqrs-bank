package com.company.bankservice.enums;

import lombok.Getter;

@Getter
public enum OperationType {
    UPDATE("u"),
    CREATE("c"),
    DELETE("d");

    private String value;
    OperationType(String type) {
        this.value = type;
    }
}
