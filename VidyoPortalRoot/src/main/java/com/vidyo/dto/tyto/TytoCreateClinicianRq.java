package com.vidyo.dto.tyto;

import com.vidyo.TytoConstants;

public class TytoCreateClinicianRq {

    private final String firstName;

    private final String lastName;

    private final String dateOfBirth;

    private final String sex;

    private final String email;

    private final String identifier;

    public TytoCreateClinicianRq(String firstName,
                                 String lastName,
                                 String email,
                                 String identifier) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.identifier = identifier;
        this.sex = TytoConstants.TYTO_CONST_GENDER;
        this.dateOfBirth = TytoConstants.TYTO_CONST_BIRTH_DATE;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getSex() {
        return sex;
    }

    public String getEmail() {
        return email;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String toString() {
        return "TytoCreateClinicianRq{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", sex='" + sex + '\'' +
                ", email='" + email + '\'' +
                ", identifier='" + identifier + '\'' +
                '}';
    }
}
