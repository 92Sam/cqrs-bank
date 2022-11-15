package com.company.bankservice.enums;

import lombok.Getter;

@Getter
public enum CreditLine {

    CREDIT_BASIC("Credit Basic", 100F);

    private String nameCredit;
    private float creditValue;

    CreditLine(String nameCredit, float creditValue) {
        this.nameCredit = nameCredit;
        this.creditValue = creditValue;
    }

}
