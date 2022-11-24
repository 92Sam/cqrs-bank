package com.company.bankservice.enums.errors;

public enum AccountError {

    ACCOUNT_EXIST("THIS ACCOUNT EXIST"),
    ACCOUNT_ERROR_UPDATED_BALANCE("ERROR UPDATE BALANCE"),
    ACCOUNT_NOT_EXIST("THE ACCOUNT NOT EXIST");

    private final String messageFinal;

    AccountError(String message) {
        this.messageFinal = message;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
