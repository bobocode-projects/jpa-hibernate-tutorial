package com.bobocode.model.advanced;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class ExchangeRequest {
    @Id @GeneratedValue
    private Long id;
    @ManyToOne
    private ExchangePoint exchangePoint;
    private LocalDateTime createdOn;
    private LocalDateTime processedOn;
    private LocalDateTime finishedOn;
    @OneToMany(mappedBy = "exchangeRequest")
    private List<RequestComment> comments = new ArrayList<>();
    @ManyToOne
    private Ride ride;
}
