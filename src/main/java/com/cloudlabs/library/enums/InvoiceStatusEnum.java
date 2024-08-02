package com.cloudlabs.library.enums;

import lombok.Getter;

@Getter
public enum InvoiceStatusEnum {
    BORROWED("BORROWED"),
    RETURNED("RETURNED");

    private final String name;

    InvoiceStatusEnum(String name) {
        this.name = name;
    }
}
