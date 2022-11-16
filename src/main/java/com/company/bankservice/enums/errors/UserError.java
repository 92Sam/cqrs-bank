package com.company.bankservice.enums.errors;

public enum UserError {

    USER_ALREADY_EXIST("The user already exist %s"),
    USER_NOT_EXIST("The user already exist %s"),
    USER_DISABLED_ACCESS("This user cannot access"),
    CREDENTIAL_NOT_MISMATCH("Wrong Credentials");

    private final String messageFinal;

    UserError(String messageFinal) {
        this.messageFinal = messageFinal;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
