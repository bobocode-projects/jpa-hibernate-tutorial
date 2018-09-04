package com.bobocode.model.advanced;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class RequestComment {
    @Id
    @GeneratedValue
    private Long id;
    @Embedded
    private Comment comment;
    @ManyToOne
    private ExchangeRequest exchangeRequest;
}
