package com.company.bankservice.enums.errors;

public enum AccountError {

    ACCOUNT_EXIST("The user already exist");

    private final String messageFinal;

    AccountError(String message) {
        this.messageFinal = message;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
