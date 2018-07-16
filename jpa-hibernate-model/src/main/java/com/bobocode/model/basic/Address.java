package com.bobocode.model.basic;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter @Setter
@ToString(exclude = "user")
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue
    private Long id;
    private String city;
    private String street;
    @Column(name = "street_number")
    private String streetNumber;
    @Column(name = "apartment_number")
    private String apartmentNumber;
    @Column(name = "zip_code")
    private String zipCode;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @OneToOne
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        return id.equals(address.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
