package com.bobocode.model.basic;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter @Setter
@EqualsAndHashCode(of = "email")
@Embeddable
public class Credentials {
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "last_modified_password")
    private LocalDateTime lastModifiedPassword;
}
