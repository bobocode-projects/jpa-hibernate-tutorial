package com.bobocode.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter @Setter
@Embeddable
public class Credentials {
    private String email;
    private String password;
    @Column(name = "last_modified_password")
    private LocalDateTime lastModifiedPassword;
}
