package com.bobocode.model.advanced;

import com.bobocode.model.basic.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class ExchangePoint {
    @Id
    @GeneratedValue
    private Long id;
    private String description;
    private Customer customer;
    private Address address;
    @OneToMany(mappedBy = "exchangePoint")
    private List<ExchangeRequest> exchangeRequests = new ArrayList<>();
}
