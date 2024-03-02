package com.bunu.springbootjdbc.domain;

public class CreditCard {
    private String cardNumber;
    private String type;
    private String validation;

    public CreditCard(String cardNumber, String type, String validation) {
        this.cardNumber = cardNumber;
        this.type = type;
        this.validation = validation;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "cardNumber='" + cardNumber + '\'' +
                ", type='" + type + '\'' +
                ", validation='" + validation + '\'' +
                '}';
    }
}
