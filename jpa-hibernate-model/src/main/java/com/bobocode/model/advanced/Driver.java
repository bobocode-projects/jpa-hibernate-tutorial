package com.bobocode.model.advanced;


import com.bobocode.model.basic.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Driver extends User {
    private String driverLicenceNumber;
    @OneToMany(mappedBy = "driver")
    private List<Ride> rides = new ArrayList<>();
}
