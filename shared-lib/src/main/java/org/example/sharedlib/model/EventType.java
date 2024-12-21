package org.example.sharedlib.model;

import lombok.Getter;

@Getter
public enum EventType {
    PRODUCT_CREATED("PRODUCT_CREATED"), PRODUCT_UPDATED("PRODUCT_UPDATED"),
    PRODUCT_DELETED("PRODUCT_DELETED");

    private String value;

    EventType(String value) {
        this.value = value;
    }

}
