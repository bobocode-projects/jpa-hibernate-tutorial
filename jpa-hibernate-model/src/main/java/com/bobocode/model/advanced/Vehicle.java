package com.bobocode.model.advanced;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Setter @Getter
@Entity @Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "vehicle")
    List<Ride> rides = new ArrayList<>();

}
