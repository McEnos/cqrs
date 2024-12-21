package org.example.sharedlib.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ProductEvent implements Serializable {
    private EventType eventType;
    private Product product;
}
