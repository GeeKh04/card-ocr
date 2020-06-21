package com.ocr.card.model;

import java.util.Date;

public class Card {

    private String firstName;
    private String lastName;
    private long reference;  // CNE
    private Date birthDate;
    private String cardNumber;

    public Card() {
    }

    public Card(Card cardTransfer){
		reference= cardTransfer.getReference();
		firstName= cardTransfer.getFirstName();
		lastName= cardTransfer.getLastName();
        cardNumber= cardTransfer.getCardNumber();
        birthDate = cardTransfer.getBirthDate();
	}

    public Card(String firstName, String lastName, long reference, Date birthDate, String cardNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.reference = reference;
        this.birthDate = birthDate;
        this.cardNumber = cardNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getReference() {
        return reference;
    }

    public void setReference(long reference) {
        this.reference = reference;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public String toString() {
        return "Card{" +
                "\n firstName='" + firstName + '\'' +
                ",\n lastName='" + lastName + '\'' +
                ",\n reference='" + reference + '\'' +
                ",\n birthDate='" + birthDate + '\'' +
                //",\n cardNumber='" + cardNumber + '\'' +
                "\n}";
    }
}