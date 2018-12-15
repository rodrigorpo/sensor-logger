package com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "locations")
@Data
@NoArgsConstructor
public class Location implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLocation;
    @NotNull
    @Column(unique = true)
    private String name;
    @NotNull
    private String city;
    @NotNull
    private String province;
    @NotNull
    private String country;

    public Location(String name, String city, String province, String country) {
        this.name = name;
        this.city = city;
        this.province = province;
        this.country = country;
    }
}
