package com.bobocode.model.advanced;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Ride {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Driver driver;
    @ManyToOne
    private Vehicle vehicle;

    @OneToMany(mappedBy = "ride")
    private List<ExchangeRequest> exchangeRequests;
    private LocalDateTime createdOn;
    private LocalDateTime startTime;
    private LocalDateTime finishTime;

}
