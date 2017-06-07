package com.bobocode.model;

import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDate birthday;
    private LocalDate creationDate = LocalDate.now();
    private BigDecimal balance = BigDecimal.ZERO;

    public Account(String firstName, String lastName, String email, String password, LocalDate birthday, BigDecimal balance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.birthday = birthday;
        this.balance = balance;
    }
}
